package com.work.finalproject.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class review_tbl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int r_num;

    @Column(length = 500, nullable = true)
    private String content_id;

    @Column(length = 500, nullable = true)
    private String r_content;

    @Column(length = 500, nullable = true)
    private int r_rating;

    @Column(length = 500, nullable = true)
    private String image;

    @Transient
    private int r_rating_avg;

    public void changeR_content(String r_content){this.r_content = r_content;}
    public void changeR_rating(int r_rating){this.r_rating = r_rating;}

}
