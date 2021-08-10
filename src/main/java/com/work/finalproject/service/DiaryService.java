package com.work.finalproject.service;

import com.work.finalproject.dto.DiaryDTO;
import com.work.finalproject.dto.PageRequestDTO;
import com.work.finalproject.dto.PageResultDTO;
import com.work.finalproject.entity.diary_tbl;

public interface DiaryService{
    //글작성
    int register(DiaryDTO dto);

    //목록관련
    PageResultDTO<DiaryDTO, diary_tbl> getList(PageRequestDTO requestDTO);

    //조회관련
    DiaryDTO read(int dno);

    //수정관련
    void modify(DiaryDTO dto);

    //삭제관련
    void remove(int dno);

    //DTO => Entity
    default diary_tbl dtoToEntity(DiaryDTO dto) {
        diary_tbl entity = diary_tbl.builder()
                .dno(dto.getDno())
                .d_title(dto.getD_title())
                .d_username(dto.getD_username())
                .d_content(dto.getD_content())
                .d_image(dto.getD_image())
                .build();
        return entity;
    }

    //Entity => DTO
    default DiaryDTO entityToDto(diary_tbl entity) {
        DiaryDTO dto = DiaryDTO.builder()
                .dno(entity.getDno())
                .d_title(entity.getD_title())
                .d_username(entity.getD_username())
                .d_content(entity.getD_content())
                .d_image(entity.getD_image())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return dto;
    }
}
