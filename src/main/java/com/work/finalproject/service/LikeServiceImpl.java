package com.work.finalproject.service;

import com.work.finalproject.dto.LikeDTO;
import com.work.finalproject.entity.like_tbl;
import com.work.finalproject.repository.like_repository;
import com.work.finalproject.repository.plan_repository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class LikeServiceImpl implements LikeService {

    private final like_repository likeRepository;
    private final plan_repository planRepository;

    public LikeServiceImpl(like_repository likeRepository, plan_repository planRepository) {
        this.likeRepository = likeRepository;
        this.planRepository = planRepository;
    }

    @Override
    public List<LikeDTO> getLikeList() {
        List<like_tbl> likes=likeRepository.findAll();
        List<LikeDTO> likeDtoList=new ArrayList<>();
        for(like_tbl like : likes){
            LikeDTO likeDto=LikeDTO.builder()
                    .no(like.getNo())
                    .f_like(like.getF_like())
                    .s_like(like.getS_like())
                    .t_like(like.getT_like())
                    .build();
            likeDtoList.add(likeDto);
        }
        return likeDtoList;
    }
}
