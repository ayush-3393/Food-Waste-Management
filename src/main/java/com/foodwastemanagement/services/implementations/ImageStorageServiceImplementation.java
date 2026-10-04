package com.foodwastemanagement.services.implementations;

import com.foodwastemanagement.services.ImageStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageStorageServiceImplementation
        implements ImageStorageService {

    private final Path uploadDirectory;

    public ImageStorageServiceImplementation() {

        this.uploadDirectory = Paths
                .get("uploads", "food-donations")
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.uploadDirectory);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not create image upload directory: " + this.uploadDirectory, e
            );
        }
    }

    @Override
    public String storeImage(MultipartFile image) {

        if (image == null || image.isEmpty()) {
            return null;
        }

        try {
            Files.createDirectories(uploadDirectory);
            String originalFileName = image.getOriginalFilename();
            String extension = "";
            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID() + extension;
            Path filePath = uploadDirectory.resolve(fileName);

            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return "food-donations/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Could not store image", e);
        }
    }
}
