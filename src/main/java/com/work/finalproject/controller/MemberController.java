package com.work.finalproject.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.finalproject.config.auth.PrincipalDetail;
import com.work.finalproject.dto.LikeDTO;
import com.work.finalproject.dto.XmlDTO;
import com.work.finalproject.entity.KakaoProfile;
import com.work.finalproject.entity.OAuthToken;
import com.work.finalproject.entity.member_tbl;
import com.work.finalproject.publicApi.PublicAPI;
import com.work.finalproject.repository.member_repository;
import com.work.finalproject.service.LikeService;
import com.work.finalproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;


import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {
    private final LikeService likeService;
    @Value("${key}")
    private String key;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private member_repository member_repository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    //???????????? ?????????
    @GetMapping("/join")
    public String join(member_tbl member_tbl) {

        return "/member/join";
    }



    //????????? ?????????
    @GetMapping("/auth/login")
    public String login(Model model) {

        return "/member/login";
    }

    @PostMapping("/delete")
    public String delete(Model model){

        return "/member/deleteForm";
    }

    //??????????????? ?????????
    /*@GetMapping("/mypage")
    public String mypage(Model model){

        return "mypage_2";
    }*/
    @GetMapping("/mypage_myinfo")
    public String mypage_myinfo(Model model) {

        return "/member/mypage_myinfo";
    }



    @GetMapping("/myPage_traveledList")
    public String myPage_traveledList(LikeDTO likeDTO, Model model) {
        List<LikeDTO>likeDTOS = likeService.likeList(likeDTO);
        PublicAPI api = new PublicAPI();
        List<XmlDTO> xmlDTOS = new ArrayList<>();
        for (int i = 0; i<likeDTOS.size(); i++){
            try {
                XmlDTO xmlDTO = api.detail(likeDTOS.get(i).getContent_id(), "");
                xmlDTOS.add(xmlDTO);
            }catch (Exception e){
                e.getMessage();
            }
        }
        model.addAttribute("likeList", xmlDTOS);

        return "/member/myPage_traveledList";
    }

    @GetMapping("/myPage")
    public String myPage() {


        return "/member/myPage";

    }
    @GetMapping("/myPage_bucketList")
    public String myPage_bucketList(LikeDTO likeDTO, Model model){
        List<LikeDTO>likeDTOS = likeService.likeList(likeDTO);
        PublicAPI api = new PublicAPI();
        List<XmlDTO> xmlDTOS = new ArrayList<>();
        for (int i = 0; i<likeDTOS.size(); i++){
            try {
                XmlDTO xmlDTO = api.detail(likeDTOS.get(i).getContent_id(), "");
                xmlDTOS.add(xmlDTO);
            }catch (Exception e){
                e.getMessage();
            }
        }
        model.addAttribute("likeList", xmlDTOS);
        return "/member/myPage_bucketList";
    }




    //??????????????? (????????????)
    @GetMapping("/user")
    public String user(Model model) {

        return "/member/user";
    }
    //?????????
    @GetMapping("/updateForm")
    public String updateForm(@AuthenticationPrincipal PrincipalDetail principal) {
        return "member/updateForm";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) { //Data??? ??????????????? ???????????? ??????

        //POST???????????? key=value ???????????? ??????(??????????????????)
        RestTemplate rt = new RestTemplate();

        //HttpHeader ???????????? ??????
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody ???????????? ??????
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "d860d38c992ca8bf5f07dcc3fd5122b9");
        params.add("redirect_uri", "http://localhost:9090/member/auth/kakao/callback");
        params.add("code", code);

        //HttpHeader??? HttpBody??? ????????? ??????????????? ??????
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        //Http ???????????? - Post???????????? - ????????? response????????? ?????? ??????
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("????????? ????????? ??????:" + oauthToken.getAccess_token());

        RestTemplate rt2 = new RestTemplate();

        //HttpHeader ???????????? ??????
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        //HttpHeader??? HttpBody??? ????????? ??????????????? ??????
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2);

        //Http ???????????? - Post???????????? - ????????? response????????? ?????? ??????
        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //member ???????????? : username, password, email
        System.out.println("????????? ?????????(??????)" + kakaoProfile.getId());
        System.out.println("????????? ?????????" + kakaoProfile.getKakao_account().getEmail());

        System.out.println("?????? ????????????" + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
        System.out.println("?????? ?????????" + kakaoProfile.getKakao_account().getEmail());
        System.out.println("??????????????? ????????????:" + key);

        member_tbl kakaoMember = member_tbl.builder()
                .username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
                .password(key)
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao")
                .build();


        // ????????? ?????? ???????????? ?????? ?????? ??????
        member_tbl originMember = memberService.findMember(kakaoMember.getUsername());

        if (originMember.getUsername() == null) {
            System.out.println("?????? ????????? ????????????!");
            memberService.join(kakaoMember);
        }

        System.out.println("?????? ???????????? ???????????????.");
        //????????? ??????
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoMember.getUsername(), key));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }

    @PostMapping("/deleteLike")
    @ResponseBody
    public String deleteLike(LikeDTO dto){
        likeService.deleteLike(dto);
        return dto.getContent_id();
    }



}
