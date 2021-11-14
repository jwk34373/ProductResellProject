package com.example.ProductResellProject.web;

import com.example.ProductResellProject.domain.users.UsersRepository;
import com.example.ProductResellProject.service.PostsService;
import com.example.ProductResellProject.service.UserService;
import com.example.ProductResellProject.web.dto.LoginInfoDto;
import com.example.ProductResellProject.web.dto.PostsResponseDto;
import com.example.ProductResellProject.web.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostsService postsService;
    private final UserService userService;
    private final HttpSession session;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        String user = (String) session.getAttribute("user");
        if(user != null) {
            model.addAttribute("user", user);
        }
        return "index";
    }

/*    @GetMapping("/posts/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }*/

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

    @RequestMapping(value ="/login/request", method = RequestMethod.POST)
    @ResponseBody
    public Long requestLogin(@RequestBody LoginInfoDto loginInfoDto, HttpServletRequest request) {
        return userService.login(loginInfoDto, request);
    }
}
