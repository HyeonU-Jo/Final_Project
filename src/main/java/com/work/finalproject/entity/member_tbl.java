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
    private int id;    // 시퀀스, auto_increment

    @Column(length = 100, nullable = false, unique = true)
    private String username;    // 아이디

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 50)
    private String name;

    @Column
    private String provider;
    @Column
    private String providerId;

    @Enumerated(EnumType.STRING)

    private RoleType role;

    @Column
    private String oauth;  // kakao, google

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    private boolean isEnabled;


    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }


}







