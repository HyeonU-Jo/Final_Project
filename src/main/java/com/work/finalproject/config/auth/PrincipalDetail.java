package com.work.finalproject.config.auth;

import com.work.finalproject.entity.member_tbl;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면
// UserDetails 타입의 오브젝트를 스프링 시큐리티의 고유한 세션저장소에 저장해줌
@Data
public class PrincipalDetail implements UserDetails, OAuth2User {
    private member_tbl member_tbl;  //콤포지션
    private Map<String,Object> attributes;

    //일반 로그인
    public PrincipalDetail(member_tbl member_tbl){
        this.member_tbl = member_tbl;
    }

    //OAuth 로그인
    public PrincipalDetail(member_tbl member_tbl, Map<String,Object> attributes){
        this.member_tbl = member_tbl;
        this.attributes = attributes;
    }

    @Override
    public String getPassword() {
        return member_tbl.getPassword();
    }

    @Override
    public String getUsername() {
        return member_tbl.getUsername();
    }

    //계정이 만료되지 않았는지 리턴.(true=만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정이 잠겨있지 않았는지 리턴.(true=잠기지않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호가 만료되지 않았는지 리턴.(true=만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정이 활성화 상태인지 리턴한다.(true:활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    //계정이 갖고있는 권한 목록을 리턴한다.(1개)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(()->{return "ROLE_"+member_tbl.getRole();});

        return collectors;
    }


    @Override
    public Map<String,Object> getAttributes(){
        return attributes;
    }

    @Override
    public String getName(){
        return null;
    }
}
