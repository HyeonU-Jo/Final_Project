package com.work.finalproject.service;

import com.work.finalproject.dto.NoticeDTO;
import com.work.finalproject.dto.PlanDTO;
import com.work.finalproject.entity.notice_tbl;
import com.work.finalproject.entity.plan_tbl;
import com.work.finalproject.repository.plan_repository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PlanServiceImpl implements PlanService{

    private final plan_repository repository;

    public PlanServiceImpl(plan_repository repository) {
        this.repository = repository;
    }

    @Override
    public int register(PlanDTO dto) {
        log.info("dto~~");
        log.info(dto);
        plan_tbl entity = dtoToEntity(dto);
        log.info(entity);
        repository.save(entity);
        return entity.getNo();
    }


}
