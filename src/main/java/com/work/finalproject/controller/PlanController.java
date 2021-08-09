package com.work.finalproject.controller;

import com.work.finalproject.dto.LikeDTO;
import com.work.finalproject.dto.PlanDTO;
import com.work.finalproject.dto.XmlDTO;
import com.work.finalproject.entity.plan_tbl;
import com.work.finalproject.publicApi.PublicAPI;
import com.work.finalproject.repository.plan_repository;
import com.work.finalproject.service.LikeService;
import com.work.finalproject.service.PlanService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/plan")
@Log4j2
public class PlanController {

    private final LikeService service;
    private final PlanService pService;

    public PlanController(LikeService service, PlanService pService) {
        this.service = service;
        this.pService = pService;
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
        List<LikeDTO> likeDtoList = service.getLikeList();

        List<XmlDTO> xmlDTO = new ArrayList<>();
        PublicAPI api = new PublicAPI();
        for (int i = 0; i < likeDtoList.size(); i++) {
            try {
                xmlDTO.add(api.detail(likeDtoList.get(i).getContent_id(), ""));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        model.addAttribute("likeList", xmlDTO);
        return "plan/planLikeList";
    }
    /*
    @GetMapping({"/test"})
    public String test(){
        return "test/searchPage";
    }*/

    @GetMapping({"/planlikesave"})
    public int planlikesave(PlanDTO dto) {
        log.info(dto.toString());
        plan_tbl plantbl=dto.toEntity();
        plan_tbl saved = plan_repository.
        log.info(saved.toString());
    }


}
