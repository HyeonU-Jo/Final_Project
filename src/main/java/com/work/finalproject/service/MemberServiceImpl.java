package com.work.finalproject.service;

import com.work.finalproject.dto.MemberDTO;
import com.work.finalproject.entity.member_tbl;
import com.work.finalproject.repository.member_repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private member_repository repository;

    @Override
    public String join(MemberDTO dto) {
        member_tbl entity =dtoToEntity(dto);

        repository.save(entity);

        return entity.getId();
    }

    //로그인

    //회원가입

    //마이페이지

}
