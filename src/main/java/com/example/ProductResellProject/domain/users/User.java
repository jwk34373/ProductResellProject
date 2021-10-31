package com.example.ProductResellProject.domain.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

/*    public List<String> getRoleList() {
        if(this.roles.length() > 0 ){
            return Arrays.asList(this.roles.split(","));
        }
        return null;
    }*/


    //== 생성 메서드 ==//
    @Builder
    public User(String userId, String userPwd, String name, Role role) {
        this.userId = userId;
        this.userPwd = userPwd;
        this.name = name;
        this.role = role;
    }

}

