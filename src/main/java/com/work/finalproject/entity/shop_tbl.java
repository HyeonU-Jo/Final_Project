package com.work.finalproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class shop_tbl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int s_no;

    @Column(length = 300, nullable = true)
    private double s_lat;

    @Column(length = 300, nullable = true)
    private double s_long;

    @Column(length = 1000, nullable = true)
    private String s_review;
}
