package com.orchasp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.orchasp.dto.FileResponseDto;

public interface FileService {

    FileResponseDto uploadFile(MultipartFile file) throws IOException;

    List<FileResponseDto> getAllFiles();
}