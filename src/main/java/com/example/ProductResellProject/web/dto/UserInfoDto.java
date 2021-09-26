package com.example.ProductResellProject.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoDto {
    private String userId;
    private String userPwd;
    private String name;
    //pwdcheck

    @Builder
    public UserInfoDto(String userId, String userPwd, String name) {
        this.userId = userId;
        this.userPwd = userPwd;
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserInfoDto{" +
                "userId='" + userId + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
