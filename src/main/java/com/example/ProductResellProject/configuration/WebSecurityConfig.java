package com.example.ProductResellProject.configuration;

import com.example.ProductResellProject.user.domain.UsersRepository;
import com.example.ProductResellProject.filter.JwtAuthorizationFilter;
import com.example.ProductResellProject.filter.JwtAuthenticationFilter;
import com.example.ProductResellProject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    UserService userService;

    private final CorsFilter corsFilter;
    private final UsersRepository usersRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/h2-console/**", "/favicon.ico", "**.png");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable();

        // 세션 만드는 방식을 안씀
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                //http로그인방식을 안씀
                .httpBasic().disable() // Basic : ID,PW을 header에 담고 서버로 감, Bearer : 헤더에 토큰을 담고 서버로 감.
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))  // AuthenticationManager 파라미터를 넘겨줘야함
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), usersRepository))
                .authorizeRequests()
                //.anyRequest().permitAll();
                .antMatchers("/login-select", "/login", "/signup").permitAll()
                .antMatchers("/api/v1/**").access("hasRole('ROLE_USER')");
                //.anyRequest().authenticated();
    }
}
