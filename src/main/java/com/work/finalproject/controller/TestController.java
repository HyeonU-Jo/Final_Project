package com.work.finalproject.controller;


import java.io.IOException;
import java.util.List;

import com.work.finalproject.dto.ReviewDTO;
import com.work.finalproject.dto.XmlDTO;
import com.work.finalproject.publicApi.PublicAPI;
import com.work.finalproject.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final ReviewService service;



    @GetMapping("/kakaoMapApi")
    public String kakaoMapApi(){
        return "/test/kakaoMapApi";
    }

    @GetMapping("/searchPage")
    public String searchPage(){

        return "/test/searchPage";

    }

    @GetMapping("/detail")
    public String search(String keyword, Model model, String contentType) throws IOException{

        PublicAPI publicAPI = new PublicAPI();

        List<XmlDTO> xmlList = publicAPI.search(keyword, contentType);

        model.addAttribute("list", xmlList);


        return "/test/detail";
    }

    @GetMapping("/realDetail")
    public String realDetail(String content_id, Model model, String contentType) throws IOException{
        List<ReviewDTO> list = service.reviewList(content_id);
        model.addAttribute("reviewList", list);

        PublicAPI realDetail = new PublicAPI();

        XmlDTO xmlDTO = realDetail.detail(content_id, contentType);

        model.addAttribute("dto", xmlDTO);

        return "/test/realDetail";
    }





    @GetMapping("/")
    public String index(){return "redirect:/test/searchPage";}

    @PostMapping("/reviewWrite")
    public String reviewWrite(ReviewDTO dto, RedirectAttributes redirectAttributes, String contentType){
        System.out.println("reviewwrite 컨텐츠 아이디 : " + dto.getContent_id());
        System.out.println("dto 값 확인한다 -------" + dto.getR_content());
        System.out.println("dto값 확인한다. ㅁㄴㅇㅁㄴㅇ" + dto.getR_num());

        service.reviewWrite(dto);
        redirectAttributes.addAttribute("content_id", dto.getContent_id());
        redirectAttributes.addAttribute("contentType", contentType);




        return "redirect:/test/realDetail";
    }



}











