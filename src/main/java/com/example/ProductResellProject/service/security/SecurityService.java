package com.example.ProductResellProject.service.security;

import com.example.ProductResellProject.domain.users.User;
import com.example.ProductResellProject.domain.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    @Transactional
    public SecurityUser loadUserByUsername(String userId) throws UsernameNotFoundException {
        return usersRepository.findByUserId(userId)
                .filter(m -> m!=null)
                .map(m-> new SecurityUser(m))
                .get();

    }
}
