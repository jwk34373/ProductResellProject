package com.example.ProductResellProject.configuration;

import com.example.ProductResellProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    UserService userService;

    private final CorsFilter corsFilter;

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
                .headers().frameOptions().disable()

                // h2-console 을 위한 설정을 추가
                .and()
                .headers()
                .frameOptions()
                .sameOrigin();

                // 로그인 화면
                //.and()
                //.formLogin()
                //.loginPage("/login")
                //.loginProcessingUrl("/login")  // login주소가 호출되면 시큐리티가 낚아채서 대신 로그인을 진행해줌
                //.defaultSuccessUrl("/")

                // 로그아웃
                //.and()
                //.logout()
                //.logoutSuccessUrl("/")
                //.invalidateHttpSession(true);

                // 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
                //.and()
                //.authorizeRequests()
                //.antMatchers("/", "/login-select", "/login", "/signup").permitAll()
                //.anyRequest().authenticated();

        // 세션 만드는 방식을 안씀
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter) //@CrossOrigin(인증 X), 필터에 등록 인증(O)
                .formLogin().disable()
                //http로그인방식을 안씀
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/", "/login-select", "/login", "/signup").permitAll()
                .anyRequest().authenticated();
    }
}
