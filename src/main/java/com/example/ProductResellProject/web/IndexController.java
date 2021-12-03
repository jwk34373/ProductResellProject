package com.example.ProductResellProject.web;

import com.example.ProductResellProject.configuration.auth.PrincipalDetails;
import com.example.ProductResellProject.service.FileSystemStorageService;
import com.example.ProductResellProject.service.PostsService;
import com.example.ProductResellProject.service.UserService;
import com.example.ProductResellProject.web.dto.LoginInfoDto;
import com.example.ProductResellProject.web.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {


    private final PostsService postsService;
    private final UserService userService;

    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        if (authentication != null) {
            PrincipalDetails principal = (PrincipalDetails)authentication.getPrincipal();
            model.addAttribute("user", principal.getUser().getName());
        }
        return "index";
    }



    @RequestMapping(value = "/signup/request", method = RequestMethod.POST)
    @ResponseBody
    public Long requestSignUp(@RequestBody @Valid UserInfoDto userInfoDto,
                              BindingResult errors) {
        if(errors.hasErrors()){
            errors.getFieldErrors().stream().forEach(fieldError -> {
                String fieldName = fieldError.getField();
                String errorMsg = fieldError.getDefaultMessage();
                throw new IllegalArgumentException(fieldName + " : " + errorMsg);
            });
        }
        return userService.save(userInfoDto);
    }

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
