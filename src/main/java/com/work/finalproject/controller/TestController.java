package com.work.finalproject.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.work.finalproject.dto.PageRequestDTO;
import com.work.finalproject.dto.ReviewDTO;
import com.work.finalproject.dto.XmlDTO;
import com.work.finalproject.publicApi.PublicAPI;
import com.work.finalproject.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final ReviewService service;

    @GetMapping("/")
    public String index(){return "redirect:/test/searchPage";}

    @GetMapping("/kakaoMapApi")
    public String kakaoMapApi(){
        return "/test/kakaoMapApi";
    }

    @GetMapping("/searchPage")
    public String searchPage(){
        return "/test/searchPage";

    }

    @GetMapping("/detail")
    public String search(String keyword, Model model, String contentType, int page) throws IOException{

        List<Integer> pageList = new ArrayList<Integer>();


        String strPage = Integer.toString(page);

        PublicAPI publicAPI = new PublicAPI();
        List<XmlDTO> xmlList = publicAPI.search(keyword, contentType, strPage);
        model.addAttribute("list", xmlList);

        model.addAttribute("totalCount", xmlList.get(0).getTotalPage());

        for(int i = 0; i<xmlList.get(0).getTotalPage(); i++){
            pageList.add(i+1);
        }
        model.addAttribute("page", page);
        model.addAttribute("pageList", pageList);
        model.addAttribute("keyword", keyword);
        System.out.println();
        model.addAttribute("contentType", contentType);
        model.addAttribute("page2", page);
        model.addAttribute("thisPage", Integer.parseInt(strPage));
        model.addAttribute("startPage", xmlList.get(0).getStartPage());
        model.addAttribute("endPage", xmlList.get(0).getEndPage());
        model.addAttribute("pageCount", 10);




        return "/test/detail";
    }

    @GetMapping("/realDetail")
    public String realDetail(String content_id, Model model, String contentType, String firstimage2) throws IOException{
        List<ReviewDTO> list = service.reviewList(content_id);
        model.addAttribute("reviewList", list);

        PublicAPI realDetail = new PublicAPI();
        XmlDTO xmlDTO = realDetail.detail(content_id, contentType);
        xmlDTO.setFirstimage2(firstimage2);
        model.addAttribute("dto", xmlDTO);
        return "/test/realDetail";
    }



    @PostMapping("/reviewWrite")
    public String reviewWrite(ReviewDTO dto, RedirectAttributes redirectAttributes, String contentType){

        service.reviewWrite(dto);
        redirectAttributes.addAttribute("content_id", dto.getContent_id());
        redirectAttributes.addAttribute("contentType", contentType);

        return "redirect:/test/realDetail";
    }

    @GetMapping("/modifyReview")
    public String reviewModify(int r_num, Model model){

        ReviewDTO dto = service.read(r_num);
        model.addAttribute("dto", dto);

        return "/test/modifyReview";
    }

}











