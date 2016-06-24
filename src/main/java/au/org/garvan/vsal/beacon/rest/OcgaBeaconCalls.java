/*
 * Copyright 2016 Garvan Institute of Medical Research
 */
package au.org.garvan.vsal.beacon.rest;

import au.org.garvan.vsal.beacon.entity.Query;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * OpenCGA Rest Calls for Beacon
 *
 * @author Dmitry Degrave (dmeetry@gmail.com)
 * @version 0.1
 */
public class OcgaBeaconCalls {

    private static String sessionId = null;
    private static String baseurl = null;
    private static String propFileName = "ocga.properties";
    private static Properties prop = null;

    public int ocgaBeaconQuery(Query query) throws IOException {
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

    private synchronized void readConfig() throws IOException {
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

    private ClientResponse ocgaRestCall(String url, MultivaluedMap<String,String> params) throws IOException {
        try {
            Client client = Client.create();
            WebResource webResource = client.resource(url);
            return webResource.queryParams(params).accept("application/json").get(ClientResponse.class);
        } catch (Exception e) {
            throw new IOException("REST Backend Exception");
        }
    }

    private synchronized void ocgaLogin() throws IOException {
        if (sessionId == null || sessionId.isEmpty()) {
            baseurl = "http://" + prop.getProperty("opencga.host") + prop.getProperty("opencga.resturl");
            String url = baseurl + "/users/" + prop.getProperty("opencga.user") + "/login";
            MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();
            queryParams.add("password", prop.getProperty("opencga.password"));
            ClientResponse res = ocgaRestCall(url,queryParams);

            // get sessionId from json
            JsonArray jsonArray;
            JsonObject jsonObject;
            String jsonLine = res.getEntity(String.class);
            JsonElement jsonElement = new JsonParser().parse(jsonLine);

            if (jsonElement.isJsonObject()) {
                jsonObject = jsonElement.getAsJsonObject();
                jsonElement = jsonObject.get("response");
                if (jsonElement.isJsonArray()) {
                    jsonArray = jsonElement.getAsJsonArray();
                    jsonObject = jsonArray.get(0).getAsJsonObject();
                    jsonElement = jsonObject.get("result");
                    if (jsonElement.isJsonArray()) {
                        jsonArray = jsonElement.getAsJsonArray();
                        jsonObject = jsonArray.get(0).getAsJsonObject();
                        sessionId = jsonObject.get("sessionId").getAsString();
                    }
                }
            }
        }
    }

    private List<Integer> getStudyIds() throws IOException {
        String url = baseurl + "/projects/" + prop.getProperty("opencga.projectId") + "/studies";
        MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();
        queryParams.add("sid", sessionId);
        ClientResponse queryResult = ocgaRestCall(url,queryParams);

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

    private int getNumVariantsInStudy(Integer studyId, Query query) throws IOException {
        String url = baseurl + "/studies/" + studyId + "/variants";
        Long pos = query.getPosition() + 1; // convert 0-based beacon protocol into 1-based VCF position
        MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();
        queryParams.add("region", query.getChromosome() + ":" + pos + "-" + pos);
        queryParams.add("alternate", query.getAllele());
        queryParams.add("count", "true");
        queryParams.add("sid", sessionId);
        ClientResponse queryResult = ocgaRestCall(url,queryParams);

        if (queryResult.getStatus() != 200) {
            throw new IOException("REST status: " + queryResult.getStatus());
        }

        // get number of matching alleles
        JsonArray jsonArray;
        JsonObject jsonObject;
        String jsonLine = queryResult.getEntity(String.class);
        JsonElement jsonElement = new JsonParser().parse(jsonLine);
        Integer numTotalResults = 0;

        if (jsonElement.isJsonObject()) {
            jsonObject = jsonElement.getAsJsonObject();
            jsonElement = jsonObject.get("response");
            if (jsonElement.isJsonArray()) {
                jsonArray = jsonElement.getAsJsonArray();
                jsonObject = jsonArray.get(0).getAsJsonObject();
                numTotalResults = jsonObject.get("numTotalResults").getAsInt();
            }
        }

        return numTotalResults;
    }

}