package com.work.finalproject.config;

import com.work.finalproject.config.auth.PrincipalOauth2UserService;
import com.work.finalproject.handler.LoginFailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.work.finalproject.config.auth.PrincipalDetailService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration  // 빈등록 (IoC관리)
@EnableWebSecurity  // 시큐리티 필터 등록
@EnableGlobalMethodSecurity(prePostEnabled = true)  //특정주소로 접근시 권한 및 인증을 미리 체크
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private PrincipalOauth2UserService principalOauth2Userservice;

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean   //IoC가 됨
    public BCryptPasswordEncoder encodePWD(){
        return new BCryptPasswordEncoder();
    }



    // 시큐리티가 대신 로그인 해준 password가 어떤 값으로 해쉬가 되어 회원가입 되었는지 알아야
    // 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음
    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/diary/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginPlz")
                .loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인
                .defaultSuccessUrl("/") //로 이동
                .failureHandler(loginFailHandler())
                .and()
                .oauth2Login()
                .loginPage("/member/auth/login")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logoutPage");

    }


        @Bean
         public AuthenticationFailureHandler loginFailHandler(){
            return new LoginFailHandler();
    }

}