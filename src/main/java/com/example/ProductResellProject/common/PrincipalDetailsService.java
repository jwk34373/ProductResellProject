package com.example.ProductResellProject.common;


import com.example.ProductResellProject.configuration.auth.PrincipalDetails;
import com.example.ProductResellProject.user.domain.User;
import com.example.ProductResellProject.user.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService 의 loadUserByUsername()");
        Optional<User> users = usersRepository.findByUserId(username);
        User userEntity = users.get();
        //System.out.println("=================================== : " + userEntity.getRoles());
        return new PrincipalDetails(userEntity);
    }
}
