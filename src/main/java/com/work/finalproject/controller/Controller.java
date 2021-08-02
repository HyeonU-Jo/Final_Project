package com.work.finalproject.controller;

import com.work.finalproject.dto.XmlDTO;
import com.work.finalproject.publicApi.PublicAPI;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@org.springframework.stereotype.Controller
@Log4j2
public class Controller {

    @GetMapping({"/"})
    public String index(Model model) {

        PublicAPI api = new PublicAPI();
        Random random = new Random();
        List<XmlDTO> xmlDTOList = new ArrayList<>();
        List<XmlDTO> xmlDTOList1 = new ArrayList<>();
        List<XmlDTO> xmlDTOList2 = new ArrayList<>();
        try{

            List<XmlDTO> xmlDTOS =api.randomSearch("15");
            List<XmlDTO> xmlDTOS1 = api.randomSearch("12");
            List<XmlDTO> xmlDTOS2 = api.randomSearch("39");

            Collections.shuffle(xmlDTOS);
            Collections.shuffle(xmlDTOS1);
            Collections.shuffle(xmlDTOS2);

            for(int i = 0; i<3; i++){

                XmlDTO xmlDTO = new XmlDTO();
                xmlDTO.setTitle(xmlDTOS.get(i).getTitle());
                xmlDTO.setContent_id(xmlDTOS.get(i).getContent_id());
                xmlDTO.setFirstimage2(xmlDTOS.get(i).getFirstimage2());
                xmlDTO.setAreacode(xmlDTOS.get(i).getAreacode());
                xmlDTOList.add(xmlDTO);

                XmlDTO xmlDTO1 = new XmlDTO();
                xmlDTO1.setTitle(xmlDTOS1.get(i).getTitle());
                xmlDTO1.setContent_id(xmlDTOS1.get(i).getContent_id());
                xmlDTO1.setFirstimage2(xmlDTOS1.get(i).getFirstimage2());
                xmlDTO1.setAreacode(xmlDTOS1.get(i).getAreacode());
                xmlDTOList1.add(xmlDTO1);

                XmlDTO xmlDTO2 = new XmlDTO();
                xmlDTO2.setTitle(xmlDTOS2.get(i).getTitle());
                xmlDTO2.setContent_id(xmlDTOS2.get(i).getContent_id());
                xmlDTO2.setFirstimage2(xmlDTOS2.get(i).getFirstimage2());
                xmlDTO2.setAreacode(xmlDTOS2.get(i).getAreacode());
                xmlDTOList2.add(xmlDTO2);

            }

            model.addAttribute("fest", xmlDTOList);
            model.addAttribute("tour", xmlDTOList1);
            model.addAttribute("shop" ,xmlDTOList2);

        }catch (Exception e){
            System.out.println("api 오류 : " + e.getMessage());
        }


        return "index";
    }

}
