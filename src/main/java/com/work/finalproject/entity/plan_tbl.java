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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @Column(length = 20)
    private String username;

    @Column(length = 10, nullable = false)
    private String p_sday;

    @Column(length = 10, nullable = false)
    private String p_eday;

    @Column(length=10, nullable = false)
    private String p_cday;

    @Column(length = 10, nullable = false)
    private String content_id;

}
