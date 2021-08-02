package com.work.finalproject.controller;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.work.finalproject.dto.LikeDTO;
import com.work.finalproject.dto.PageRequestDTO;
import com.work.finalproject.dto.ReviewDTO;
import com.work.finalproject.dto.XmlDTO;
import com.work.finalproject.publicApi.PublicAPI;
import com.work.finalproject.service.LikeService;
import com.work.finalproject.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("/detail")
@RequiredArgsConstructor
public class DetailController {

    private final ReviewService service;
    private final LikeService likeService;

    @GetMapping("/")
    public String index(){return "redirect:/detail/searchPage";}

    @GetMapping("/kakaoMapApi")
    public String kakaoMapApi(){
        return "/detail/kakaoMapApi";
    }

    @GetMapping("/searchPage")
    public String searchPage(){
        return "/detail/searchPage";

    }

    @GetMapping("/detail")
    public String search(String keyword, Model model, String contentType, int page, String areaCode) throws IOException{

        List<Integer> pageList = new ArrayList<Integer>();


        String strPage = Integer.toString(page);

        PublicAPI publicAPI = new PublicAPI();
        List<XmlDTO> xmlList = publicAPI.search(keyword, contentType, strPage, areaCode);
        model.addAttribute("list", xmlList);
        if(!xmlList.isEmpty()){
            System.out.println("검색 결과 확인용 ~~~~~"+xmlList.size());
            for (int i = 0; i<xmlList.size(); i++){

                xmlList.get(i).setReviewAvg(service.reviewAvg(xmlList.get(i).getContent_id()));
                System.out.println("리뷰 평균 값 확인용 " + xmlList.get(i).getReviewAvg());
            }

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
        }






        return "/detail/detail";
    }

    @GetMapping("/realDetail")
    public String realDetail(String content_id, Model model, String contentType, String firstimage2, String areaCode) throws IOException{
        List<ReviewDTO> list = service.reviewList(content_id);
        model.addAttribute("reviewList", list);

        PublicAPI realDetail = new PublicAPI();
        XmlDTO xmlDTO = realDetail.detail(content_id, contentType);
        xmlDTO.setFirstimage2(firstimage2);
        model.addAttribute("dto", xmlDTO);
        return "/detail/realDetail";
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

        return "redirect:/detail/realDetail";
    }

    @GetMapping("/modifyReview")
    public String reviewModify(int r_num, Model model, XmlDTO xmlDTO){

        ReviewDTO dto = service.read(r_num);
        model.addAttribute("dto", dto);
        model.addAttribute("xmlDTO", xmlDTO);
        model.addAttribute("r_num", r_num);
        return "/detail/modifyReview";
    }
    @PostMapping("/modifyReview")
    public String modifyReview(ReviewDTO reviewDTO, XmlDTO dto, RedirectAttributes redirectAttributes){

        service.reviewModify(reviewDTO);
        redirectAttributes.addAttribute("content_id", dto.getContent_id());
        redirectAttributes.addAttribute("contentType", dto.getContentType());
        redirectAttributes.addAttribute("firstimage2", dto.getFirstimage2());

        return "redirect:/detail/realDetail";
    }

    @PostMapping("/deleteReview")
    public String deleteReview(int r_num, RedirectAttributes redirectAttributes, XmlDTO dto){
        service.deleteReview(r_num);
        redirectAttributes.addAttribute("content_id", dto.getContent_id());
        redirectAttributes.addAttribute("contentType", dto.getContentType());
        redirectAttributes.addAttribute("firstimage2", dto.getFirstimage2());
        return "redirect:/detail/realDetail";
    }

    @GetMapping("/moreSearch")
    public String moreSearch(){

        return "/detail/moreSearch";
    }

    @PostMapping("/likeDetail")
    @ResponseBody
    public String likeDetail(LikeDTO likeDTO){
        String abc = likeService.like(likeDTO);

        return abc;
    }
}











