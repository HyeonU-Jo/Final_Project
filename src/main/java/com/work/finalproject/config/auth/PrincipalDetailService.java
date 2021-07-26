package com.work.finalproject.config.auth;

import com.work.finalproject.entity.member_tbl;
import com.work.finalproject.repository.member_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service       //Bean 등록
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private member_repository member_repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        member_tbl principal = member_repository.findByUsername(username)
                .orElseThrow(()->{
            return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다." + username);
        });
        return new PrincipalDetail(principal);  //시큐리티의 세션에 유저 정보가 저장됨
    }





}
