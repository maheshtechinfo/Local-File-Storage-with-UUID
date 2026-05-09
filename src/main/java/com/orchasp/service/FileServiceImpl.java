package com.orchasp.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.orchasp.dto.FileResponseDto;
import com.orchasp.entity.FileEntity;
import com.orchasp.repository.FileRepository;
import com.orchasp.util.FileValidationUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

	private final FileRepository fileRepository;

	@Value("${file.upload-dir}")
	private String uploadDir;

	@Override
	public FileResponseDto uploadFile(MultipartFile file) throws IOException {

		// Validate file
		FileValidationUtil.validateFile(file);

		// Generate UUID
		String uuid = UUID.randomUUID().toString().substring(0, 8);

		// Original filename
		String originalFileName = file.getOriginalFilename();

		// File extension
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

		// Stored filename
		String storedFileName = uuid + "_" + originalFileName;

		// Final file path
		Path filePath = Paths.get(uploadDir, storedFileName);

		// Save file locally
		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

		// Save metadata in DB
		FileEntity fileEntity = FileEntity.builder().uuid(uuid).originalFileName(originalFileName)
				.storedFileName(storedFileName).filePath(filePath.toString()).uploadedAt(LocalDateTime.now()).build();

		FileEntity savedFile = fileRepository.save(fileEntity);

		// Convert Entity -> DTO
		return FileResponseDto.builder().id(savedFile.getId()).uuid(savedFile.getUuid())
				.originalFileName(savedFile.getOriginalFileName()).storedFileName(savedFile.getStoredFileName())
				.filePath(savedFile.getFilePath())

				// ADD THIS
				.imageUrl("http://localhost:8081/api/files/view/" + savedFile.getStoredFileName())

				.uploadedAt(savedFile.getUploadedAt()).build();
	}

	@Override
	public List<FileResponseDto> getAllFiles() {

		return fileRepository.findAll().stream()
				.map(file -> FileResponseDto.builder().id(file.getId()).uuid(file.getUuid())
						.originalFileName(file.getOriginalFileName()).storedFileName(file.getStoredFileName())
						.filePath(file.getFilePath())

						// ADD THIS
						.imageUrl("http://localhost:8081/api/files/view/" + file.getStoredFileName())

						.uploadedAt(file.getUploadedAt()).build())
				.toList();
	}
}