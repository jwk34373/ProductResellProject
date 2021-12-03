package com.example.ProductResellProject.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.ProductResellProject.configuration.auth.PrincipalDetails;
import com.example.ProductResellProject.domain.users.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

// 스프링 시큐리티에 UsernamePasswordAuthenticationFilter 가 있음
// /login 요청해서 username, password 전송(post로)
// UsernamePasswordAuthenticationFilter 동작

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter : 로그인 시도중");
        // username과 passowrd를 받음
        try {
/*          BufferedReader br = request.getReader();

            String info = br.readLine();
            String[] userInfo = info.split("=|&");

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userInfo[1], userInfo[3]);  //userInfo[1] : username , userInfo[3] : password*/

            // JSON 요청 시
            ObjectMapper om = new ObjectMapper(); //json data를 parsing해줌
            User user = om.readValue(request.getInputStream(), User.class);

            System.out.println("user.getUserId(): "+user.getUserId()+" / user.getUserPwd()) : "+user.getUserPwd());

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUserId(), user.getUserPwd());

            // PrincipalDetailsService의 LoadUserByUsername() 함수가 실행된 후 정상이면 authentication이 리턴됨.
            // DB에 있는 userId와 userPwd가 일치
            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);

            // 로그인 완료
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("로그인 완료됨 : "+principalDetails.getUser().getUserId());

            // return 을 함으로써 authentication 객체가 session 영역에 저장됨.
            // return 의 이유는 권한 관리를 security 가 대신 해주기 때문에 편하려고
            return authentication;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행
    // JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 응답해줌.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨 : 인증이 완료되었음.");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        // Hash 암호 방식
        String jwtToken = JWT.create()
                .withSubject("cos토큰")
                .withExpiresAt(new Date(System.currentTimeMillis()+(1000 * 60 * 60)))
                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("username", principalDetails.getUser().getUserId())
                .sign(Algorithm.HMAC512("cos"));

        // 쿠키 추가
        Cookie cookie = new Cookie("Authorization", "Bearer-" + jwtToken);
        response.addCookie(cookie);

        response.getOutputStream().write(jwtToken.getBytes());
    }
}
