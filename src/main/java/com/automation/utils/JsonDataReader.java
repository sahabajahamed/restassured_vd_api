package com.automation.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JsonDataReader {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Reads JSON file and converts it to a List of specified type
     * 
     * @param filePath      - path to JSON file relative to resources folder
     * @param typeReference - TypeReference for the target type
     * @param <T>           - Generic type parameter
     * @return List of objects of type T
     */
    public static <T> List<T> readJsonAsList(String filePath, TypeReference<List<T>> typeReference) {
        try {
            // Try to read from classpath (resources folder)
            InputStream inputStream = JsonDataReader.class.getClassLoader().getResourceAsStream(filePath);

            if (inputStream != null) {
                return objectMapper.readValue(inputStream, typeReference);
            } else {
                // If not found in classpath, try as absolute path
                File file = new File(filePath);
                if (file.exists()) {
                    return objectMapper.readValue(file, typeReference);
                } else {
                    throw new RuntimeException("File not found: " + filePath);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON file: " + filePath, e);
        }
    }

    /**
     * Reads JSON file and converts it to a single object of specified type
     * 
     * @param filePath - path to JSON file relative to resources folder
     * @param clazz    - Class type of the target object
     * @param <T>      - Generic type parameter
     * @return Object of type T
     */
    public static <T> T readJsonAsObject(String filePath, Class<T> clazz) {
        try {
            InputStream inputStream = JsonDataReader.class.getClassLoader().getResourceAsStream(filePath);

            if (inputStream != null) {
                return objectMapper.readValue(inputStream, clazz);
            } else {
                File file = new File(filePath);
                if (file.exists()) {
                    return objectMapper.readValue(file, clazz);
                } else {
                    throw new RuntimeException("File not found: " + filePath);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON file: " + filePath, e);
        }
    }
}
