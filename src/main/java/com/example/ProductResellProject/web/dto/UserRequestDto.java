package com.example.ProductResellProject.web.dto;

import com.example.ProductResellProject.domain.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private String userid;
    private String userPwd;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .userId(userid)
                .userPwd(passwordEncoder.encode(userPwd))
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(userid, userPwd);
    }
}
