package com.example.filestorage.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity // Thêm annotation này
@Table(name = "file_metadata", schema = "file_storage")
public class FileMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "file_name", nullable = false, unique = true)
    private String fileName;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "file_hash", nullable = false, unique = true, length = 64)
    private String fileHash;
}