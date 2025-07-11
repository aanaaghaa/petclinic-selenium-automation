package com.anagha.petclinic.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class to read configuration values from config.properties file.
 * This class loads the properties once at class loading time and provides 
 * reusable methods to fetch string or integer property values.
 */
public class ConfigReader {
	
	// Properties object to hold key-value pairs loaded from the config file
    private static Properties properties = new Properties();
    
    // Static block to initialize the properties when the class is loaded
    static {
        try {
        	// Load the properties file located in src/test/resources/
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            properties.load(fis);
        } catch (IOException e) {
        	// If file loading fails, throw runtime exception to fail fast
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    //Returns the property value as integer for the given key
    public static String get(String key) {
        return properties.getProperty(key);
    }
    
    //Returns the property value corresponding to the given key
    public static int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

}
