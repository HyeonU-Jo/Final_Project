package com.work.finalproject.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.security.Timestamp;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class member_tbl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num;    // 시퀀스, auto_increment

    @Column(length = 20, nullable = false)
    private String id;    // 아이디

    @Column(length = 50, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private RoleType role;







}
