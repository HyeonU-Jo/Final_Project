package com.work.finalproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class notice_tbl extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @Column(length = 500, nullable = false)
    private String n_title;

    @Column(length = 100, nullable = false)
    private String n_writer;

    @Column(length = 4000, nullable = false)
    private String n_content;

    public void changeTitle(String title){
        this.n_title=title;
    }
    public void changeContent(String content){
        this.n_content=content;
    }
}
