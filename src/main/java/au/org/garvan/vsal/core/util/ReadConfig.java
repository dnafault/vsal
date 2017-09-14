package au.org.garvan.vsal.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Config file utilities.
 *
 * @author Dmitry Degrave (dmeetry@gmail.com)
 * @version 1.0
 */
public class ReadConfig {
    private static final String propFileName = "vsal.properties";
    private static final Properties prop = readConfig();

    private static Properties readConfig() {
        Properties p = new Properties();
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFileName)) {
            if (inputStream != null) {
                p.load(inputStream);
            } else {
                System.out.println("Can't read properties from " + propFileName + ". Setting up Kudu Master as localhost:7051");
                p.setProperty("kuduMaster","localhost:7051");
            }
        } catch (IOException e) {
            System.out.println("Can't read properties from " + propFileName + ". Setting up Kudu Master as localhost:7051");
            p.setProperty("kuduMaster","localhost:7051");
        }
        return p;
    }

    public static Properties getProp() {
        return prop;
    }
}
