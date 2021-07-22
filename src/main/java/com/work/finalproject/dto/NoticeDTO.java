package com.work.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NoticeDTO {
    private int no;
    private String n_title;
    private String n_writer;
    private String n_content;
    private LocalDateTime regDate, modDate;
}


