package com.example.filestorage.controller;

import com.example.filestorage.model.FileMetadata;
import com.example.filestorage.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            FileMetadata savedFile = fileStorageService.saveFile(file);
            return ResponseEntity.ok(Map.of(
                    "message", "File uploaded successfully",
                    "file", savedFile
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
        return ResponseEntity.ok(fileStorageService.getAllFiles());
    }

    @GetMapping("/home")
    public Map<String, String> home() {
        return Map.of("message", "Home");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFileById(@PathVariable Long id) {
        return ResponseEntity.ok(fileStorageService.getFileById(id));
    }
}