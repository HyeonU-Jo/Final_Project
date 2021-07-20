package com.work.finalproject.service;


import com.work.finalproject.dto.ReviewDTO;
import com.work.finalproject.dto.PageRequestDTO;
import com.work.finalproject.dto.PageResultDTO;
import com.work.finalproject.entity.review_tbl;

public interface ReviewService {

    void reviewWrite(ReviewDTO dto);

    PageResultDTO<ReviewDTO, review_tbl> rlist2(PageRequestDTO requestDTO);



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

    default ReviewDTO EntityToReview(review_tbl entity){
        ReviewDTO dto = ReviewDTO.builder()
                .r_num(entity.getR_num())
                .r_content(entity.getR_content())
                .r_rating(entity.getR_rating())
                .content_id(entity.getContent_id())
                .build();
        return dto;
    }

}
