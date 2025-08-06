package com.example.suntcamp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Postman 테스트 위해 CSRF 끄기
                .authorizeHttpRequests()
                .anyRequest().permitAll(); // 모든 요청 허용
        return http.build();
    }
}
