package com.orchasp.dto;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileResponseDto {

    private Long id;
    private String uuid;
    private String originalFileName;
    private String storedFileName;
    private String filePath;
    private String imageUrl;
    private LocalDateTime uploadedAt;
    
}