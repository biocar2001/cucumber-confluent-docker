package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystemNotFoundException;
import java.util.Properties;

public class UtilsKafka
{
    private UtilsKafka()
    {
    }

    public static Properties getConfigProperties() throws IOException {

        Properties properties = new Properties();
        String propFileName = "config.properties";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try(InputStream resourceStream = loader.getResourceAsStream(propFileName)) {
            properties.load(resourceStream);
        }

        return properties;
    }
}
