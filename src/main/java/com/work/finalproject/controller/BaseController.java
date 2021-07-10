package com.work.finalproject.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/base")
@Log4j2
public class BaseController {

    @GetMapping({"/","/list"})
    public String list() {

        log.info("list...");

        return null; //추후 경로 설정
    }
}
