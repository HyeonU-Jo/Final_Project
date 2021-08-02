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

    @Transactional
    public void update(member_tbl member_tbl){
        member_tbl persistance = member_repository.findById(member_tbl.getNum()).orElseThrow(()->{
            return new IllegalArgumentException("회원 찾기 실패");
        });
        String rawPassword = member_tbl.getPassword();
        String encPassword = encoder.encode(rawPassword);
        persistance.setPassword(encPassword);
        persistance.setEmail(member_tbl.getEmail());
        persistance.setName(member_tbl.getName());
        // 회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit이 자동으로 된다.
        // 영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌

    }

}