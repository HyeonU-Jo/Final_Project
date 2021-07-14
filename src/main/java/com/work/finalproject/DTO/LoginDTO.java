package com.work.finalproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDTO {
    private String id;
    private String password;
    private String email;
    private String name;
}
