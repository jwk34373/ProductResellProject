package com.example.ProductResellProject.service;

import com.example.ProductResellProject.domain.users.User;
import com.example.ProductResellProject.domain.users.UsersRepository;
import com.example.ProductResellProject.web.dto.UserInfoDto;
import javassist.bytecode.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Transactional
    public Long save(UserInfoDto userInfoDto) {
//        log.info(userInfoDto.toString());
        validateDuplicateUser(userInfoDto.getUserId());
        User user = User.builder()
                .userId(userInfoDto.getUserId())
                .userPwd(userInfoDto.getUserPwd())
                .name(userInfoDto.getName())
                .build();
        User newUser = usersRepository.save(user);
        return newUser.getId();

    }

    @Override
    @Transactional
    public User loadUserByUsername(String userId) throws UsernameNotFoundException {
        return usersRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("not found userId : "+userId));
    }

    // userId 중복검사
    public void validateDuplicateUser(String userId){
        Optional<User> user = usersRepository.findByUserId(userId);
        log.info("userId : " + userId);
        user.ifPresent(findUser -> {
            throw new IllegalStateException("아이디 중복");
        });
    }
}
