package com.automation.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static ConfigManager manager;
    private static final Properties prop = new Properties();

    private ConfigManager() {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config.properties");
        }
    }

    public static ConfigManager getInstance() {
        if (manager == null) {
            synchronized (ConfigManager.class) {
                if (manager == null) {
                    manager = new ConfigManager();
                }
            }
        }
        return manager;
    }

    public String getString(String key) {
        return System.getProperty(key, prop.getProperty(key));
    }
}
