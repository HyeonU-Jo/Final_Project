package com.work.finalproject.controller;

import com.work.finalproject.dto.LikeDTO;
import com.work.finalproject.service.LikeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/plan")
@Log4j2
public class PlanController {

    private final LikeService service;

    public PlanController(LikeService service) {
        this.service = service;
    }


    /*plan화면*/
    @GetMapping({"/plan"})
    public String plan() {
        log.info("plan...");
        return "plan/plan";
    }

    /*찜목록가기*/
    @GetMapping({"/planLikeList"})
    public String planLikeList(Model model) {
        List<LikeDTO> likeDtoList= service.getLikeList();
        model.addAttribute("likeList",likeDtoList);
        return "plan/planLikeList";
    }


}
