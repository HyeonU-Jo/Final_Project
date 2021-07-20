package com.work.finalproject.controller;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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

    @GetMapping("/realDetail")
    public void realDetail(String content_id, Model model) throws IOException{
        List<ReviewDTO> list = service.reviewList(content_id);
        model.addAttribute("reviewList", list);

        PublicAPI realDetail = new PublicAPI();

        List<XmlDTO> xmlList = realDetail.realDetail(content_id, model);

        model.addAttribute("list", xmlList);
        model.addAttribute("contentId", xmlList.get(0).getContent_id());



    }

    @GetMapping("/detail")
    public void search(String keyword, Model model) throws IOException{

        PublicAPI publicAPI = new PublicAPI();

        List<XmlDTO> xmlList = publicAPI.search(keyword, model);

        model.addAttribute("list", xmlList);


    }



    @GetMapping("/")
    public String index(){return "redirect:/test/searchPage";}

    @PostMapping("/reviewWrite")
    public String reviewWrite(ReviewDTO dto, RedirectAttributes redirectAttributes){
        System.out.println("reviewwrite 컨텐츠 아이디 : " + dto.getContent_id());
        System.out.println("dto 값 확인한다 -------" + dto.getR_content());
        System.out.println("dto값 확인한다. ㅁㄴㅇㅁㄴㅇ" + dto.getR_num());

        service.reviewWrite(dto);
        redirectAttributes.addAttribute("content_id", dto.getContent_id());



        return "redirect:/test/realDetail";
    }



}











