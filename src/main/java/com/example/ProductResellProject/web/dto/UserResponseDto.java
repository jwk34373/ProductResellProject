package com.example.ProductResellProject.web.dto;

import com.example.ProductResellProject.domain.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private String userid;

    public static UserResponseDto of(User user){
        return new UserResponseDto(user.getUserId());
    }
}
