package com.work.finalproject.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.work.finalproject.dto.ReviewDTO;
import com.work.finalproject.dto.PageRequestDTO;
import com.work.finalproject.dto.PageResultDTO;
import com.work.finalproject.entity.Qreview_tbl;
import com.work.finalproject.entity.review_tbl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.work.finalproject.repository.review_repository;


import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final review_repository repository;

    @Override
    public void reviewWrite(ReviewDTO dto){
        review_tbl entity = reviewToEntity(dto);
        repository.save(entity);
    }

    @Override
    public PageResultDTO<ReviewDTO, review_tbl> rlist2(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("r_num"));

        BooleanBuilder booleanBuilder = getSearch(requestDTO);

        Page<review_tbl> result = repository.findAll(booleanBuilder, pageable);
        Function<review_tbl, ReviewDTO> fn = (entity -> EntityToReview(entity));



        return new PageResultDTO<>(result, fn);
    }


    private BooleanBuilder getSearch(PageRequestDTO requestDTO){

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        Qreview_tbl qreview_tbl = Qreview_tbl.review_tbl;

        String content_id = requestDTO.getContent_id();


        BooleanExpression expression = qreview_tbl.r_num.gt(0L);
        booleanBuilder.and(expression);

        BooleanBuilder conditionBuilder = new BooleanBuilder();

        conditionBuilder.or(qreview_tbl.content_id.contains(content_id));

        return booleanBuilder;
    }


}
