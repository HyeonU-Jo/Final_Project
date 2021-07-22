package com.work.finalproject.controller.api;

import com.work.finalproject.dto.ResponseDTO;
import com.work.finalproject.entity.RoleType;
import com.work.finalproject.entity.member_tbl;
import com.work.finalproject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/api/user")
    public ResponseDTO<Integer> save(@RequestBody member_tbl member_tbl){
        System.out.println("UserApiController : save 호출됨");
        member_tbl.setRole(RoleType.USER);
        int result = memberService.join(member_tbl);
        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);

    }

}
