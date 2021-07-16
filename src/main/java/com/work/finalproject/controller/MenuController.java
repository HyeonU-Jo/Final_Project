package com.work.finalproject.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu")
@Log4j2
public class MenuController {

    @GetMapping({"/plan"})
    public String plan() {

        log.info("plan...");

        return "menu/plan";
    }
    @GetMapping({"/notice"})
    public String notice() {

        log.info("notice...");

        return "menu/notice";
    }
}
