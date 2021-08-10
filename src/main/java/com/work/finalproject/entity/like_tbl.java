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

    @Column(length = 80, nullable = true)
    private String content_id;

    @Column(length = 90, nullable = true)
    private String username;

    @Column(length = 10, nullable = true)
    private String like_type;

}