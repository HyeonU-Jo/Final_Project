package com.work.finalproject.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/plan")
@Log4j2
public class PlanController {

    /*plan화면*/
    @GetMapping({"/plan"})
    public String plan() {
        log.info("plan...");
        return "plan/plan";
    }

    /*찜목록가기*/
    @GetMapping({"/plan_popup"})
    public String popup() {
        log.info("notice...");
        return "plan/plan_popup";
    }


}
