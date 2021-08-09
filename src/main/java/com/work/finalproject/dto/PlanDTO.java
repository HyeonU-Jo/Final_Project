package com.work.finalproject.dto;

import com.work.finalproject.entity.plan_tbl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class PlanDTO {
    private int no;
    private String username;
    private String p_sday;
    private String p_eday;
    private String p_cday;
    private String content_id;


 /*   public plan_tbl toEntity(){
        return plan_tbl.builder()
                .no(no)
                .username(username)
                .p_sday(p_sday)
                .p_eday(p_eday)
                .p_cday(p_cday)
                .content_id(content_id)
                .build();
    }*/
}
