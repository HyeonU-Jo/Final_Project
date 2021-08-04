package com.work.finalproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class plan_tbl {

    @Id
    @Column(length=20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @Column(length = 20)
    private String username;

    @Column(length = 10, nullable = false)
    private String sDay;

    @Column(length = 10, nullable = false)
    private String eDay;

    @Column(length=10, nullable = false)
    private int cDay;

    @Column(length = 10, nullable = false)
    private String content_id;
}
