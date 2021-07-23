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
    private String content_id;
    private String overview;
    private String mapx;
    private String mapy;
    private String title;
    private String firstimage;
    private String firstimage2;

    private String homepage;
    private String tel;

    private String contentType;

}
