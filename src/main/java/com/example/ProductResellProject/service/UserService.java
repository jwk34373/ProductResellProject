package com.example.ProductResellProject.service;

import com.example.ProductResellProject.domain.users.User;
import com.example.ProductResellProject.domain.users.UsersRepository;
import com.example.ProductResellProject.web.dto.UserInfoDto;
import javassist.bytecode.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long save(UserInfoDto userInfoDto) {
//        log.info(userInfoDto.toString());
//        log.info("pwd encode : " + passwordEncoder.encode(userInfoDto.getUserPwd()));

        validateDuplicateUser(userInfoDto.getUserId()); // ID 중복 체크
        if(!userInfoDto.getUserPwd().equals(userInfoDto.getUserPwdCheck())){    // pwd 체크
            throw new RuntimeException("비밀번호가 동일하지 않습니다.");
//          throw new IllegalStateException("비밀번호가 동일하지 않습니다. ")
        }

        String encodePwd = passwordEncoder.encode(userInfoDto.getUserPwd());
        return usersRepository.save(userInfoDto.toEntity(encodePwd)).getId();

    }

    // userId 중복검사
    private void validateDuplicateUser(String userId){
        Optional<User> user = usersRepository.findByUserId(userId);
        log.info("userId : " + userId);
        user.ifPresent(findUser -> {
            throw new RuntimeException("아이디 중복");
        });
    }

}
