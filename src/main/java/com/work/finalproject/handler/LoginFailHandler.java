package com.work.finalproject.handler;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFailHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        boolean hideUserNotFoundExceptions = false;

        if (exception instanceof UsernameNotFoundException) {
            request.setAttribute("errorMsg", "존재하지 않는 아이디입니다.");
        } else if(exception instanceof BadCredentialsException) {
            request.setAttribute("errorMsg", "아이디 또는 비밀번호가 틀립니다.");

        } else if(exception instanceof LockedException) {
            request.setAttribute("errorMsg", "잠긴 계정입니다..");

        } else if(exception instanceof DisabledException) {
            request.setAttribute("errorMsg", "비활성화된 계정입니다..");

        } else if(exception instanceof AccountExpiredException) {
            request.setAttribute("errorMsg", "만료된 계정입니다..");

        } else if(exception instanceof CredentialsExpiredException) {
            request.setAttribute("errorMsg", "비밀번호가 만료되었습니다.");
        } else{
            request.setAttribute("errorMsg", "알 수 없는 오류가 발생하였습니다.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/login/fail");
        dispatcher.forward(request, response);
        }
    }
