package my.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProperties {
    public static Properties loadProperties(String propertyFile) {
        Properties properties = new Properties();
        try {
            InputStream is = JedisTool.class.getClassLoader().getResourceAsStream(propertyFile);
            if(is == null){
                is = JedisTool.class.getClassLoader().getResourceAsStream("properties/" + propertyFile);
            }
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 根据key值取得对应的value值
     *
     * @param key
     * @return
     */
    public static String getValue(String propertyFile, String key) {
        Properties properties = loadProperties(propertyFile);
        return properties.getProperty(key);
    }
}
