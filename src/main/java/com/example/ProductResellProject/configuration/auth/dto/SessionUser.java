package com.example.ProductResellProject.configuration.auth.dto;

import com.example.ProductResellProject.domain.users.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String userId;
    private String userPwd;

    public SessionUser(User user) {
        this.userId = user.getUserId();
        this.userPwd = user.getUserPwd();
    }
}
