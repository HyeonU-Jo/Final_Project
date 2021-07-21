package com.work.finalproject.service;

import com.work.finalproject.dto.MemberDTO;
import com.work.finalproject.entity.member_tbl;
import com.work.finalproject.repository.member_repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService, UserDetailsService {

    private member_repository repository;
    private MemberService service;

    //회원가입
    @Transactional
    public String join(MemberDTO memberDTO){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));

        member_tbl entity = dtoToEntity(memberDTO);
        repository.save(entity);

        //password를 암호화 한 뒤에 db에 저장
        return entity.getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        //로그인을 하기 위해 가입된 user정보를 조회하는 메서드
        Optional<member_tbl> memberWrapper = member_repository.findByusername(username);
        member_tbl member = memberWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if("admin".equals(username)){
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        // 아이디, 비밀번호, 권한리스트를 매개변수로 User를 만들어 반환해준다.
        return new User(member.getId(), member.getPassword(), authorities);

    }

   //마이페이지

}
