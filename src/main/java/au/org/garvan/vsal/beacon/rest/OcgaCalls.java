/*
 * Copyright 2016 Garvan Institute of Medical Research
 */
package au.org.garvan.vsal.beacon.rest;

import au.org.garvan.vsal.beacon.entity.Query;
import au.org.garvan.vsal.core.entity.CoreQuery;
import au.org.garvan.vsal.core.entity.CoreVariant;
import au.org.garvan.vsal.ocga.entity.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import java.lang.reflect.Type;

/**
 * OpenCGA Rest Calls
 *
 * @author Dmitry Degrave (dmeetry@gmail.com)
 * @version 0.1
 */
public class OcgaCalls {

    private static String sessionId = null;
    private static String baseurl = null;
    private static String propFileName = "ocga.properties";
    private static Properties prop = null;

    public int ocgaBeaconQuery(Query query)
            throws IOException {
        // dirty fast checks for static fields
        if (prop == null) {
            readConfig();
        }
        if (sessionId == null || sessionId.isEmpty() || baseurl == null) { // sync visibility between static fields
            ocgaLogin();
        }

        int numTotalResults = 0;
        List<Integer> studies = getStudyIds();
        for (Integer study : studies) {
            numTotalResults += getNumVariantsInStudy(study, query);
        }

        return numTotalResults;
    }

    private synchronized void readConfig()
            throws IOException {
        if (prop == null) {
            try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFileName)) {
                Properties p = new Properties();
                if (inputStream != null) {
                    p.load(inputStream);
                } else {
                    throw new FileNotFoundException("property file '" + propFileName + "' not found.");
                }
                prop = p;
            }
        }
    }

    private ClientResponse ocgaRestGetCall(String url, MultivaluedMap<String,String> params)
            throws IOException {
        try {
            Client client = Client.create();
            WebResource webResource = client.resource(url);
            return webResource.queryParams(params).accept("application/json").get(ClientResponse.class);
        } catch (Exception e) {
            throw new IOException("OpenCGA REST call exception");
        }
    }

    private synchronized void ocgaLogin()
            throws IOException {
        if (sessionId == null || sessionId.isEmpty()) {
            baseurl = "http://" + prop.getProperty("opencga.host") + prop.getProperty("opencga.resturl");
            String url = baseurl + "/users/" + prop.getProperty("opencga.user") + "/login";
            MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();
            queryParams.add("password", prop.getProperty("opencga.password"));

            ClientResponse queryResult = ocgaRestGetCall(url,queryParams);
            if (queryResult.getStatus() != 200) {
                throw new IOException("REST status: " + queryResult.getStatus());
            }

            // Gson Unmarshalling - works well with any request
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.serializeNulls().create();
            Type type = new TypeToken<QueryResponse<LoginResponse>>(){}.getType();
            QueryResponse<LoginResponse> ocgaResponse = gson.fromJson(queryResult.getEntity(String.class), type);

            sessionId = ocgaResponse.getResponse().get(0).getResult().get(0).getSessionId();
        }
    }

    private List<Integer> getStudyIds()
            throws IOException {
        String url = baseurl + "/projects/" + prop.getProperty("opencga.projectId") + "/studies";
        MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();
        queryParams.add("sid", sessionId);
        ClientResponse queryResult = ocgaRestGetCall(url,queryParams);

        if (queryResult.getStatus() != 200) {
            throw new IOException("REST status: " + queryResult.getStatus());
        }

        JsonArray jsonArray;
        JsonObject jsonObject;
        String jsonLine = queryResult.getEntity(String.class);
        JsonElement jsonElement = new JsonParser().parse(jsonLine);
        List<Integer> studies = new ArrayList<>();
        int numStudies;

        if (jsonElement.isJsonObject()) {
            jsonObject = jsonElement.getAsJsonObject();
            jsonElement = jsonObject.get("response");
            if (jsonElement.isJsonArray()) {
                jsonArray = jsonElement.getAsJsonArray();
                jsonObject = jsonArray.get(0).getAsJsonObject();
                numStudies = jsonObject.get("numResults").getAsInt();
                jsonElement = jsonObject.get("result");
                if (jsonElement.isJsonArray()) {
                    jsonArray = jsonElement.getAsJsonArray();
                    for (int i=0; i < numStudies; ++i) {
                        jsonObject = jsonArray.get(i).getAsJsonObject();
                        studies.add(jsonObject.get("id").getAsInt());
                    }
                }
            }
        }

        return studies;
    }

    private int getNumVariantsInStudy(Integer studyId, Query query)
            throws IOException {
        String url = baseurl + "/studies/" + studyId + "/variants";
        Long pos = query.getPosition() + 1; // convert 0-based beacon protocol into 1-based VCF position
        MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();
        queryParams.add("region", query.getChromosome() + ":" + pos + "-" + pos);
        queryParams.add("alternate", query.getAllele());
        queryParams.add("count", "true");
        queryParams.add("sid", sessionId);
        ClientResponse queryResult = ocgaRestGetCall(url,queryParams);

        if (queryResult.getStatus() != 200) {
            throw new IOException("REST status: " + queryResult.getStatus());
        }

        /*  We could unmarshall all the objects first to get a number of matching alleles here, but
                a) resultType can get changed once 'count' option starts to work in opencga
                b) we actually don't need objects unmarshalled
            hence we traverse manually&fast without unmarshalling.
         */

        // get number of matching alleles
        JsonArray jsonArray;
        JsonObject jsonObject;
        String jsonLine = queryResult.getEntity(String.class);
        JsonElement jsonElement = new JsonParser().parse(jsonLine);
        Integer numResults = 0;

        if (jsonElement.isJsonObject()) {
            jsonObject = jsonElement.getAsJsonObject();
            jsonElement = jsonObject.get("response");
            if (jsonElement.isJsonArray()) {
                jsonArray = jsonElement.getAsJsonArray();
                jsonObject = jsonArray.get(0).getAsJsonObject();
                numResults = jsonObject.get("numResults").getAsInt();
            }
        }

        return numResults;
    }

    private List<VariantResponse> getVariantsInStudy(Integer studyId, CoreQuery query)
            throws IOException {
        String url = baseurl + "/studies/" + studyId + "/variants";

        MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();
        queryParams.add("sid", sessionId);
        if (query.getChromosome() != null && query.getPositionStart() != null && query.getPositionEnd() != null) { // 1-based VCF positions
            queryParams.add("region", query.getChromosome().toString() +
                    ":" + query.getPositionStart() + "-" + query.getPositionEnd());
        } else if (query.getChromosome() != null) {
            queryParams.add("chromosome", query.getChromosome().toString());
        }
        if (query.getType() != null) {
            queryParams.add("type", query.getType().toString());
        }
        if (query.getRefAllele() != null && !query.getRefAllele().isEmpty()) {
            queryParams.add("reference", query.getRefAllele());
        }
        if (query.getAltAllele() != null && !query.getAltAllele().isEmpty()) {
            queryParams.add("alternate", query.getAltAllele());
        }
        if (query.getLimit() != null) {
            queryParams.add("limit", query.getLimit().toString());
        }
        if (query.getSkip() != null) {
            queryParams.add("skip", query.getSkip().toString());
        }
        // addAll() is not implemented at com.sun.jersey.core.util.MultivaluedMapImpl, hence ugly loops
        // (and yes - returned Lists are never null)
        for (String s : query.getGenes()) {
            queryParams.add("gene", s);
        }
        for (String s : query.getRsIDs()) {
            queryParams.add("ids", s);
        }

        ClientResponse queryResult = ocgaRestGetCall(url,queryParams);
        if (queryResult.getStatus() != 200) {
            throw new IOException("REST status: " + queryResult.getStatus());
        }

        // JAXB fails to parse Variant response, so we use Gson Unmarshalling.
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.serializeNulls().create();
        Type type = new TypeToken<QueryResponse<VariantResponse>>(){}.getType();
        QueryResponse<VariantResponse> ocgaResponse = gson.fromJson(queryResult.getEntity(String.class), type);

        return ocgaResponse.getResponse().get(0).getResult();
    }

    public List<CoreVariant> ocgaFindQuery(CoreQuery coreQuery, List<String> samples)
            throws IOException {
        // dirty fast checks for static fields
        if (prop == null) {
            readConfig();
        }
        if (sessionId == null || sessionId.isEmpty() || baseurl == null) { // sync visibility between static fields
            ocgaLogin();
        }

        if (samples != null && !samples.isEmpty()) {
            //TODO: filtering by samples
        }

        List<Integer> studies = getStudyIds();
        List<VariantResponse> variants = new LinkedList<>();

        for (Integer study : studies) {
            variants.addAll(getVariantsInStudy(study, coreQuery));
        }

        return toCoreVariants(variants);
    }

    public List<CoreVariant> toCoreVariants(List<VariantResponse> ocgaVariants) {
        List<CoreVariant> coreVariants = new LinkedList<>();
        for (VariantResponse vr : ocgaVariants) {
            String rsId = vr.getId();
            CoreVariant cv = new CoreVariant(vr.getChromosome(), vr.getStart(), vr.getEnd(),
                    (rsId != null && rsId.startsWith("rs")) ? rsId : null, vr.getAlternate(),
                    vr.getReference(), vr.getStrand(), vr.getType());
            coreVariants.add(cv);
        }
        return coreVariants;
    }
}