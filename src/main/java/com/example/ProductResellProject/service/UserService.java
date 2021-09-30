package com.example.ProductResellProject.service;

import com.example.ProductResellProject.domain.users.User;
import com.example.ProductResellProject.domain.users.UsersRepository;
import com.example.ProductResellProject.web.dto.LoginInfoDto;
import com.example.ProductResellProject.web.dto.UserInfoDto;
import javassist.bytecode.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long save(UserInfoDto userInfoDto) {
//        log.info(userInfoDto.toString());
//        log.info("pwd encode : " + passwordEncoder.encode(userInfoDto.getUserPwd()));

        validateDuplicateUser(userInfoDto.getUserId()); // ID 중복 체크
        if(!userInfoDto.getUserPwd().equals(userInfoDto.getUserPwdCheck())){    // pwd 체크
            throw new IllegalStateException("Password가 동일하지 않습니다.");
        }

        String encodePwd = passwordEncoder.encode(userInfoDto.getUserPwd());
        return usersRepository.save(userInfoDto.toEntity(encodePwd)).getId();

    }
    // userId 중복검사
    private void validateDuplicateUser(String userId){
        Optional<User> user = usersRepository.findByUserId(userId);
        log.info("userId : " + userId);
        user.ifPresent(findUser -> {
            throw new IllegalStateException("아이디 중복");
        });
    }

    public Long login(LoginInfoDto loginInfoDto){
        check(loginInfoDto.getUserId(), loginInfoDto.getUserPwd());
        return 1L;
    }

    private boolean check(String userId, String userPwd){
        Optional<User> userWrapper = usersRepository.findByUserId(userId);

        if (userWrapper==null){
            log.info("해당 아이디가 존재하지 않습니다.");
            return false;
        }

        if(!passwordEncoder.matches(userPwd, userWrapper.get().getUserPwd())){
            log.info(" 비밀번호가 일치하지 않습니다.");
            return false;
        }

        return true;
    }

    @Override
    @Transactional
    public User loadUserByUsername(String userId) throws UsernameNotFoundException {
        return usersRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("not found userId : "+userId));
    }

}
