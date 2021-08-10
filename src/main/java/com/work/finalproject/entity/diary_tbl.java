package com.work.finalproject.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class diary_tbl extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dno;

    @Column(length = 500, nullable = false)
    private String d_title;

    @Column(length = 100, nullable = false)
    private String d_username;

    @Column(length = 4000, nullable = false)
    private String d_content;

    public void changeTitle(String title){
        this.d_title=title;
    }
    public void changeContent(String content){
        this.d_content=content;
    }


}
