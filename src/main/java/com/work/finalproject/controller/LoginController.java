package com.work.finalproject.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@AllArgsConstructor
public class LoginController {
    @PostMapping("/login/fail")
    public String initPost() {
        return "/member/loginFail";
    }
}
