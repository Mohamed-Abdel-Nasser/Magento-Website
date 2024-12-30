package engine.dataDriven;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class ReadProperties {
    static Properties PROPERTIES;
    private static final String FILE_PATH = "src/main/resources/properties/";
    private static final String FILE_EXTENSION = ".properties";

    public static Properties readAllFiles() {
        PROPERTIES = new Properties(); // Reset properties
        List<String> files = List.of("paths");
        for (String file : files) {
            try (FileReader fileReader = new FileReader(FILE_PATH + file + FILE_EXTENSION)) {
                PROPERTIES.load(fileReader);
            } catch (Exception e) {
                System.err.println("Error reading property file: " + file + " - " + e.getMessage());
            }
        }
        return PROPERTIES;
    }


    public static String readCertainProperty(String fileName, String property) {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("File name cannot be null or blank.");
        }
        if (property == null || property.isBlank()) {
            throw new IllegalArgumentException("Property key cannot be null or blank.");
        }

        Properties tempProperties = new Properties();
        String filePath = FILE_PATH + fileName + FILE_EXTENSION;

        try (FileReader fileReader = new FileReader(filePath)) {
            tempProperties.load(fileReader);
        } catch (IOException e) {
            System.err.printf("Error reading property file: %s - %s%n", filePath, e.getMessage());
            return null;
        }

        String value = tempProperties.getProperty(property);
        if (value == null) {
            System.err.printf("Property '%s' not found in file: %s%n", property, filePath);
        }

        return value;
    }


    public static String getProperty(String property) {
        if (PROPERTIES == null) {
            throw new IllegalStateException("Properties have not been loaded yet.");
        }
        return PROPERTIES.getProperty(property);
    }

}
