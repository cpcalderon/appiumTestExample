package utils;

import io.appium.java_client.AppiumDriver;
import org.apache.log4j.Level;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class UtilsFunctions {

    /**
     * Method to wait for a number of seconds.
     * @param seconds to wait
     */
    public static void waitSeconds(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
    }


    /**
     * Method to load test properties file.
     * @param fileName string path to load
     * @return map with the different properties.
     */
    public static Map<String,String> propertiesLoader(String fileName) {
        Properties prop = new Properties();
        InputStream input = null;
        Map<String, String> propertiesMap = new HashMap<>();

        try {

            input = new FileInputStream(fileName);

            // load a properties file
            prop.load(input);

            // Store all values into a map
            for(String key: prop.stringPropertyNames()){
                propertiesMap.put(key, prop.getProperty(key));
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Return a map with all the properties
        return propertiesMap;

    }

    /**
     * Method to change the Appium driver context to WebView.
     * @param driver driver to load
     */
    public static void changeToWebViewContext(AppiumDriver driver){
        Set<String> contextNames = driver.getContextHandles();
        driver.context((String) contextNames.toArray()[1]); // set context to WEBVIEW
    }

    /**
     * @param level sets the desired level between ALL, DEBUG, TRACE, INFO, WARN, ERROR, FATAL
     */
    public static  void setLoggerLevel(String level){
        switch(level.toUpperCase()) {
            case "ALL":
                MyLogger.log.setLevel(Level.ALL);
                break;
            case "DEBUG":
                MyLogger.log.setLevel(Level.DEBUG);
                break;
            case "TRACE":
                MyLogger.log.setLevel(Level.TRACE);
                break;
            case "INFO":
                MyLogger.log.setLevel(Level.INFO);
                break;
            case "WARN":
                MyLogger.log.setLevel(Level.WARN);
                break;
            case "ERROR":
                MyLogger.log.setLevel(Level.ERROR);
                break;
            case "FATAL":
                MyLogger.log.setLevel(Level.FATAL);
                break;
        }
    }
}