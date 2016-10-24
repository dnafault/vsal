/*
 * Copyright 2016 Garvan Institute of Medical Research
 */
package au.org.garvan.vsal.core.rest;

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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * OpenCGA Rest Calls
 *
 * @author Dmitry Degrave (dmeetry@gmail.com)
 * @version 1.0
 */
public class OcgaCalls {

    private static String sessionId = null;
    private static String baseurl = null;
    private static String propFileName = "vsal.properties";
    private static Properties prop = null;

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

    private String getVariantsInStudy(Integer studyId, CoreQuery query, List<String> samples, boolean count)
            throws IOException {
        String study = studyId.toString();
        String url = baseurl + "/studies/" + study + "/variants";

        MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();
        queryParams.add("sid", sessionId);
        if (count) {
            queryParams.add("count", "true");
        }
//        queryParams.add("include", "blah");
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
        if (query.getGenes() != null) {
            for (String s : query.getGenes()) {
                queryParams.add("gene", s);
            }
        }
        if (query.getDbSNP() != null) {
            for (String s : query.getDbSNP()) {
                queryParams.add("ids", s);
            }
        }
        if (query.getMaf() != null && !query.getMaf().isEmpty()) {
            queryParams.add("maf", "ALL" + query.getMaf());
        }
        if (query.getPopMaf() != null && !query.getPopMaf().isEmpty()) {
            queryParams.add("annot-population-maf", study + ":ALL" + query.getPopMaf());
        }
        if (query.getPopAltFrq() != null && !query.getPopAltFrq().isEmpty()) {
            queryParams.add("alternate_frequency", study + ":ALL" + query.getPopAltFrq());
        }
        if (query.getPopRefFrq() != null && !query.getPopRefFrq().isEmpty()) {
            queryParams.add("reference_frequency", study + ":ALL" + query.getPopRefFrq());
        }
        if (query.getAnnotCT() != null && !query.getAnnotCT().isEmpty()) {
            queryParams.add("annot-ct", query.getAnnotCT());
        }
        if (query.getAnnotHPO() != null && !query.getAnnotHPO().isEmpty()) {
            queryParams.add("annot-hpo", query.getAnnotHPO());
        }
        if (query.getAnnotGO() != null && !query.getAnnotGO().isEmpty()) {
            queryParams.add("annot-go", query.getAnnotGO());
        }
        if (query.getAnnotXref() != null && !query.getAnnotXref().isEmpty()) {
            queryParams.add("annot-xref", query.getAnnotXref());
        }
        if (query.getAnnotBiotype() != null && !query.getAnnotBiotype().isEmpty()) {
            queryParams.add("annot-biotype", query.getAnnotBiotype());
        }
        if (query.getPolyphen() != null && !query.getPolyphen().isEmpty()) {
            queryParams.add("polyphen", query.getPolyphen());
        }
        if (query.getSift() != null && !query.getSift().isEmpty()) {
            queryParams.add("sift", query.getSift());
        }
        if (query.getConservationScore() != null && !query.getConservationScore().isEmpty()) {
            queryParams.add("conservation", query.getConservationScore());
        }
//        if (samples != null && !samples.isEmpty()) {
//            for (String s : samples) {
//                queryParams.add("returnedSamples", s);
//            }
//        }

        ClientResponse queryResult = ocgaRestGetCall(url,queryParams);
        if (queryResult.getStatus() != 200) {
            throw new IOException("Ocga REST call status: " + queryResult.getStatus());
        }

        return queryResult.getEntity(String.class);
    }

    public List<CoreVariant> ocgaFindVariants(CoreQuery coreQuery, List<String> samples)
            throws IOException {
        // dirty fast checks for static fields
        if (prop == null) {
            readConfig();
        }
        if (sessionId == null || sessionId.isEmpty() || baseurl == null) { // sync visibility between static fields
            ocgaLogin();
        }

        List<Integer> studies = getStudyIds();
        List<VariantResponse> variants = new LinkedList<>();

        for (Integer study : studies) {
            String jsonVariants = getVariantsInStudy(study, coreQuery, samples, false);

            // JAXB fails to parse Variant response, so use Gson Unmarshalling.
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.serializeNulls().create();
            Type type = new TypeToken<QueryResponse<VariantResponse>>(){}.getType();
            QueryResponse<VariantResponse> ocgaResponse = gson.fromJson(jsonVariants, type);

            variants.addAll(ocgaResponse.getResponse().get(0).getResult());
        }

        return toCoreVariants(variants);
    }

    private List<CoreVariant> toCoreVariants(List<VariantResponse> ocgaVariants) {
        List<CoreVariant> coreVariants = new LinkedList<>();
        for (VariantResponse vr : ocgaVariants) {
            String dbSNP = vr.getId();
            List<VariantStats> stat = new LinkedList<>();
            for (StudyEntry studyEntry : vr.getStudies()) {
                stat.add(studyEntry.getStats().get("ALL"));
            }
            CoreVariant cv = new CoreVariant(vr.getChromosome(), vr.getStart(), vr.getEnd(),
                    (dbSNP != null && dbSNP.startsWith("rs")) ? dbSNP : null, vr.getAlternate(),
                    vr.getReference(), vr.getStrand(), vr.getType(), stat);
            coreVariants.add(cv);
        }
        return coreVariants;
    }

    public List<Integer> CountVariants(CoreQuery coreQuery, List<String> samples)
            throws IOException {
        // dirty fast checks for static fields
        if (prop == null) {
            readConfig();
        }
        if (sessionId == null || sessionId.isEmpty() || baseurl == null) { // sync visibility between static fields
            ocgaLogin();
        }

        List<Integer> studies = getStudyIds();
        List<Integer> count = new LinkedList<>();

        for (Integer study : studies) {
            String jsonVariants = getVariantsInStudy(study, coreQuery, samples, true);

            // JAXB fails to parse Variant response, so use Gson Unmarshalling.
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.serializeNulls().create();
            Type type = new TypeToken<QueryResponse<Integer>>(){}.getType();
            QueryResponse<Integer> ocgaResponse = gson.fromJson(jsonVariants, type);

            count.addAll(ocgaResponse.getResponse().get(0).getResult());
        }

        return count;
    }
}