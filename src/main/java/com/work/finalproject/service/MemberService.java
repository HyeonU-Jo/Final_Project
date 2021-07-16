package com.work.finalproject.service;

import com.work.finalproject.dto.MemberDTO;
import com.work.finalproject.entity.member_tbl;

public interface MemberService {

    //로그인

    //회원가입

    //마이페이지


    // DTO -> Entity
    default member_tbl dtoToEntity(MemberDTO dto) {
        member_tbl entity = member_tbl.builder()
                .id(dto.getId())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .name(dto.getName())
                .build();
        return entity;
    }

    // Entity -> DTO
    default MemberDTO entityToDto(member_tbl entity){
        MemberDTO dto = MemberDTO.builder()
                .id(entity.getId())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .name(entity.getName())
                .build();
        return dto;
    }
}
