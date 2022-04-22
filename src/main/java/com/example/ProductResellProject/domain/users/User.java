package com.example.ProductResellProject.domain.users;

import com.example.ProductResellProject.Post.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_code")
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String userPwd;

    private String name;

    private String roles;
    /**
     *  if admin ->  ROLE_USER,ROLE_ADMIN
     */
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    public List<String> getRoleList() {
        if(roles.length() > 0) {
            return Arrays.asList(roles.split(","));
        }
        return new ArrayList<>();
    }

    //== 생성 메서드 ==//
    @Builder
    public User(String userId, String userPwd, String name, String roles) {
        this.userId = userId;
        this.userPwd = userPwd;
        this.name = name;
        this.roles = roles;
    }
}

