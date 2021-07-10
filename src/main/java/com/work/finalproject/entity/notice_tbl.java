package com.work.finalproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class notice_tbl extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int n_no;

    @Column(length = 500, nullable = false)
    private String n_title;

    @Column(length = 100, nullable = false)
    private String n_writer;

    @Column(length = 4000, nullable = false)
    private String n_content;
}
