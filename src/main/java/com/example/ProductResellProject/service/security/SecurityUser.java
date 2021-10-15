package com.example.ProductResellProject.service.security;

import com.example.ProductResellProject.domain.users.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@NoArgsConstructor
public class SecurityUser implements UserDetails {
    private String userId;
    private String userPwd;
    private String name;

    private boolean isEnabled;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private Collection<? extends  GrantedAuthority> authorities;

    public SecurityUser(User user){
        userId = user.getUserId();
        userPwd = user.getUserPwd();
        name = user.getName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Role
        return authorities;
    }

    @Override
    public String getPassword() {
        return userPwd;
    }

    @Override
    public String getUsername() {
        return userId;
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
