package com.work.finalproject.config.auth;

import com.work.finalproject.entity.member_tbl;
import com.work.finalproject.repository.member_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private member_repository member_repository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oauth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getClientId();
        String providerId = oauth2User.getAttribute("sub");
        String username = provider+"_"+providerId;
        String password = bCryptPasswordEncoder.encode("놀먹");
        String email = oauth2User.getAttribute("email");


        member_tbl memberEntity = member_repository.findByEmail(email);

        if(memberEntity == null) {
            memberEntity = member_tbl.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .oauth("google")
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            member_repository.save(memberEntity);
        }
        return new PrincipalDetail(memberEntity, oauth2User.getAttributes());

    }
}
