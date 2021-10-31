package com.example.ProductResellProject.configuration.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킴
// 로그인 진행이 완료가 되면 시큐리티 session을 만들어 줌. (Security ContextHolder) <- 이 값에 저장해줌.
// 오브젝트 타입 -> Authentication 타입 객체
// Authentication 안에 User정보가 있어야 됨.
// User오브젝트의 타입 -> UserDetails 타입 객체

// Security Session(내부 Authentication(내부 UserDetails(PrincipalDetails))) ->  ->

import com.example.ProductResellProject.domain.users.Role;
import com.example.ProductResellProject.domain.users.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // user.getRole(); 해당 권한은 String 타입
        Collection<GrantedAuthority> collect = new ArrayList<>();
        String getRole = Role.values().toString();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return getRole;
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getUserPwd();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
