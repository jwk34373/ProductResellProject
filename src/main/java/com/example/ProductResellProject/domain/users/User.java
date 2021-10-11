package com.example.ProductResellProject.domain.users;

import com.example.ProductResellProject.web.dto.UserInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Getter
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_code")
    private long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String userPwd;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    //== 생성 메서드 ==//
    @Builder
    public User(String userId, String userPwd, String name, Role role) {
        this.userId = userId;
        this.userPwd = userPwd;
        this.name = name;
        this.role = role;
    }

}

