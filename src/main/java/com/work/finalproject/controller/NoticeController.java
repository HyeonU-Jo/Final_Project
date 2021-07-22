package com.work.finalproject.controller;

import com.work.finalproject.dto.NoticeDTO;
import com.work.finalproject.dto.PageRequestDTO;
import com.work.finalproject.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/notice")
@Log4j2
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService service;

    @GetMapping("/")
    public String index(){
        return "redirect:/notice/list";
    }

    /*공지사항*/
    @GetMapping({"/list"})
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", service.getList(pageRequestDTO));

        System.out.println(service.getList(pageRequestDTO));
    }

    /*글쓰기*/
    @GetMapping({"/register"})
    public void register(){
        log.info("register....");
    }

    /*등록처리*/
    @PostMapping("/register")
    public String registerPost(NoticeDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto~~~" + dto);
        //새로 추가된 엔티티의 번호
        int no = service.register(dto);
        redirectAttributes.addFlashAttribute("msg", no);
        return "redirect:/notice/list";
    }

    /*글수정*/
    @PostMapping("/modify")
    public String modify(NoticeDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes) {

        log.info("post modify...");
        log.info("dto: " + dto);

        service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("no", dto.getNo());

        return "redirect:/notice/read";
    }

    /*상세보기*/
    @GetMapping({"/read", "/modify"})
    public void read(int no, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("no:~~~" + no);
        NoticeDTO dto = service.read(no);
        model.addAttribute("dto", dto);
    }

    /*삭제처리*/
    @PostMapping("/remove")
    public String remove(int no, RedirectAttributes redirectAttributes) {
        log.info("no~~~" + no);
        service.remove(no);
        redirectAttributes.addFlashAttribute("msg", no);
        return "redirect:/notice/list";
    }

}
