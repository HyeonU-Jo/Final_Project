package com.work.finalproject.controller;

import com.work.finalproject.dto.MemberDTO;
import com.work.finalproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private MemberService memberService;

    @GetMapping("/")
    public String main(Model model){

        return "main";
    }


    //회원가입 페이지
    @GetMapping("/join")
    public String join(Model model){

        return "join";
    }
    //회원가입 페이지 처리
    @PostMapping("/join")
    public String joinPost(MemberDTO dto, RedirectAttributes redirectAttributes){
        String id = memberService.join(dto);
        redirectAttributes.addFlashAttribute("msg",id);
        return "redirect:/member/login";
    }


    //로그인 페이지
    @GetMapping("/login")
    public String memberLogin(Model model){

        return "/member/login";
    }
    //로그인 페이지 처리
    @PostMapping("/login")
    public String memberLoginProc(){

        return "redirect:/main";
    }


    //마이페이지 페이지
    @GetMapping("/mypage")
    public String memberMypage(Model model){

        return "/member/mypage";
    }
    //마이페이지 처리
    public String memberMypageProc(){

        return "redirect:/main";
    }


    //유저페이지 (테스트용)
    @GetMapping("/user")
    public String user(Model model){

        return "/member/user";
    }


}
