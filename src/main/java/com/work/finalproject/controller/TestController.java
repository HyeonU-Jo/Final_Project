package com.work.finalproject.controller;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;
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
    public String reviewWrite(ReviewDTO dto, RedirectAttributes redirectAttributes, String contentType, MultipartFile imageFile){



        Date date = new Date(System.currentTimeMillis());

        System.out.println(date);
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd-HH-mm-ss-SS");
        String time = format.format(date);
        System.out.println(time);
        MultipartFile mf = imageFile;
        String path = "c:\\upload\\test\\";
        String uploadPath = "";
        String original = time+"__"+ mf.getOriginalFilename();

        uploadPath = path + original;
        if(mf!=null){
            try {
                mf.transferTo(new File(uploadPath));
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        dto.setImage(original);
        service.reviewWrite(dto);
        redirectAttributes.addAttribute("content_id", dto.getContent_id());
        redirectAttributes.addAttribute("contentType", contentType);

        return "redirect:/test/realDetail";
    }

    @GetMapping("/modifyReview")
    public String reviewModify(int r_num, Model model, XmlDTO xmlDTO){

        ReviewDTO dto = service.read(r_num);
        model.addAttribute("dto", dto);
        model.addAttribute("xmlDTO", xmlDTO);
        model.addAttribute("r_num", r_num);
        return "/test/modifyReview";
    }
    @PostMapping("/modifyReview")
    public String modifyReview(ReviewDTO reviewDTO, XmlDTO dto, RedirectAttributes redirectAttributes){

        service.reviewModify(reviewDTO);
        redirectAttributes.addAttribute("content_id", dto.getContent_id());
        redirectAttributes.addAttribute("contentType", dto.getContentType());
        redirectAttributes.addAttribute("firstimage2", dto.getFirstimage2());

        return "redirect:/test/realDetail";
    }

    @PostMapping("/deleteReview")
    public String deleteReview(int r_num, RedirectAttributes redirectAttributes, XmlDTO dto){
        service.deleteReview(r_num);
        redirectAttributes.addAttribute("content_id", dto.getContent_id());
        redirectAttributes.addAttribute("contentType", dto.getContentType());
        redirectAttributes.addAttribute("firstimage2", dto.getFirstimage2());
        return "redirect:/test/realDetail";
    }

}











