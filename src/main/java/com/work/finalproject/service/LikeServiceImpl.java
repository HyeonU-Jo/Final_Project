package com.work.finalproject.service;

import com.work.finalproject.dto.LikeDTO;
import com.work.finalproject.dto.ReviewDTO;
import com.work.finalproject.entity.like_tbl;
import com.work.finalproject.entity.review_tbl;
import com.work.finalproject.repository.like_repository;
import com.work.finalproject.repository.plan_repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import com.work.finalproject.entity.like_tbl;

@Service
@Log4j2
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {


    private final like_repository likeRepository;
    private final plan_repository planRepository;



    @Override
    public List<LikeDTO> getLikeList() {
        List<like_tbl> likes=likeRepository.findAll();
        List<LikeDTO> likeDtoList=new ArrayList<>();
        for(like_tbl like : likes){
            LikeDTO likeDto=LikeDTO.builder()
                    .no(like.getNo())
                    .content_id(like.getContent_id())
                    .username(like.getUsername())
                    .build();
            likeDtoList.add(likeDto);
        }
        return likeDtoList;
    }



    @Override
    public void like(LikeDTO dto) {
        like_tbl entity = dtoToEntity(dto);
        likeRepository.save(entity);
    }
}
