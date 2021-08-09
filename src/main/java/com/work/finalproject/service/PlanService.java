package com.work.finalproject.service;

import com.work.finalproject.dto.NoticeDTO;
import com.work.finalproject.dto.PlanDTO;
import com.work.finalproject.entity.notice_tbl;
import com.work.finalproject.entity.plan_tbl;

public interface PlanService {

    int register(PlanDTO dto);


    //DTO => Entity
    default plan_tbl dtoToEntity(PlanDTO dto) {
        plan_tbl entity = plan_tbl.builder()
                .no(dto.getNo())
                .username(dto.getUsername())
                .sDay(dto.getSDay())
                .eDay(dto.getEDay())
                .cDay(dto.getCDay())
                .content_id(dto.getContent_id())
                .build();
        return entity;
    }

    //Entity => DTO
    default PlanDTO entityToDto(plan_tbl entity) {
        PlanDTO dto = PlanDTO.builder()
                .no(entity.getNo())
                .username(entity.getUsername())
                .sDay(entity.getSDay())
                .eDay(entity.getEDay())
                .cDay(entity.getCDay())
                .content_id(entity.getContent_id())
                .build();
        return dto;
    }
}
