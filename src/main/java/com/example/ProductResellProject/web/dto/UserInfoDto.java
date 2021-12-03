package com.example.ProductResellProject.web.dto;

import com.example.ProductResellProject.domain.users.Role;
import com.example.ProductResellProject.domain.users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserInfoDto {

    @NotBlank
    @Size(min = 3, max = 20)
    private String userId;
    @NotBlank
    @Size(min = 3, max = 20)
    private String userPwd;
    @NotBlank
    @Size(min = 3, max = 20)
    private String name;
    @NotBlank
    @Size(min = 3, max = 20)
    private String userPwdCheck;
    private List<Role> role;

    @Builder
    public UserInfoDto(String userId, String userPwd, String name, String userPwdCheck, List<Role> role) {
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
                .roles(role)
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
