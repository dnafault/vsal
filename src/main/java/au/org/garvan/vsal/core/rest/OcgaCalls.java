/*
 * Copyright 2016 Garvan Institute of Medical Research
 */
package au.org.garvan.vsal.core.rest;

import au.org.garvan.vsal.core.entity.CoreQuery;
import au.org.garvan.vsal.core.entity.CoreVariant;
import au.org.garvan.vsal.core.entity.CoreVariantStats;
import au.org.garvan.vsal.ocga.entity.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.*;
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
 * Rest Calls to OpenCGA
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
        throws UniformInterfaceException, ClientHandlerException {
        Client client = Client.create();
        WebResource webResource = client.resource(url);
        return webResource.queryParams(params).accept("application/json").get(ClientResponse.class);
    }

    private ClientResponse ocgaRestPostCall(String url, MultivaluedMap<String,String> params, String jsonReq)
        throws UniformInterfaceException, ClientHandlerException {
        Client client = Client.create();
        WebResource webResource = client.resource(url);
        if (params != null)
            return webResource.queryParams(params).type("application/json").post(ClientResponse.class, jsonReq);
        else
            return webResource.type("application/json").post(ClientResponse.class, jsonReq);
    }

    private synchronized void ocgaLogin()
            throws IOException {
        if (sessionId == null || sessionId.isEmpty()) {
            baseurl = "http://" + prop.getProperty("opencga.host") + prop.getProperty("opencga.resturl");
            String url = baseurl + "/users/" + prop.getProperty("opencga.user") + "/login";
            LoginRequest lr = new LoginRequest(prop.getProperty("opencga.password"));
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.serializeNulls().create();
            ClientResponse queryResult;
            try {
                queryResult = ocgaRestPostCall(url, null, gson.toJson(lr));
            } catch (UniformInterfaceException|ClientHandlerException e) {
                throw new IOException("Can't connect to ocga.");
            }
            if (queryResult.getStatus() != 200) {
                throw new IOException("Ocga login failed: " + queryResult.getStatus());
            }
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
        ClientResponse queryResult;

        try {
            queryResult = ocgaRestGetCall(url, queryParams);
        } catch (UniformInterfaceException|ClientHandlerException e) {
            throw new IOException("Can't connect to ocga.");
        }

        if (queryResult.getStatus() != 200) {
            throw new IOException("Can't get study IDs: " + queryResult.getStatus());
        }

        JsonArray jsonArray;
        JsonObject jsonObject;
        String jsonLine = queryResult.getEntity(String.class);
        JsonElement jsonElement = new JsonParser().parse(jsonLine);
        List<Integer> studies = new ArrayList<>();
        int numStudies;

        // old way to parse.
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

    private String getVariantsInStudyGET(Integer studyId, CoreQuery query, List<String> samples, boolean count)
            throws IOException, IllegalArgumentException {

        if (studyId == null)
            throw new IllegalArgumentException("Study can't be null");

        String study = studyId.toString();
        String url = baseurl + "/analysis/variant/query";
        MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();
        queryParams.add("sid", sessionId);
        queryParams.add("studies", study);

        if (count) {
            queryParams.add("count", "true");
        }
        if (query.getReturnAnnotations()) {
            queryParams.add("exclude", "studies.samplesData,studies.files");
        } else {
            queryParams.add("exclude", "studies.samplesData,studies.files,annotation");
        }
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
        // for sample filtering use POST - getVariantsInStudyPOST, as it's easy to exceed GET limit

        ClientResponse queryResult;
        try {
            queryResult = ocgaRestGetCall(url, queryParams);
        } catch (UniformInterfaceException|ClientHandlerException e) {
            throw new IOException("Can't connect to ocga.");
        }

        if (queryResult.getStatus() != 200) {
            if (queryResult.getStatus() == 500) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.serializeNulls().create();
                Type type = new TypeToken<QueryResponse<VariantResponse>>(){}.getType();
                QueryResponse<VariantResponse> ocgaResponse = gson.fromJson(queryResult.getEntity(String.class), type);
                throw new IOException("Can't get variants: " + ocgaResponse.getError());
            } else
                throw new IOException("Can't get variants: " + queryResult.getStatus());
        }

        return queryResult.getEntity(String.class);
    }

    private String getVariantsInStudyPOST(Integer studyId, CoreQuery query, List<String> samples, boolean count)
            throws IOException, IllegalArgumentException {

        if (studyId == null)
            throw new IllegalArgumentException("Study can't be null");

        String study = studyId.toString();
        String url = baseurl + "/analysis/variant/query";
        MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();
        queryParams.add("sid", sessionId);

        if (count) {
            queryParams.add("count", "true");
        }
        if (query.getReturnAnnotations()) {
            queryParams.add("exclude", "studies.samplesData,studies.files");
        } else {
            queryParams.add("exclude", "studies.samplesData,studies.files,annotation");
        }
        if (query.getLimit() != null) {
            queryParams.add("limit", query.getLimit().toString());
        }
        if (query.getSkip() != null) {
            queryParams.add("skip", query.getSkip().toString());
        }

        VariantRequestParams vrp = new VariantRequestParams();
        vrp.setStudies(study);

        if (query.getChromosome() != null && query.getPositionStart() != null && query.getPositionEnd() != null) { // 1-based VCF positions
            vrp.setRegion(query.getChromosome().toString() + ":" + query.getPositionStart() + "-" + query.getPositionEnd());
        } else if (query.getChromosome() != null) {
            vrp.setChromosome(query.getChromosome().toString());
        }
        if (query.getType() != null) {
            vrp.setType(query.getType().toString());
        }
        if (query.getRefAllele() != null && !query.getRefAllele().isEmpty()) {
            vrp.setReference(query.getRefAllele());
        }
        if (query.getAltAllele() != null && !query.getAltAllele().isEmpty()) {
            vrp.setAlternate(query.getAltAllele());
        }
        if (query.getGenes() != null && !query.getGenes().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String s : query.getGenes()) {
                sb.append(s).append(",");
            }
            vrp.setGene(sb.deleteCharAt(sb.length()-1).toString());
        }
        if (query.getDbSNP() != null && !query.getDbSNP().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String s : query.getDbSNP()) {
                sb.append(s).append(",");
            }
            vrp.setIds(sb.deleteCharAt(sb.length()-1).toString());
        }
        if (query.getMaf() != null && !query.getMaf().isEmpty()) {
            vrp.setMaf("ALL" + query.getMaf());
        }
        if (query.getPopAltFrq() != null && !query.getPopAltFrq().isEmpty()) {
            vrp.setAlternate_frequency(study + ":ALL" + query.getPopAltFrq());
        }
        if (query.getPopRefFrq() != null && !query.getPopRefFrq().isEmpty()) {
            vrp.setReference_frequency(study + ":ALL" + query.getPopRefFrq());
        }
        if (query.getPolyphen() != null && !query.getPolyphen().isEmpty()) {
            vrp.setPolyphen(query.getPolyphen());
        }
        if (query.getSift() != null && !query.getSift().isEmpty()) {
            vrp.setSift(query.getSift());
        }
        if (samples != null && !samples.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String s : samples) {
                sb.append(s + ":0/1,1/1,0/2,1/2,2/2,0/3,1/3,2/3,3/3,0/4,1/4,2/4,3/4,4/4,0/5,1/5,2/5,3/5,4/5,5/5;");
            }
            vrp.setGenotype(sb.toString());
        }

        ClientResponse queryResult;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            queryResult = ocgaRestPostCall(url, queryParams, gson.toJson(vrp));
        } catch (UniformInterfaceException|ClientHandlerException e) {
            throw new IOException("Can't connect to ocga.");
        }

        if (queryResult.getStatus() != 200) {
            if (queryResult.getStatus() == 500) {
                Type type = new TypeToken<QueryResponse<VariantResponse>>(){}.getType();
                QueryResponse<VariantResponse> ocgaResponse = gson.fromJson(queryResult.getEntity(String.class), type);
                throw new IOException("Can't get variants: " + ocgaResponse.getError());
            } else
                throw new IOException("Can't get variants: " + queryResult.getStatus());
        }

        return queryResult.getEntity(String.class);
    }

    public List<CoreVariant> ocgaFindVariants(CoreQuery coreQuery, List<String> samples, List<Integer> dbTime)
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
            String jsonVariants = getVariantsInStudyPOST(study, coreQuery, samples, false);

            // JAXB fails to parse Variant response, so use Gson Unmarshalling.
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.serializeNulls().create();
            Type type = new TypeToken<QueryResponse<VariantResponse>>(){}.getType();
            QueryResponse<VariantResponse> ocgaResponse = gson.fromJson(jsonVariants, type);

            variants.addAll(ocgaResponse.getResponse().get(0).getResult());
            dbTime.add(ocgaResponse.getResponse().get(0).getDbTime());
        }

        return toCoreVariants(variants);
    }

    private List<CoreVariant> toCoreVariants(List<VariantResponse> ocgaVariants) {
        List<CoreVariant> coreVariants = new LinkedList<>();
        for (VariantResponse vr : ocgaVariants) {
            String dbSNP = vr.getId();
            List<CoreVariantStats> stat = new LinkedList<>();
            for (StudyEntry studyEntry : vr.getStudies()) {
                stat.add(toCoreVariantStats(studyEntry.getStats().get("ALL")));
            }
            CoreVariant cv = new CoreVariant(vr.getChromosome(), vr.getStart(),
                    (dbSNP != null && dbSNP.startsWith("rs")) ? dbSNP : null, vr.getAlternate(),
                    vr.getReference(), vr.getType(), vr.getAnnotation(), stat);
            coreVariants.add(cv);
        }
        return coreVariants;
    }

    private CoreVariantStats toCoreVariantStats(VariantStats ocgaStat) {
        return new CoreVariantStats(ocgaStat.getAltAlleleCount(), ocgaStat.getAltAlleleFreq(), ocgaStat.getGenotypesCount());
    }

    public List<Integer> CountVariants(CoreQuery coreQuery, List<String> samples, List<Integer> dbTime)
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
            String jsonVariants = getVariantsInStudyPOST(study, coreQuery, samples, true);

            // JAXB fails to parse Variant response, so use Gson Unmarshalling.
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.serializeNulls().create();
            Type type = new TypeToken<QueryResponse<Integer>>(){}.getType();
            QueryResponse<Integer> ocgaResponse = gson.fromJson(jsonVariants, type);

            count.addAll(ocgaResponse.getResponse().get(0).getResult());
            dbTime.add(ocgaResponse.getResponse().get(0).getDbTime());
        }

        return count;
    }
}