package com.work.finalproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class travel_tbl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int t_no;

    @Column(length = 300, nullable = true)
    private double t_lat;

    @Column(length = 300, nullable = true)
    private double t_long;

    @Column(length = 1000, nullable = true)
    private String t_review;
}
