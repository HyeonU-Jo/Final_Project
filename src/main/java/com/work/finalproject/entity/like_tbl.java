package com.work.finalproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class like_tbl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @Column(length = 10, nullable = true)
    private int t_like;

    @Column(length = 10, nullable = true)
    private int s_like;

    @Column(length = 10, nullable = true)
    private int f_like;
}
