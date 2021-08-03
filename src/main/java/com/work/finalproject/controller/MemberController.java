package com.work.finalproject.controller;

import com.work.finalproject.config.auth.PrincipalDetail;
import com.work.finalproject.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {





    //회원가입 페이지
    @GetMapping("/auth/join")
    public String join(Model model){

        return "/member/join";
    }
    //회원가입 페이지 처리
    @PostMapping("/join")
    public String joinPost(MemberDTO memberDTO){

        return "redirect:/";
    }


    //로그인 페이지
    @GetMapping("/auth/login")
    public String login(Model model){

        return "/member/login";
    }
    //로그인 페이지 처리
    @PostMapping("/login")
    public String loginPost(){

        return "index";
    }




    //마이페이지 페이지
    @GetMapping("/mypage")
    public String mypage(Model model){

        return "/member/mypage";
    }
    @GetMapping("/mypage_myinfo")
    public String mypage_myinfo(Model model){

        return "/member/mypage_myinfo";
    }
    @GetMapping("/mypage_like")
    public String mypage_like(Model model){

        return "/member/mypage_like";
    }


    //유저페이지 (테스트용)
    @GetMapping("/user")
    public String user(Model model){

        return "/member/user";
    }

    @GetMapping("/updateForm")
    public String updateForm(@AuthenticationPrincipal PrincipalDetail principal){
        return "member/updateForm";
    }

}
