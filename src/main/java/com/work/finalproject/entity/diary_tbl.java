package com.work.finalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class diary_tbl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @Column(length = 500, nullable = false)
    private String d_title;

    @Column(length = 100, nullable = false)
    private String d_username;

    @Column(length = 4000, nullable = false)
    private String d_content;


}
