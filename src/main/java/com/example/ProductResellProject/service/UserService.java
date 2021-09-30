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
        validateDuplicateUser(userInfoDto.getUserId()); // ID 중복 체크
        if(!userInfoDto.getUserPwd().equals(userInfoDto.getUserPwdCheck())){    // pwd 체크
            throw new IllegalStateException("Password가 동일하지 않습니다.");
        }
        User user = User.builder()  // 나중에 dto로 옮기면 깔금함
                .userId(userInfoDto.getUserId())
                .userPwd(userInfoDto.getUserPwd())
                .name(userInfoDto.getName())
                .build();

        User newUser = usersRepository.save(user);  // user 저장
        return newUser.getId();

    }


    @Override
    @Transactional
    public User loadUserByUsername(String userId) throws UsernameNotFoundException {
        return usersRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("not found userId : "+userId));
    }

    // userId 중복검사
    private void validateDuplicateUser(String userId){
        Optional<User> user = usersRepository.findByUserId(userId);
        log.info("userId : " + userId);
        user.ifPresent(findUser -> {
            throw new IllegalStateException("아이디 중복");
        });
    }

}
