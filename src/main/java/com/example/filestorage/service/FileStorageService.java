package com.example.filestorage.service;
import com.example.filestorage.model.FileMetadata;


import java.util.List;


public interface FileStorageService {
    FileMetadata saveFile(byte[] fileData, String fileName) throws Exception;
    List<FileMetadata> getAllFiles();
}