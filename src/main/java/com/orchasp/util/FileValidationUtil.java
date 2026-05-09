package com.orchasp.util;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.orchasp.exception.InvalidFileException;

public class FileValidationUtil {

    private static final List<String> ALLOWED_TYPES = List.of(
            "image/jpeg",
            "image/jpg",
            "image/png"
    );

    public static void validateFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new InvalidFileException("File is empty");
        }

        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new InvalidFileException(
                    "Only JPG, JPEG, PNG files are allowed"
            );
        }
    }
}