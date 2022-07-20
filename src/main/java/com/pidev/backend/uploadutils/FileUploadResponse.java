package com.pidev.backend.uploadutils;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class FileUploadResponse {
    private String fileName;
    private String downloadUri;
    private long size;
}