package com.work.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class XmlDTO {
    private String keyword;
    private String contentId;
    private String eventplace;
    private String agelimit;
    private String sponsor1tel;


}
