package com.work.finalproject.controller;

import com.work.finalproject.dto.DiaryDTO;
import com.work.finalproject.dto.NoticeDTO;
import com.work.finalproject.dto.PageRequestDTO;
import com.work.finalproject.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/diary")
@Log4j2
public class DiaryController {

    @Autowired
    private final DiaryService service;

    public DiaryController(DiaryService service) {
        this.service = service;
    }

    private String getFolder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-", File.separator);
    }

    private boolean checkImageType(File file) {
        try {
            String contentType = Files.probeContentType(file.toPath());
            return contentType.startsWith("image");
        } catch (IOException e) {
            System.err.println("checkImageType err = " + e.getMessage());
        }
        return false;
    }


    @GetMapping("/")
    public String index() {
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
    public void registerGet() {
        log.info("registerGet....");
    }

    /*등록처리*/

    @PostMapping({"/register"})
    @ResponseBody
    public String registerPost(DiaryDTO dto, RedirectAttributes redirectAttributes, MultipartFile[] image) {
        log.info("dto~~~" + dto);

        //새로 추가된 엔티티의 번호
        log.info("uploadFile =========================================== ");
        String uploadFolder = "C:\\image";

        System.out.println("uploadFile크기!!!" + image.length);
        for (MultipartFile multipartFile : image) {
            log.info("multipartFile = " + multipartFile.getOriginalFilename());
            log.info("multipartFile size= " + multipartFile.getSize());
            System.out.println(multipartFile.getOriginalFilename());
            String uploadFileName = multipartFile.getOriginalFilename();
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
            log.info("uploadFileName = " + uploadFileName);
            File saveFile = new File(uploadFolder, uploadFileName);
            try {
                multipartFile.transferTo(saveFile);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            dto.setUploadfile(uploadFileName);
        }

        service.dtoToEntity(dto);

        int dno = service.register(dto);
        redirectAttributes.addFlashAttribute("msg", dno);
        return "redirect:/diary/list";
    }


   /* @GetMapping("download")
    public ResponseEntity<Resource> download(String image) throws IOException {
        Path path = Paths.get("C:\\image" + image);
        //이 부분을 파일 이름을 받아와서 그 이름으로 DB에서 찾아올수 있도록 해야함


        String contentType = Files.probeContentType(path);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }*/

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


   /* @PostMapping("/uploadAjaxAction")
    @ResponseBody
    public void uploadAjaxPost(MultipartFile[] uploadFile){
        log.info("uploadFile =========================================== ");
        String uploadFolder = "C:\\upload";

        for (MultipartFile multipartFile : uploadFile) {
            log.info("multipartFile = " + multipartFile.getOriginalFilename());
            log.info("multipartFile size= " + multipartFile.getSize());
            String uploadFileName = multipartFile.getOriginalFilename();
            uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
            log.info("uploadFileName = " + uploadFileName);
            File saveFile = new File(uploadFolder, uploadFileName);
            try{
                multipartFile.transferTo(saveFile);
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
    }*/

}
