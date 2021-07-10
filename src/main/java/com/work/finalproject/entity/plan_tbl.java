package com.work.finalproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class plan_tbl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 20)
    private String id;

    @Column(length = 10, nullable = true)
    private int p_date;

    @Column(length = 10, nullable = true)
    private int all_like;
}
