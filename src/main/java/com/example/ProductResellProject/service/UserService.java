package com.example.ProductResellProject.service;

import com.example.ProductResellProject.domain.users.User;
import com.example.ProductResellProject.domain.users.UsersRepository;
import com.example.ProductResellProject.web.dto.LoginInfoDto;
import com.example.ProductResellProject.web.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long save(UserInfoDto userInfoDto) {

        validateDuplicateUser(userInfoDto.getUserId()); // ID 중복 체크
        validatePwCheck(userInfoDto.getUserPwd(), userInfoDto.getUserPwdCheck()); // pw 체크

        String encodePwd = passwordEncoder.encode(userInfoDto.getUserPwd());
        return usersRepository.save(userInfoDto.toEntity(encodePwd)).getId();
    }

    private void validatePwCheck(String pw, String pwCheck) {
        if(!pw.equals(pwCheck)){    // pwd 체크
            throw new IllegalArgumentException("Password가 동일하지 않습니다.");
        }
    }

    private void validateDuplicateUser(String userId){
        usersRepository.findByUserId(userId).ifPresent(user -> {
            throw new IllegalArgumentException("아이디 중복 id= " + userId);
        });
    }

    public Long login(LoginInfoDto loginInfoDto, HttpServletRequest request){
        Optional<User> user = usersRepository.findByUserId(loginInfoDto.getUserId());
        User userRole = user.get();
        String roleCheck = String.valueOf(userRole.getRoles());
        HttpSession session = request.getSession();

        check(loginInfoDto.getUserId(), loginInfoDto.getUserPwd());

        if(roleCheck.equals("ROLE_ADMIN")) {
            session.setAttribute("role", roleCheck);
        } else if(roleCheck.equals("ROLE_USER")){
            session.setAttribute("role", roleCheck);
        }

        session.setAttribute("user", loginInfoDto.getUserId());

        return 12L;
    }

    private boolean check(String userId, String userPwd){
        Optional<User> userWrapper = usersRepository.findByUserId(userId);
        User member = userWrapper.get();
        if (userWrapper==null){
            log.info("해당 아이디가 존재하지 않습니다.");
            return false;
        }

        if(!passwordEncoder.matches(userPwd, member.getUserPwd())){
            throw new BadCredentialsException("비밀번호가 틀립니다.");
        }
        return true;
    }
}
