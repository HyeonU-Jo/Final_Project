package com.work.finalproject.controller;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.qos.logback.core.CoreConstants;
import com.querydsl.core.BooleanBuilder;
import com.work.finalproject.dto.LikeDTO;
import com.work.finalproject.dto.PageRequestDTO;
import com.work.finalproject.dto.ReviewDTO;
import com.work.finalproject.dto.XmlDTO;
import com.work.finalproject.publicApi.PublicAPI;
import com.work.finalproject.service.LikeService;
import com.work.finalproject.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


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
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setContent_id(content_id);
        likeDTO.setUsername("2");
        String like = likeService.likeCheck(likeDTO);
        PublicAPI realDetail = new PublicAPI();
        XmlDTO xmlDTO = realDetail.detail(content_id, contentType);
        xmlDTO.setFirstimage2(firstimage2);
        model.addAttribute("dto", xmlDTO);
        model.addAttribute("like", like);
        return "/detail/realDetail";
    }



    @Autowired
    private ServletContext servletContext;

    @GetMapping("/writeReview")
    public String writeReview(String content_id,String contentType,String title, Model model, String firstimage2){
        XmlDTO dto = new XmlDTO();
        dto.setContent_id(content_id);
        dto.setContentType(contentType);
        dto.setFirstimage(firstimage2);
        dto.setTitle(title);
        model.addAttribute("dto", dto);


        return "/detail/writeReview";
    }

    @PostMapping("/reviewWrite")
    public String reviewWrite(HttpServletRequest request, ReviewDTO dto, RedirectAttributes redirectAttributes, String contentType, MultipartFile imageFile, String firstimage2){
        System.out.println("dto 값 확인 : " + dto.getContent_id());


        Date date = new Date(System.currentTimeMillis());

        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd-HH-mm-ss-SS");
        String time = format.format(date);

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
        redirectAttributes.addAttribute("firstimage2", firstimage2);

        return "redirect:/detail/realDetail";
    }

    @GetMapping("download")
    public ResponseEntity<Resource> download(String image) throws IOException {
        Path path = Paths.get("C:\\upload\\test\\" + image);
        //이 부분을 파일 이름을 받아와서 그 이름으로 DB에서 찾아올수 있도록 해야함


        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
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
    public String modifyReview(String nowImage, ReviewDTO reviewDTO, XmlDTO dto, RedirectAttributes redirectAttributes, MultipartFile imageFile){
        File file = new File("c:\\upload\\test\\"+nowImage);

        MultipartFile mf2 = imageFile;

        String checkFile = mf2.getOriginalFilename();
        System.out.println(checkFile + "무야호");
        if(checkFile.equals("")||checkFile == null){
            reviewDTO.setImage(nowImage);
        } else {
            if(file.exists()){
                //파일 경로가 존재하는지
                if(file.delete()){
                    System.out.println("파일 삭제 완료");
                }else{
                    System.out.println("파일 삭제 실패");
                }
            }else{
                System.out.println("파일이 존재하지 않음!");
            }

            Date date = new Date(System.currentTimeMillis());

            SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd-HH-mm-ss-SS");
            String time = format.format(date);
            System.out.println("이미지 파일 확인!" + imageFile);
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
            reviewDTO.setImage(original);
        }


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











