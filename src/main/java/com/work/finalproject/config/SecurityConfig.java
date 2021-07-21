package com.work.finalproject.config;

import com.work.finalproject.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private MemberService memberService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public void configure(WebSecurity web) throws Exception{
        //static 디렉터리 하위 파일 목록은 인증 무시 (=자동통과)
        web.ignoring().antMatchers("/css/**","/js/**","/img/**","/lib/**","/test/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
            //각 페이지 권한설정
                //.antMatchers("/admin/**").hasRole("ADMIN")        //admin 으로 시작하는 경로는 ADMIN 롤을 가진 사용자만 접근 가능
                //.antMatchers("/member/mypage").hasRole("MEMBER")  //user/myinfo 경로는 MEMBER 롤을 가진 사용자만 접근 가능
                .antMatchers("/**").permitAll()          //모든 경로에 대해서는 권한없이 접근 가능
                .and()
            //로그인설정
                .formLogin()
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
             //로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and()
             //403 예외처리 핸들링
                .exceptionHandling();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 로그인 처리를 하기 위한 AuthenticationManagerBuilder를 설정
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
