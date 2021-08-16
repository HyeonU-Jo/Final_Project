package com.work.finalproject.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.work.finalproject.dto.DiaryDTO;
import com.work.finalproject.dto.PageRequestDTO;
import com.work.finalproject.dto.PageResultDTO;
import com.work.finalproject.entity.Qdiary_tbl;
import com.work.finalproject.entity.diary_tbl;
import com.work.finalproject.repository.diary_repository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
public class DiaryServiceImpl implements DiaryService {

    private final diary_repository repository;

    public DiaryServiceImpl(diary_repository repository) {
        this.repository = repository;
    }


    @Override
    public int register(DiaryDTO dto) {
        diary_tbl entity = dtoToEntity(dto);
        repository.save(entity);
        return entity.getDno();
    }

    @Override
    public PageResultDTO<DiaryDTO, diary_tbl> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("dno").descending());
        //검색 조건 처리 : getSearch()의 반환 타입 BooleanBuilder
        BooleanBuilder booleanBuilder = getSearch(requestDTO);
        //Querydsl 사용하기 위한 수정
        Page<diary_tbl> result = repository.findAll(booleanBuilder, pageable);
        Function<diary_tbl, DiaryDTO> fn = (entity -> entityToDto(entity));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public DiaryDTO read(int dno) {
        Optional<diary_tbl> result = repository.findById(dno);
        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    public void modify(DiaryDTO dto) {
        Optional<diary_tbl> result = repository.findById(dto.getDno());
        if (result.isPresent()) {
            diary_tbl entity = result.get();
            entity.changeTitle(dto.getD_title());
            entity.changeContent(dto.getD_content());
            repository.save(entity);

        }
    }

    @Override
    public void remove(int dno) {
        repository.deleteById(dno);
    }

    //검색 관련: NoticeServiceImpl자체선언메서드
    //검색 메서드는 현 클래스 내에서만 사용 :즉, getList() 내에서사용
    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        Qdiary_tbl qdiary_tbl = Qdiary_tbl.diary_tbl;

        //검색
        String keyword = requestDTO.getKeyword();
        BooleanExpression expression = qdiary_tbl.dno.gt(0);
        booleanBuilder.and(expression);

        //검색 조건이 없을 경우
        if (type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        //검색조건이 존재
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            conditionBuilder.or(qdiary_tbl.d_title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qdiary_tbl.d_content.contains(keyword));
        }
        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }
}
