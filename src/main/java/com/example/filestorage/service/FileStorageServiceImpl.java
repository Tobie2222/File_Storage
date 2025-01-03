package com.example.filestorage.service;

import com.example.filestorage.model.FileMetadata;
import com.example.filestorage.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public FileMetadata saveFile(MultipartFile file) throws Exception {


        // Lưu metadata vào database
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setFileName(file.getOriginalFilename());
        fileMetadata.setFileSize(file.getSize());
        fileMetadata.setFileHash(file.getBytes());
        return fileRepository.save(fileMetadata);
    }

    @Override
    public List<FileMetadata> getAllFiles() {
        return fileRepository.findAll();
    }
    @Override
    public Optional<FileMetadata> getFileById(Long id) {
        return fileRepository.findById(id);
    }

}