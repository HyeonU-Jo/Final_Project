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
    public List<LikeDTO> likeList(LikeDTO likeDTO){
        List<like_tbl> like = likeRepository.findByUsername(likeDTO.getUsername());
        List<LikeDTO> likeList = new ArrayList<>();
        for(int i = 0; i<like.size(); i++){
            LikeDTO likeDTO1 = new LikeDTO();
            likeDTO1.setContent_id(like.get(i).getContent_id());
            likeDTO1.setUsername(like.get(i).getUsername());
            likeList.add(likeDTO1);
        }
        return likeList;
    }



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
    public String likeCheck(LikeDTO dto){
        List<like_tbl> likeDTOS = likeRepository.findByContent_idAndUsername(dto.getContent_id(), dto.getUsername());
        System.out.println("라이크 체크 : " + dto.getContent_id());
        if (likeDTOS.isEmpty()){
            return "찜 하기";
        } else {
            return "찜 취소";
        }

    }



    @Override
    public String like(LikeDTO dto) {
        List<like_tbl> likeDTOS = likeRepository.findByContent_idAndUsername(dto.getContent_id(), dto.getUsername());
        System.out.println("진짜 라이크 : " + dto.getContent_id());
        if (likeDTOS.isEmpty()){
            like_tbl entity = dtoToEntity(dto);
            likeRepository.save(entity);
            return "찜 취소";
        } else {
            likeRepository.deleteByContent_idAndUsername(dto.getContent_id(), dto.getUsername());
            return "찜 하기";
        }

    }

    @Override
    public String likeBoolean(LikeDTO dto) {

        return null;
    }
}
