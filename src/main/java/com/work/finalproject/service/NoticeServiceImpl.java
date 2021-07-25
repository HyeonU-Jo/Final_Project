package com.work.finalproject.service;

import com.work.finalproject.dto.NoticeDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.work.finalproject.dto.PageRequestDTO;
import com.work.finalproject.dto.PageResultDTO;
import com.work.finalproject.entity.Qnotice_tbl;
import com.work.finalproject.entity.notice_tbl;
import com.work.finalproject.repository.notice_repository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
public class NoticeServiceImpl implements NoticeService {
    private final notice_repository repository;

    public NoticeServiceImpl(notice_repository repository) {

        this.repository = repository;
    }

    @Override
    public int register(NoticeDTO dto) {
        log.info("DTO~~~");
        log.info(dto);

        notice_tbl entity = dtoToEntity(dto);
        log.info(entity);

        repository.save(entity);

        return entity.getNo();
    }


    //목록 관련(검색 추가)
    @Override
    public PageResultDTO<NoticeDTO, notice_tbl> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("no").descending());
        //검색 조건 처리 : getSearch()의 반환 타입 BooleanBuilder
        BooleanBuilder booleanBuilder = getSearch(requestDTO);
        //Querydsl 사용하기 위한 수정
        Page<notice_tbl> result = repository.findAll(booleanBuilder, pageable);
        Function<notice_tbl, NoticeDTO> fn = (entity -> entityToDto(entity));
        return new PageResultDTO<>(result, fn);

    }

    //조회관련
    @Override
    public NoticeDTO read(int no) {
        Optional<notice_tbl> result = repository.findById(no);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    //수정관련
    @Override
    public void modify(NoticeDTO dto) {
        //업데이트 항목: 제목 / 내용
        Optional<notice_tbl> result = repository.findById(dto.getNo());
        if (result.isPresent()) {
            notice_tbl entity = result.get();
            entity.changeTitle(dto.getN_title());
            entity.changeContent(dto.getN_content());

            repository.save(entity);
        }
    }

    //삭제관련련
    @Override
    public void remove(int no) {

        repository.deleteById(no);
    }

    //검색 관련: NoticeServiceImpl자체선언메서드
    //검색 메서드는 현 클래스 내에서만 사용 :즉, getList() 내에서사용
    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        Qnotice_tbl qnotice_tbl = Qnotice_tbl.notice_tbl;

        //검색
        String keyword = requestDTO.getContent_id();

        BooleanExpression expression = qnotice_tbl.no.gt(0);
        booleanBuilder.and(expression);

        //검색 조건이 없을 경우
        if (type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        //검색조건이 존재
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) {
            conditionBuilder.or(qnotice_tbl.n_title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qnotice_tbl.n_content.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(qnotice_tbl.n_writer.contains(keyword));
        }
        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;

    }

}