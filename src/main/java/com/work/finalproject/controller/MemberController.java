package com.work.finalproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    //로그인
    @GetMapping("/login")
    public  void login(){
        log.info("login()~~~~");
    }

    //회원가입
    @GetMapping("/insert")
    public void register(){
        log.info("insert()~~~~");
    }

    //마이페이지





}
