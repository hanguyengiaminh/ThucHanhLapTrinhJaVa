package com.hutech.BaiTap3Java.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
    // Thư mục gốc để lưu trữ ảnh
    private final Path root = Paths.get("src/main/resources/static/images");

    public FileStorageService() {
        try {
            // Tạo thư mục nếu nó chưa tồn tại
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        try {
            // Tạo tên file duy nhất để tránh bị ghi đè
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            // Lưu file vào thư mục
            Files.copy(file.getInputStream(), this.root.resolve(uniqueFileName));

            // Trả về đường dẫn tương đối để lưu vào database
            return "/images/" + uniqueFileName;
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public void deleteFile(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return;
        }
        try {
            // Lấy tên file từ đường dẫn
            String filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            Path file = root.resolve(filename);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            System.err.println("Failed to delete image: " + e.getMessage());
        }
    }
}