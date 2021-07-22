package com.work.finalproject.service;


import com.work.finalproject.entity.member_tbl;
import com.work.finalproject.repository.member_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MemberService{

    @Autowired
    private member_repository member_repository;

    @Transactional
    public int join(member_tbl member_tbl){
        try{
            member_repository.save(member_tbl);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("MemberService:join():"+e.getMessage());
        }
        return -1;
    }

}