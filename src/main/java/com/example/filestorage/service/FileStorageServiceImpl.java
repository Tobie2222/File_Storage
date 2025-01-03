package com.example.filestorage.service;

import com.example.filestorage.model.FileMetadata;
import com.example.filestorage.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public FileMetadata saveFile(byte[] fileData, String fileName) throws Exception {
        // Lưu metadata và dữ liệu file vào database
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setFileName(fileName);
        fileMetadata.setFileSize((long) fileData.length); // Set file size
        fileMetadata.setFileData(fileData);
        return fileRepository.save(fileMetadata);
    }

    @Override
    public List<FileMetadata> getAllFiles() {
        return fileRepository.findAll();
    }

}