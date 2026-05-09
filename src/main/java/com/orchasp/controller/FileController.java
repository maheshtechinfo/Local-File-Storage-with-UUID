package com.orchasp.controller;

 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orchasp.dto.ApiResponse;
import com.orchasp.dto.FileResponseDto;
import com.orchasp.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
//@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    // Upload File API
    @PostMapping(value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FileResponseDto>> uploadFile(
            @RequestParam("file") MultipartFile file)
            throws IOException {

        FileResponseDto uploadedFile =
                fileService.uploadFile(file);

        ApiResponse<FileResponseDto> response =
                ApiResponse.<FileResponseDto>builder()
                        .success(true)
                        .message("File uploaded successfully")
                        .data(uploadedFile)
                        .build();

        return ResponseEntity.ok(response);
    }

    // Fetch All Files API
    @GetMapping
    public ResponseEntity<ApiResponse<List<FileResponseDto>>>
    getAllFiles() {

        List<FileResponseDto> files =
                fileService.getAllFiles();

        ApiResponse<List<FileResponseDto>> response =
                ApiResponse.<List<FileResponseDto>>builder()
                        .success(true)
                        .message("Files fetched successfully")
                        .data(files)
                        .build();

        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewImage(
            @PathVariable String fileName) throws IOException {

        Path imagePath = Paths.get("uploads", fileName);

        Resource resource =
                new UrlResource(imagePath.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("File not found");
        }

        String contentType =
                Files.probeContentType(imagePath);

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(contentType)
                )
                .body(resource);
    }
}