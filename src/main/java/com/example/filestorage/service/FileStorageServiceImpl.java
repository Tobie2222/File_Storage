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
            return existingFile.get(); // Hoặc throw exception tùy bạn
        }

        // Lưu metadata vào database
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setFileName(file.getOriginalFilename());
        fileMetadata.setFileSize(file.getSize());
        fileMetadata.setFileHash(fileHash);
        return fileRepository.save(fileMetadata);
    }

    @Override
    public List<FileMetadata> getAllFiles() {
        return fileRepository.findAll();
    }
    
    @Override
    public Optional<FileMetadata> getFileByHash(String fileHash) {
        return fileRepository.findByFileHash(fileHash);
    }
}