package com.work.finalproject.service;

import com.work.finalproject.dto.NoticeDTO;
import com.work.finalproject.entity.notice_tbl;

public interface PlanService {

    int register(NoticeDTO dto);


    //DTO => Entity
    default notice_tbl dtoToEntity(NoticeDTO dto) {
        notice_tbl entity = notice_tbl.builder()
                .no(dto.getNo())
                .n_title(dto.getN_title())
                .n_content(dto.getN_content())
                .n_writer(dto.getN_writer())
                .build();

        return entity;
    }

    //Entity => DTO
    default NoticeDTO entityToDto(notice_tbl entity) {
        NoticeDTO dto = NoticeDTO.builder()
                .no(entity.getNo())
                .n_title(entity.getN_title())
                .n_content(entity.getN_content())
                .n_writer(entity.getN_writer())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }
}
