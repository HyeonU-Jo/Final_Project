package com.work.finalproject.controller;

import com.work.finalproject.dto.NoticeDTO;
import com.work.finalproject.dto.PageRequestDTO;
import com.work.finalproject.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/diary")
@Log4j2
@RequiredArgsConstructor
public class DiaryController {
    @GetMapping("/")
    public String index(){
        return "redirect:/diary/list";
    }

}
