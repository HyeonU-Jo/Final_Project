package com.work.finalproject.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/notice")
@Log4j2
public class NoticeController {

    /*공지사항*/
    @GetMapping({"/notice"})
    public String notice() {
        log.info("notice...");
        return "menu/notice";
    }
}
