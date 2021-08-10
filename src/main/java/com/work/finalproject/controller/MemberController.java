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
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

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


    //회원가입 페이지
    @GetMapping("/auth/join")
    public String join(Model model) {

        return "/member/join";
    }

    //로그인 페이지
    @GetMapping("/auth/login")
    public String login(Model model) {

        return "/member/login";
    }


    //마이페이지 페이지
    /*@GetMapping("/mypage")
    public String mypage(Model model){

        return "mypage_2";
    }*/
    @GetMapping("/mypage_myinfo")
    public String mypage_myinfo(Model model) {

        return "/member/mypage_myinfo";
    }

    @GetMapping("/myPage_bucketList")
    public void myPage_bucketList() {

    }

    @GetMapping("/myPage_traveledList")
    public void myPage_traveledList() {

    }

    @GetMapping("/myPage")
    public String myPage(LikeDTO likeDTO, Model model) {
        likeDTO.setUsername("2");
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

        return "/member/myPage";

    }




    //유저페이지 (테스트용)
    @GetMapping("/user")
    public String user(Model model) {

        return "/member/user";
    }

    @GetMapping("/updateForm")
    public String updateForm(@AuthenticationPrincipal PrincipalDetail principal) {
        return "member/updateForm";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) { //Data를 리턴해주는 컨트롤러 함수

        //POST방식으로 key=value 데이터를 요청(카카오쪽으로)
        RestTemplate rt = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "d860d38c992ca8bf5f07dcc3fd5122b9");
        params.add("redirect_uri", "http://localhost:9090/member/auth/kakao/callback");
        params.add("code", code);

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        //Http 요청하기 - Post방식으로 - 그리고 response변수의 응답 받음
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

        System.out.println("카카오 엑세스 토큰:" + oauthToken.getAccess_token());

        RestTemplate rt2 = new RestTemplate();

        //HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2);

        //Http 요청하기 - Post방식으로 - 그리고 response변수의 응답 받음
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

        //member 오브젝트 : username, password, email
        System.out.println("카카오 아이디(번호)" + kakaoProfile.getId());
        System.out.println("카카오 이메일" + kakaoProfile.getKakao_account().getEmail());

        System.out.println("놀먹 유저네임" + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
        System.out.println("놀먹 이메일" + kakaoProfile.getKakao_account().getEmail());
        System.out.println("블로그서버 패스워드:" + key);

        member_tbl kakaoMember = member_tbl.builder()
                .username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
                .password(key)
                .email(kakaoProfile.getKakao_account().getEmail())
                .oauth("kakao")
                .build();


        // 가입자 혹은 비가입자 체크 해서 처리
        member_tbl originMember = memberService.findMember(kakaoMember.getUsername());

        if (originMember.getUsername() == null) {
            System.out.println("기존 회원이 아닙니다!");
            memberService.join(kakaoMember);
        }

        System.out.println("자동 로그인을 진행합니다.");
        //로그인 처리
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoMember.getUsername(), key));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }

    @PostMapping("/deleteLike")
    @ResponseBody
    public String deleteLike(LikeDTO dto){
        dto.setUsername("2");
        likeService.deleteLike(dto);
        
        return dto.getContent_id();
    }


}
