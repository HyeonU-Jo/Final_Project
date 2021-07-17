package com.work.finalproject.service;

import com.work.finalproject.dto.ReviewDTO;
import com.work.finalproject.entity.review_tbl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import com.work.finalproject.repository.review_repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<ReviewDTO> rlist2(String content_id) {



        return null;
    }





}
