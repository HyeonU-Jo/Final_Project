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


// 이근준 테스트용 컨트롤러
@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final ReviewService service;



    @GetMapping("/kakaoMapApi")
    public void kakaoMapApi(){

    }

    @GetMapping("/searchPage")
    public void searchPage(){

    }

    @GetMapping("/detail")
    public void search(String keyword, Model model, String contentType) throws IOException{
        PublicAPI publicAPI = new PublicAPI();

        List<XmlDTO> xmlList = publicAPI.search(keyword, model, contentType);

        for (int i = 0; i<xmlList.size(); i++){
            xmlList.get(i).setContentType(contentType);
        }

        model.addAttribute("list", xmlList);

    }

    @GetMapping("/realDetail")
    public void realDetail(String content_id, Model model, String contentType) throws IOException{
        List<ReviewDTO> list = service.reviewList(content_id);
        model.addAttribute("reviewList", list);

        PublicAPI realDetail = new PublicAPI();

        List<XmlDTO> xmlList = realDetail.detail(content_id, contentType);
        System.out.println("이것은 확인용!!!!!!!!!!!!!!!!!"+xmlList.get(0).getMapx());
/*        List<XmlDTO> xmlDTOList = realDetail.imageDetail(content_id);
        xmlList.get(0).setFirstimage(xmlDTOList.get(0).getOriginimgurl());*/
        model.addAttribute("list", xmlList);
        model.addAttribute("contentId", content_id);



    }





    @GetMapping("/")
    public String index(){return "redirect:/test/searchPage";}

    @PostMapping("/reviewWrite")
    public String reviewWrite(ReviewDTO dto, RedirectAttributes redirectAttributes){

        service.reviewWrite(dto);
        redirectAttributes.addAttribute("content_id", dto.getContent_id());



        return "redirect:/test/realDetail";
    }



}











