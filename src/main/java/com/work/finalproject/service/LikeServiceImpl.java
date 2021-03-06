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
        List<like_tbl> like = likeRepository.findByUsername(likeDTO.getUsername(), likeDTO.getLike_type());
        List<LikeDTO> likeList = new ArrayList<>();
        for(int i = 0; i<like.size(); i++){
            LikeDTO likeDTO1 = new LikeDTO();
            likeDTO1.setContent_id(like.get(i).getContent_id());
            likeDTO1.setUsername(like.get(i).getUsername());
            likeDTO1.setLike_type(like.get(i).getLike_type());
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

        List<like_tbl> likeDTOS = likeRepository.findByContent_idAndUsername(dto.getContent_id(), dto.getUsername(), dto.getLike_type());

        System.out.println("????????? ?????? : " + dto.getContent_id());


            if(likeDTOS.isEmpty()&&dto.getLike_type().equals("1")){
                return "far fa-star";
            }else if (likeDTOS.isEmpty()&&dto.getLike_type().equals("2")){
                return "far fa-star";
            }else if(likeDTOS.get(0).getLike_type().equals("1")){
                return "fas fa-star";
            }else if(likeDTOS.get(0).getLike_type().equals("2")){
                return "fas fa-star";
            }else {
                return "??????";
            }

       /* if (likeDTOS.isEmpty()){
            return "??? ??????";
        } else{
            return "??? ??????";
        }*/

    }



    @Override
    public String like(LikeDTO dto) {

        if(dto.getLike_type().equals("1")){
            List<like_tbl> likeDTOS = likeRepository.findByContent_idAndUsername(dto.getContent_id(), dto.getUsername(), dto.getLike_type());
            System.out.println("?????? ????????? : " + dto.getContent_id());


            if(likeDTOS.isEmpty()){
                dto.setLike_type("1");
                like_tbl entity = dtoToEntity(dto);
                likeRepository.save(entity);
                return "fas fa-star";
            }else if(likeDTOS.get(0).getLike_type().equals("1")){
                likeRepository.deleteByContent_idAndUsername(dto.getContent_id(), dto.getUsername(), dto.getLike_type());
                return "far fa-star";
            }else {
                return "?????????";
            }
        }else{
            List<like_tbl> likeDTOS = likeRepository.findByContent_idAndUsername(dto.getContent_id(), dto.getUsername(), dto.getLike_type());
            System.out.println("?????? ????????? : " + dto.getContent_id());


            if(likeDTOS.isEmpty()){
                dto.setLike_type("2");
                like_tbl entity = dtoToEntity(dto);
                likeRepository.save(entity);
                return "fas fa-star";
            }else if(likeDTOS.get(0).getLike_type().equals("2")){
                likeRepository.deleteByContent_idAndUsername(dto.getContent_id(), dto.getUsername(), dto.getLike_type());
                return "far fa-star";
            }else {
                return "?????????";
            }
        }




        /*if (likeDTOS.isEmpty()){
            like_tbl entity = dtoToEntity(dto);
            likeRepository.save(entity);
            return "??? ??????";
        } else {
            likeRepository.deleteByContent_idAndUsername(dto.getContent_id(), dto.getUsername());
            return "??? ??????";
        }*/

    }

    @Override
    public void deleteLike(LikeDTO dto){
        System.out.println(dto.getLike_type() + "Ssssssssssssssssssssssssss");

        likeRepository.deleteByContent_idAndUsername(dto.getContent_id(), dto.getUsername(), dto.getLike_type());
    }

    @Override
    public String likeBoolean(LikeDTO dto) {

        return null;
    }
}
