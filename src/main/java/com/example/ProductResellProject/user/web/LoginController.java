package com.example.ProductResellProject.user.web;

import com.example.ProductResellProject.user.api.dto.LoginInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("login", new LoginInfoDto());
        return "login";
    }

    @GetMapping("/login-select")
    public String loginSelect(){
        return "login-select";
    }
}
