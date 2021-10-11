package com.example.ProductResellProject.domain.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User implements UserDetails {

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


    // 야발 이게 뭐임??? ㅋㅋ
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    // 사용자의 Password를 반환
    @Override
    public String getPassword() {
        return userPwd;
    }

    // 사용자의 id를 반환
    @Override
    public String getUsername() {
        return userId;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 이후 만료되었는지 확인하는 로직 구현
        return true;
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 이후 로직 구현
        return true;
    }

    // 패스워드 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 이후 로직 구현
        return true;
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 이후 로직 구현
        return false;
    }

    //== 생성 메서드 ==//
    @Builder
    public User(String userId, String userPwd, String name, Role role) {
        this.userId = userId;
        this.userPwd = userPwd;
        this.name = name;
        this.role = role;
    }



}

