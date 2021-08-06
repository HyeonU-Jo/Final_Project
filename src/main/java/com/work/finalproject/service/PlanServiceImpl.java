package com.work.finalproject.service;

import com.work.finalproject.dto.NoticeDTO;
import com.work.finalproject.entity.notice_tbl;

public class PlanServiceImpl implements PlanService{
    @Override
    public int register(NoticeDTO dto) {
        return 0;
    }

    @Override
    public notice_tbl dtoToEntity(NoticeDTO dto) {
        return PlanService.super.dtoToEntity(dto);
    }

    @Override
    public NoticeDTO entityToDto(notice_tbl entity) {
        return PlanService.super.entityToDto(entity);
    }
}
