package com.work.finalproject.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
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

    @NotBlank(message = "아이디를 입력해주세요.")
    @Column(length = 100)
    private String username;    // 아이디

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Column(length = 100, nullable = false)
    private String password;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식을 맞춰주세요")
    @Column(length = 50, nullable = false)
    private String email;

    @NotBlank(message = "이름을 입력해주세요.")
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







