package au.org.garvan.vsal.beacon.rest;

import au.org.garvan.vsal.beacon.entity.BeaconResponseSSVS;
import au.org.garvan.vsal.beacon.entity.Query;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MultivaluedMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SSVSCalls {

    private static String baseurl = null;
    private static Properties prop = null;
    private static String propFileName = "vsal.properties";

    private static ClientResponse restGetCall(String url, MultivaluedMap<String,String> params)
            throws IOException {
//        try {
            Client client = Client.create();
            WebResource webResource = client.resource(url);
            return webResource.queryParams(params).accept("application/json").get(ClientResponse.class);
//        } catch (Exception e) {
//            throw new IOException("REST call exception");
//        }
    }

    private static synchronized void readConfig()
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
                if (prop.getProperty("ssvs.host") == null) {
                    baseurl = "http://localhost:8080/query";
                } else {
                    baseurl = "http://" + prop.getProperty("ssvs.host") + "/ssvs/query";
                }
            }
        }
    }

    public static BeaconResponseSSVS beacon(Query q) throws IOException {
        if (prop == null) {
            readConfig();
        }

        MultivaluedMap<String,String> queryParams = new MultivaluedMapImpl();

        if (q.getChromosome() != null) {
            queryParams.add("chr", q.getChromosome().toString());
        }
        if (q.getPosition() != null) {
            queryParams.add("start", q.getPosition().toString()); // convert 0-based beacon protocol into 1-based VCF position
        }
        if (q.getPosition() != null) {
            queryParams.add("end", q.getPosition().toString());
        }
        if (q.getAllele() != null) {
            queryParams.add("alt", q.getAllele());
        }
        queryParams.add("beacon", "true");

        ClientResponse queryResult = restGetCall(baseurl,queryParams);

        if (queryResult.getStatus() != 200) {
            throw new IOException("REST status: " + queryResult.getStatus());
        }

        // JAXB Unmarshalling - works fine here
        return queryResult.getEntity(BeaconResponseSSVS.class);
    }
}