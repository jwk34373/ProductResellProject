package com.example.ProductResellProject.web.dto;

import com.example.ProductResellProject.domain.users.Role;
import com.example.ProductResellProject.domain.users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoDto {
    private String userId;
    private String userPwd;
    private String name;
    private String userPwdCheck;
    private Role role;

    @Builder
    public UserInfoDto(String userId, String userPwd, String name, String userPwdCheck, Role role) {
        this.userId = userId;
        this.userPwd = userPwd;
        this.name = name;
        this.userPwdCheck = userPwdCheck;
        this.role = role;
    }

    public User toEntity(String bcryptPwd){
        return  User.builder()
                .userId(userId)
                .userPwd(bcryptPwd)
                .name(name)
                .role(role)
                .build();
    }

    @Override
    public String toString() {
        return "UserInfoDto{" +
                "userId='" + userId + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", name='" + name + '\'' +
                ", userPwdCheck='" + userPwdCheck + '\'' +
                '}';
    }
}
