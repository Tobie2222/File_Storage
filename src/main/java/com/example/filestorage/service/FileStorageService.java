package com.example.filestorage.service;

import com.example.filestorage.model.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface FileStorageService {
    FileMetadata saveFile(MultipartFile file) throws Exception;
    List<FileMetadata> getAllFiles();
    Optional<FileMetadata> getFileById(Long id);
}