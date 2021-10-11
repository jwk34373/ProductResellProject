package com.example.ProductResellProject.service;

import com.example.ProductResellProject.domain.users.User;
import com.example.ProductResellProject.domain.users.UsersRepository;
import com.example.ProductResellProject.web.dto.LoginInfoDto;
import com.example.ProductResellProject.web.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService{

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
            throw new RuntimeException("아이디 중복");
        });
    }

//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//        return usersRepository.findByUserId(userId)
//                .orElseThrow(() -> new UsernameNotFoundException("USER ID가 존재하지 않습니다."));
//    }

    public Long login(LoginInfoDto loginInfoDto, HttpServletRequest request){
        check(loginInfoDto.getUserId(), loginInfoDto.getUserPwd());

        HttpSession session = request.getSession();
        session.setAttribute("user", loginInfoDto.getUserId());

        return 12L;
    }

    private boolean check(String userId, String userPwd){
        Optional<User> userWrapper = usersRepository.findByUserId(userId);

        if (userWrapper==null){
            log.info("해당 아이디가 존재하지 않습니다.");
            return false;
        }

        if(!passwordEncoder.matches(userPwd, userWrapper.get().getUserPwd())){
            throw new BadCredentialsException("비밀번호가 틀립니다.");
        }
        return true;
    }

}



//jwt token 테스트 코드

/*    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return usersRepository.findByUserId(userId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("USER ID가 존재하지 않습니다."));
    }

    private UserDetails createUserDetails(User user){
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getAuthorities().toString());

        return new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getUserId()),
                user.getUserPwd(),
                Collections.singleton(grantedAuthority)
        );
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserInfo(String userid){
        return usersRepository.findByUserId(userid)
                .map(UserResponseDto::of)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }

    //현재 SecurityContext에 있는 유저 정보 가져오기
    @Transactional(readOnly = true)
    public UserResponseDto getMyInfo() {
        return usersRepository.findById(SecurityUtil.getCurrentUserId())
                .map(UserResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }*/
