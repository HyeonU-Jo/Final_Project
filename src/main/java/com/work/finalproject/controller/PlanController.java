package com.work.finalproject.controller;

import com.work.finalproject.dto.LikeDTO;
import com.work.finalproject.dto.PlanDTO;
import com.work.finalproject.dto.XmlDTO;
import com.work.finalproject.publicApi.PublicAPI;
import com.work.finalproject.service.LikeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/plan")
@Log4j2
public class PlanController {

    private final LikeService service;

    public PlanController(LikeService service) {
        this.service = service;
    }


    /*plan화면*/
    @GetMapping({"/plan"})
    public String plan() {
        log.info("plan...");
        return "plan/plan";
    }

    /*찜목록가기*/
    @GetMapping({"/planLikeList"})
    public String planLikeList(Model model) {
        List<LikeDTO> likeDtoList= service.getLikeList();
        List<XmlDTO> xmlDTO = new ArrayList<>();
        PublicAPI api = new PublicAPI();
        for (int i = 0; i<likeDtoList.size(); i++){
            try{
                xmlDTO.add(api.detail(likeDtoList.get(i).getContent_id(), ""));
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
        model.addAttribute("likeList",xmlDTO);
        return "plan/planLikeList";
    }

    @GetMapping({"/test"})
    public String test(){
        return "test/searchPage";
    }
//
//    @GetMapping({"/save"})
//    public String save(PlanDTO dto){
//        log.info("dto~~~" + dto);
//        //새로 추가된 엔티티의 번호
//        int no = service.register(dto);
//        redirectAttributes.addFlashAttribute("msg", no);
//
//        return "plan/plan";
//    }


}
