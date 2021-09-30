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
    private String userPwdCheck;

    @Builder
    public UserInfoDto(String userId, String userPwd, String name, String userPwdCheck) {
        this.userId = userId;
        this.userPwd = userPwd;
        this.name = name;
        this.userPwdCheck = userPwdCheck;
    }

}
