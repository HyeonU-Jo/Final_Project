package com.work.finalproject.controller.api;

import com.work.finalproject.dto.ResponseDTO;
import com.work.finalproject.entity.ConfirmationToken;
import com.work.finalproject.entity.RoleType;
import com.work.finalproject.entity.member_tbl;
import com.work.finalproject.repository.ConfirmationTokenRepository;
import com.work.finalproject.repository.email_repository;
import com.work.finalproject.service.EmailSenderService;
import com.work.finalproject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private email_repository email_repository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/auth/joinProc")
    public ResponseDTO<Integer> save(@RequestBody member_tbl member_tbl){
        System.out.println("UserApiController : save 호출됨");
        memberService.join(member_tbl);
        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/member")
    public ResponseDTO<Integer> update(@RequestBody member_tbl member_tbl) {
        memberService.update(member_tbl);


        // DB의 값은 변경이 됐음.직접 세션값을 변경
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(member_tbl.getUsername(), member_tbl.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
    }


    @RequestMapping(value="/forgot-password", method=RequestMethod.GET)
    public ModelAndView displayResetPassword(ModelAndView modelAndView, member_tbl member_tbl) {
        modelAndView.addObject("member_tbl", member_tbl);
        modelAndView.setViewName("member/forgotPassword");
        return modelAndView;
    }

    //회원탈퇴
    @DeleteMapping("/auth/member/{id}")
    public ResponseDTO<Integer> deleteById(@PathVariable int id){
        memberService.delete(id);
        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
    }



    @RequestMapping(value="/forgot-password", method=RequestMethod.POST)
    public ModelAndView forgotUserPassword(ModelAndView modelAndView, member_tbl member_tbl) {
        member_tbl existingUser = email_repository.findByEmailIgnoreCase(member_tbl.getEmail());
        if(existingUser != null) {
            // create token
            ConfirmationToken confirmationToken = new ConfirmationToken(existingUser);

            // save it
            confirmationTokenRepository.save(confirmationToken);

            // create the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(existingUser.getEmail());
            mailMessage.setSubject("패스워드 리셋 성공");
            mailMessage.setFrom("forsomeday97@gmail.com");
            mailMessage.setText("비밀번호 재설정 절차를 완료하려면 여기를 클릭하세요 : "
                    +"http://localhost:9090/confirm-reset?token="+confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);

            modelAndView.addObject("message", "비밀번호 재설정 요청을 받았습니다. 받은 편지함에서 재설정 링크를 확인하세요");
            modelAndView.setViewName("member/successForgotPassword");

        } else {
            modelAndView.addObject("message", "등록되지 않은 이메일입니다.");
            modelAndView.setViewName("member/error");
        }

        return modelAndView;
    }

    @RequestMapping(value="/confirm-reset", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView validateResetToken(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null) {
            member_tbl member_tbl = email_repository.findByEmailIgnoreCase(token.getMember().getEmail());
            email_repository.save(member_tbl);
            modelAndView.addObject("member_tbl", member_tbl);
            modelAndView.addObject("email", member_tbl.getEmail());
            modelAndView.setViewName("member/resetPassword");
        } else {
            modelAndView.addObject("message", "링크가 잘못되거나 연결이 끊어졌습니다. 다시 시도해주세요.");
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }


    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public ModelAndView resetUserPassword(ModelAndView modelAndView, member_tbl member_tbl) {

        if(member_tbl.getEmail() != null) {
            // use email to find user
            member_tbl tokenUser = email_repository.findByEmailIgnoreCase(member_tbl.getEmail());
            tokenUser.setPassword(encoder.encode(member_tbl.getPassword()));
            email_repository.save(tokenUser);
            modelAndView.addObject("message", "비밀번호가 재설정되었습니다.");
            modelAndView.setViewName("member/successResetPassword");
        } else {
            modelAndView.addObject("message","링크가 잘못되거나 연결이 끊어졌습니다. 다시 시도해주세요.");
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }
}
