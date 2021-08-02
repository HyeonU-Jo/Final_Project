package com.work.finalproject.controller.api;

import com.work.finalproject.dto.ResponseDTO;
import com.work.finalproject.entity.RoleType;
import com.work.finalproject.entity.member_tbl;
import com.work.finalproject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostMapping("/auth/joinProc")
    public ResponseDTO<Integer> save(@RequestBody member_tbl member_tbl){
        System.out.println("UserApiController : save 호출됨");
        memberService.join(member_tbl);
        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/member")
    public ResponseDTO<Integer> update(@RequestBody member_tbl member_tbl) {
        memberService.update(member_tbl);
        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
    }
}
