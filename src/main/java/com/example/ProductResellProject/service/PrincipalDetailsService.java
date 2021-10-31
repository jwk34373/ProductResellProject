package com.example.ProductResellProject.service;

import com.example.ProductResellProject.configuration.auth.PrincipalDetails;
import com.example.ProductResellProject.domain.users.User;
import com.example.ProductResellProject.domain.users.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

// 시큐리티 설정에서 loginProcessingIrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행 됨.
@Service
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    private HttpServletRequest request;
    private HttpSession session;

    // 시큐리티 Session = Authentication = UserDetails(PrincipalDetails)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userEntity = usersRepository.findByUserId(username);
        String roleCheck = String.valueOf(userEntity.get().getRole());

        session = request.getSession();
        if(session != null) {
            User user = (User) session.getAttribute("user");
        }

        if(roleCheck.equals("ROLE_ADMIN")) {
            session.setAttribute("role", roleCheck);
        } else if(roleCheck.equals("ROLE_USER")){
            session.setAttribute("role", roleCheck);
        }

        session.setAttribute("user", userEntity.get().getUserId());
        log.info("success");
        if (userEntity != null) {
            return new PrincipalDetails(userEntity.get());
        }

        return null;
    }
}
