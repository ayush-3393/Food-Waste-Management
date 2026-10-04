package com.foodwastemanagement.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {
    String storeImage(MultipartFile image);
}
