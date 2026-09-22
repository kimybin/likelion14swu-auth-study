package com.example.authstudy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 비밀번호 암호화 인코더 - BCrypt 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // URL별 인가 규칙: /signup은 누구나, 나머지는 인증 필요
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/signup").permitAll()
                        .anyRequest().authenticated()
                )
                // 기본 폼 로그인 사용 -> 로그인 성공 시 세션 생성
                .formLogin(form -> form
                        .loginProcessingUrl("/login") // 이 URL로 POST하면 Security가 자동으로 인증 처리
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .permitAll()
                )
                // 실습 편의를 위해 CSRF 비활성화 (Postman 테스트용)
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}