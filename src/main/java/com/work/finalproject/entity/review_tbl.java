package com.work.finalproject.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class review_tbl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int r_num;

    @Column(length = 500, nullable = true)
    private String contentId;


    @Column(length = 500, nullable = true)
    private String r_content;

    @Column(length = 500, nullable = true)
    private int r_rating;




}
