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
    @GetMapping({"/list"})
    public String list() {
        log.info("list...");
        return "/notice/list";
    }

    /*글쓰기*/
    @GetMapping({"/register"})
    public String register(){
        log.info("register....");
        return "/notice/register";
    }
    /*글수정*/
    @GetMapping({"/modify"})
    public String modify(){
        log.info("modify....");
        return "/notice/modify";
    }
    /*상세보기*/
    @GetMapping({"/read"})
    public String read(){
        log.info("read....");
        return "/notice/read";
    }

}
