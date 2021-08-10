package com.work.finalproject.controller;

import com.work.finalproject.dto.DiaryDTO;
import com.work.finalproject.dto.NoticeDTO;
import com.work.finalproject.dto.PageRequestDTO;
import com.work.finalproject.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/diary")
@Log4j2
public class DiaryController {

    private final DiaryService service;

    public DiaryController(DiaryService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(){
        return "redirect:/diary/list";
    }

    /*리스트*/
    @GetMapping({"/list"})
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list----------------------");
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
    public String registerPost(DiaryDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto~~~" + dto);
        //새로 추가된 엔티티의 번호
        int dno = service.register(dto);
        redirectAttributes.addFlashAttribute("msg", dno);
        return "redirect:/diary/list";
    }


    /*글수정*/
    @PostMapping("/modify")
    public String modify(DiaryDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes) {
        log.info("post modify...");
        log.info("dto: " + dto);
        service.modify(dto);
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("dno", dto.getDno());
        return "redirect:/diary/read";
    }


    /*상세보기*/
    @GetMapping({"/read", "/modify"})
    public void read(int dno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("dno:~~~" + dno);
        DiaryDTO dto = service.read(dno);
        model.addAttribute("dto", dto);
    }

    /*삭제처리*/
    @PostMapping("/remove")
    public String remove(int dno, RedirectAttributes redirectAttributes) {
        log.info("dno~~~" + dno);
        service.remove(dno);
        redirectAttributes.addFlashAttribute("msg", dno);
        return "redirect:/diary/list";
    }


}
