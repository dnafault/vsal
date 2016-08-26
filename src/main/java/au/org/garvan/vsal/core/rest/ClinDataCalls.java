package au.org.garvan.vsal.core.rest;

import au.org.garvan.vsal.core.entity.CoreQuery;
import au.org.garvan.vsal.core.entity.SampleIDs;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class ClinDataCalls {

    private static String baseurl = null;
    private static Properties prop = null;
    private static String propFileName = "vsal.properties";

    private ClientResponse restGetCall(String url, MultivaluedMap<String,String> params)
            throws IOException {
        try {
            Client client = Client.create();
            WebResource webResource = client.resource(url);
            return webResource.queryParams(params).accept("application/json").get(ClientResponse.class);
        } catch (Exception e) {
            throw new IOException("REST call exception");
        }
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
                if (prop.getProperty("clindata.host") == null) {
                    baseurl = "http://localhost:8080/clindata/find";
                } else {
                    baseurl = "http://" + prop.getProperty("clindata.host") + "/clindata/find";
                }
            }
        }
    }

    public List<String> getClinDataSamples(CoreQuery q)
            throws IOException {
        if (prop == null) {
            readConfig();
        }

        MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();

        if (q.getGender() != null) {
            queryParams.add("gender", q.getGender().toString());
        }
        if (q.getYobStart() != null) {
            queryParams.add("yobStart", q.getYobStart().toString());
        }
        if (q.getYobEnd() != null) {
            queryParams.add("yobEnd", q.getYobEnd().toString());
        }
        if (q.getSbpStart() != null) {
            queryParams.add("sbpStart", q.getSbpStart().toString());
        }
        if (q.getSbpEnd() != null) {
            queryParams.add("sbpEnd", q.getSbpEnd().toString());
        }
        if (q.getHeightStart() != null) {
            queryParams.add("heightStart", q.getHeightStart().toString());
        }
        if (q.getHeightEnd() != null) {
            queryParams.add("heightEnd", q.getHeightEnd().toString());
        }
        if (q.getWeightStart() != null) {
            queryParams.add("weightStart", q.getWeightStart().toString());
        }
        if (q.getWeightEnd() != null) {
            queryParams.add("weightEnd", q.getWeightEnd().toString());
        }
        if (q.getAbdCircStart() != null) {
            queryParams.add("abdCircStart", q.getAbdCircStart().toString());
        }
        if (q.getAbdCircEnd() != null) {
            queryParams.add("abdCircEnd", q.getAbdCircEnd().toString());
        }
        if (q.getGlcStart() != null) {
            queryParams.add("glcStart", q.getGlcStart().toString());
        }
        if (q.getGlcEnd() != null) {
            queryParams.add("glcEnd", q.getGlcEnd().toString());
        }

        ClientResponse queryResult = restGetCall(baseurl,queryParams);

        if (queryResult.getStatus() != 200) {
            throw new IOException("REST status: " + queryResult.getStatus());
        }

        // JAXB Unmarshalling - works fine here
        SampleIDs samples = queryResult.getEntity(SampleIDs.class);

        return samples.getSampleIds();
    }
}