package com.work.finalproject.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
@Log4j2
public class Controller {

    @GetMapping({"/"})
    public String index() {

        return "index";
    }
}
