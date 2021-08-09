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
                .p_sday(dto.getP_sday())
                .p_eday(dto.getP_eday())
                .p_cday(dto.getP_cday())
                .content_id(dto.getContent_id())
                .build();
        return entity;
    }

    //Entity => DTO
    default PlanDTO entityToDto(plan_tbl entity) {
        PlanDTO dto = PlanDTO.builder()
                .no(entity.getNo())
                .username(entity.getUsername())
                .p_sday(entity.getP_sday())
                .p_eday(entity.getP_eday())
                .p_cday(entity.getP_cday())
                .content_id(entity.getContent_id())
                .build();
        return dto;
    }
}
