package com.luca.film.file;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utility class for file-related operations.
 */
@Slf4j
public class FileUtils {

    /**
     * Reads a file from the given location and returns its content as a byte array.
     *
     * @param fileUrl the file path as a string, must not be blank.
     * @return a byte array containing the file's content, or null if the file does not exist or an error occurs.
     */
    public static byte[] readFileFromLocation(String fileUrl) {
        if (StringUtils.isBlank(fileUrl)) {
            return null;
        }
        try {
            Path filePath = new File(fileUrl).toPath();
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            log.warn("No file found at the path: {}", fileUrl);
        }
        return null;
    }
}
