package com.om.springboot.util;

import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationUtil {

    // static variable single_instance of type Singleton
    private static ConfigurationUtil configurationUtil=null;

    private Properties appProps = null;

    // private constructor restricted to this class itself
    private ConfigurationUtil()
    {
        try {
            String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            String appConfigPath = rootPath + "custom.properties";
            appProps = new Properties();
            ClassPathResource classPathResource = new ClassPathResource("custom.properties");
            //appProps.load(new FileInputStream(appConfigPath));
            appProps.load(classPathResource.getInputStream());
            System.out.println("appProps ="+appProps);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    // static method to create instance of Singleton class
    public static ConfigurationUtil ConfigurationUtil()
    {
        // To ensure only one instance is created
        if (configurationUtil == null)
        {
            configurationUtil = new ConfigurationUtil();
        }
        return configurationUtil;
    }

    public String getProperty(String key){
        return appProps.getProperty(key);
    }
}
