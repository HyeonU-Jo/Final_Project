package com.work.finalproject.service;


import com.work.finalproject.dto.ReviewDTO;
import com.work.finalproject.entity.review_tbl;

public interface ReviewService {

    void reviewWrite(ReviewDTO dto);


    default review_tbl reviewToEntity(ReviewDTO dto){
        System.out.println("리뷰 투 엔티티 값 확인" + dto.getContent_id());
        review_tbl entity = review_tbl.builder()
                .r_num(dto.getR_num())
                .content_id(dto.getContent_id())
                .r_content(dto.getR_content())
                .r_rating(dto.getR_rating())
                .build();
        return entity;
    }

}
