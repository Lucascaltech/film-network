package com.luca.film.file;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;
import static java.lang.System.currentTimeMillis;

/**
 * Service responsible for handling file storage operations.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${application.file.uploads.poster-cover-path}")
    private String fileUploadPath;

    /**
     * Saves an uploaded file in a user-specific directory.
     *
     * @param sourceFile the file to be saved, must not be null.
     * @param id         the identifier for the user or entity to associate with the file.
     * @return the path of the saved file, or null if the operation failed.
     */
    public String saveFile(@NotNull MultipartFile sourceFile, @NotNull String id) {
        final String fileUploadSubPath = "users" + separator + id;
        return uploadFile(sourceFile, fileUploadSubPath);
    }

    /**
     * Uploads a file to the specified subdirectory.
     *
     * @param sourceFile       the file to be uploaded, must not be null.
     * @param fileUploadSubPath the subdirectory where the file should be stored.
     * @return the full path of the uploaded file, or null if the upload failed.
     */
    private String uploadFile(@NotNull MultipartFile sourceFile, String fileUploadSubPath) {
        final String finalUploadPath = fileUploadPath + separator + fileUploadSubPath;
        File targetFolder = new File(finalUploadPath);

        // Create directory if it does not exist
        if (!targetFolder.exists()) {
            boolean folderCreated = targetFolder.mkdirs();
            if (!folderCreated) {
                log.warn("Failed to create the target folder: " + targetFolder);
                return null;
            }
        }

        final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());
        String targetFilePath = finalUploadPath + separator + currentTimeMillis() + "." + fileExtension;
        Path targetPath = Paths.get(targetFilePath);

        try {
            Files.write(targetPath, sourceFile.getBytes());
            log.info("File saved to: " + targetFilePath);
            return targetFilePath;
        } catch (IOException e) {
            log.error("File was not saved", e);
        }
        return null;
    }

    /**
     * Extracts the file extension from a given filename.
     *
     * @param fileName the name of the file, including the extension.
     * @return the extracted file extension in lowercase, or an empty string if none exists.
     */
    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }
}
