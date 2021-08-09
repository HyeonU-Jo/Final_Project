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
    private String p_sday;

    @Column(length = 10, nullable = false)
    private String p_eday;

    @Column(length=10, nullable = false)
    private String p_cday;

    @Column(length = 10, nullable = false)
    private String content_id;

    @Builder
    public plan_tbl(int no, String p_sday, String p_eday, String p_cday, String username, String content_id){
        this.no=no;
        this.p_sday=p_sday;
        this.p_eday=p_eday;
        this.p_cday=p_cday;
        this.username=username;
        this.content_id=content_id;

    }

}
