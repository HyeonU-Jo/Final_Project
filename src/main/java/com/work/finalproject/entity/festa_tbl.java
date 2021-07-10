package com.work.finalproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class festa_tbl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int f_no;

    @Column(length = 300, nullable = true)
    private double f_lat;

    @Column(length = 300, nullable = true)
    private double f_long;

    @Column(length = 1000, nullable = true)
    private String f_review;
}
