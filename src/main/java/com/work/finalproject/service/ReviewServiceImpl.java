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


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public ReviewDTO read(int r_num){
        Optional<review_tbl> result = repository.findById(r_num);

        return result.isPresent() ? EntityToReview(result.get()): null;
    }

    @Override
    public void reviewModify(ReviewDTO reviewDTO) {
        Optional<review_tbl> result = repository.findById(reviewDTO.getR_num());

        if (result.isPresent()){
            review_tbl entity =result.get();
            entity.changeR_content(reviewDTO.getR_content());
            entity.changeR_rating(reviewDTO.getR_rating());
            entity.changeImage(reviewDTO.getImage());

            repository.save(entity);
        }
    }

    @Override
    public void deleteReview(int r_num) {
        repository.deleteById(r_num);
    }

    @Override
    public int reviewAvg(String content_id) {
        List<review_tbl> review_tbls = repository.findByContent_id(content_id);
        System.out.println("리뷰 합계 확인"+repository.sumR_rating(content_id));
        int sum = 0;
        if(review_tbls.size()==0){
            return 0;
        }else{
            for (int i = 0;i<review_tbls.size(); i++){
                if(review_tbls.size()!=0){
                    sum += review_tbls.get(i).getR_rating();
                }else {
                    return 0;
                }
            }
        }



        return sum/review_tbls.size();
    }


    @Override
    public PageResultDTO<ReviewDTO, review_tbl> rlist2(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("r_num"));

        BooleanBuilder booleanBuilder = getSearch(requestDTO);

        Page<review_tbl> result = repository.findAll(booleanBuilder, pageable);
        Function<review_tbl, ReviewDTO> fn = (entity -> EntityToReview(entity));



        return new PageResultDTO<>(result, fn);
    }

    @Override
    public List<ReviewDTO> reviewList(String content_id) {
        List<review_tbl> list = repository.findByContent_id(content_id);
        List<ReviewDTO> dtoList = new ArrayList<ReviewDTO>();

        for(int i = 0; i<list.size(); i++){
            ReviewDTO dto = new ReviewDTO();
            dto.setR_num(list.get(i).getR_num());
            dto.setContent_id(list.get(i).getContent_id());
            dto.setR_content(list.get(i).getR_content());
            dto.setR_rating(list.get(i).getR_rating());
            dto.setImage(list.get(i).getImage());
            dtoList.add(dto);
        }

        return dtoList;
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
