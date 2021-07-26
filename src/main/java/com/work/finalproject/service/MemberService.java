package com.work.finalproject.service;


import com.work.finalproject.entity.RoleType;
import com.work.finalproject.entity.member_tbl;
import com.work.finalproject.repository.member_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MemberService{

    @Autowired
    private member_repository member_repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void join(member_tbl member_tbl){
       String rawPassword = member_tbl.getPassword();   //비밀번호 원문
       String encPassword = encoder.encode(rawPassword);    // 해쉬
       member_tbl.setPassword(encPassword);
       member_tbl.setRole(RoleType.USER);
       member_repository.save(member_tbl);
    }


}