package com.example.ProductResellProject.service;

import com.example.ProductResellProject.domain.users.PrincipalDetail;
import com.example.ProductResellProject.domain.users.User;
import com.example.ProductResellProject.domain.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {

    private UsersRepository userRepository;

    @Autowired
    public PrincipalDetailService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User principal = userRepository.findByUserId(userId)
                .orElseThrow(() ->{
                    return new UsernameNotFoundException("해당 사용자를 찾을수 없습니다.:" + userId);
                });
        return new PrincipalDetail(principal);
    }
}
