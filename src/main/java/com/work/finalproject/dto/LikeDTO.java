package com.work.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LikeDTO {
    private int no;
    private int t_like;
    private int s_like;
    private int f_like;
}
