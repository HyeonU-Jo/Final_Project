package com.work.finalproject.service;

import com.work.finalproject.dto.MemberDTO;
import com.work.finalproject.entity.member_tbl;
import com.work.finalproject.repository.member_repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private member_repository repository;
    private MemberService service;

    //회원가입
    @Transactional
    public String join(MemberDTO memberDTO){
        member_tbl entity = dtoToEntity(memberDTO);
        repository.save(entity);

        return entity.getId();
    }

}
