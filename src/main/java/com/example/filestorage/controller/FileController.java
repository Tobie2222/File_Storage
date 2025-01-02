package com.example.filestorage.controller;

import com.example.filestorage.model.FileMetadata;
import com.example.filestorage.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileRepository fileRepository;

    private final Path storagePath = Paths.get("uploads");

    public FileController() throws Exception {
        Files.createDirectories(storagePath);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Tính hash SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] fileHashBytes = digest.digest(file.getBytes());
            StringBuilder hashString = new StringBuilder();
            for (byte b : fileHashBytes) {
                hashString.append(String.format("%02x", b));
            }
            String fileHash = hashString.toString();

            // Kiểm tra file trùng lặp
            Optional<FileMetadata> existingFile = fileRepository.findByFileHash(fileHash);
            if (existingFile.isPresent()) {
                return ResponseEntity.ok(Map.of(
                        "message", "File already exists",
                        "file", existingFile.get()
                ));
            }

            // Lưu file vào thư mục
            Path targetLocation = storagePath.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), targetLocation);

            // Lưu metadata vào database
            FileMetadata fileMetadata = new FileMetadata();
            fileMetadata.setFileName(file.getOriginalFilename());
            fileMetadata.setFileSize(file.getSize());
            fileMetadata.setFileHash(fileHash);
            fileRepository.save(fileMetadata);

            // Trả về thông tin file
            return ResponseEntity.ok(Map.of(
                    "message", "File uploaded successfully",
                    "file", fileMetadata
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of(
                    "message", "Error uploading file",
                    "error", e.getMessage()
            ));
        }
    }

    @GetMapping
    public ResponseEntity<?> listFiles() {
        return ResponseEntity.ok(fileRepository.findAll());
    }
}