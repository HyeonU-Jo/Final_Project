package com.work.finalproject.service;

import com.work.finalproject.dto.LikeDTO;
import com.work.finalproject.dto.ReviewDTO;
import com.work.finalproject.entity.review_tbl;
import com.work.finalproject.entity.like_tbl;

import java.util.List;

public interface LikeService {
    List<LikeDTO> getLikeList();
    String like(LikeDTO dto);
    String likeCheck(LikeDTO dto);

    String likeBoolean(LikeDTO dto);

    List<LikeDTO> likeList(LikeDTO dto);

    void deleteLike(LikeDTO dto);

    default like_tbl dtoToEntity(LikeDTO dto){
        like_tbl entity = like_tbl.builder()
                .no(dto.getNo())
                .content_id(dto.getContent_id())
                .username(dto.getUsername())
                .build();
        return entity;
    }
}
