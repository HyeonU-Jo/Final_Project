package com.work.finalproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/")
    public String main(Model model){
        log.info("home controller");
        return "main";
    }
    @GetMapping("/user")
    public String dispUser(Model model){
        log.info("home controller");
        return "/member/user";
    }




}
