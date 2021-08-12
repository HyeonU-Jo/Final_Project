package com.work.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DiaryDTO {
    private int dno;
    private String d_title;
    private String d_username;
    private String d_content;
    private LocalDateTime regDate, modDate;
    private String d_image;
    private byte[] d_imageByte;
    private MultipartFile d_imageFile;
}
