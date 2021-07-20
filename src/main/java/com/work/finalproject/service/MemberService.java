package com.work.finalproject.service;

import com.work.finalproject.dto.MemberDTO;
import com.work.finalproject.entity.member_tbl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MemberService extends UserDetailsService {

    //회원가입
    String join(MemberDTO memberDTO);
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

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
