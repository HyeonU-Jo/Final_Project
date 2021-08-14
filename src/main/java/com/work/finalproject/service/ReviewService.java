package com.work.finalproject.service;


import com.work.finalproject.dto.ReviewDTO;
import com.work.finalproject.dto.PageRequestDTO;
import com.work.finalproject.dto.PageResultDTO;
import com.work.finalproject.entity.review_tbl;

import java.util.List;

public interface ReviewService {

    void reviewWrite(ReviewDTO dto);

    List<ReviewDTO> reviewList(String content_id);

    ReviewDTO read(int r_num);

    void reviewModify(ReviewDTO reviewDTO);

    void deleteReview(int r_num);

    int reviewAvg(String content_id);



    default review_tbl reviewToEntity(ReviewDTO dto){
        System.out.println("리뷰 투 엔티티 값 확인" + dto.getContent_id());
        review_tbl entity = review_tbl.builder()
                .r_num(dto.getR_num())
                .content_id(dto.getContent_id())
                .r_content(dto.getR_content())
                .r_rating(dto.getR_rating())
                .image(dto.getImage())
                .username(dto.getUsername())
                .name(dto.getName())
                .build();
        return entity;
    }

    default ReviewDTO EntityToReview(review_tbl entity){
        ReviewDTO dto = ReviewDTO.builder()
                .r_num(entity.getR_num())
                .r_content(entity.getR_content())
                .r_rating(entity.getR_rating())
                .content_id(entity.getContent_id())
                .image(entity.getImage())
                .username(entity.getUsername())
                .name(entity.getName())
                .build();
        return dto;
    }

}
