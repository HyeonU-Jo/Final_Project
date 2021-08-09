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
    private String sDay;
    private String eDay;
    private int cDay;
    private String content_id;
}
