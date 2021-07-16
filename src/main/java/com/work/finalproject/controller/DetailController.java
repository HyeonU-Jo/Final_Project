package com.work.finalproject.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu")
@Log4j2
public class DetailController {

    @GetMapping({"/festa"})
    public String festa() {

        log.info("festa...");

        return "detail/festa";
    }
    @GetMapping({"/shop"})
    public String shop() {

        log.info("shop...");

        return "detail/shop";
    }
    @GetMapping({"/travel"})
    public String travel() {

        log.info("travel...");

        return "detail/travel";
    }
}
