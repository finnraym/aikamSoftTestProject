package ru.egorov.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ApplicationConfig {

    private static Properties properties;
    private static final String PROPERTIES_FILEPATH = getPropertiesFilepath();

    public static Properties getProperties() {
        if (properties == null) loadProperties();
        return properties;
    }

    public static void loadProperties() {
        if (properties == null) {
            properties = new Properties();
            try (InputStream stream = Files.newInputStream(Paths.get(PROPERTIES_FILEPATH))) {
                properties.load(stream);
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла конфигурации: " + e.getMessage());
            }
        }
    }

    private static String getPropertiesFilepath() {
        return "src" + File.separator + "main" + File.separator + "resources" + File.separator + "application.properties";
    }
}
