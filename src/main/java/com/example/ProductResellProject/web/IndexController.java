package com.example.ProductResellProject.web;

import com.example.ProductResellProject.service.PostsService;
import com.example.ProductResellProject.service.UserService;
import com.example.ProductResellProject.web.dto.LoginInfoDto;
import com.example.ProductResellProject.web.dto.PostsResponseDto;
import com.example.ProductResellProject.web.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostsService postsService;
    private final UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @RequestMapping(value = "/signup/request", method = RequestMethod.POST)
    @ResponseBody
    public Long requestSignUp(@RequestBody UserInfoDto userInfoDto) {
        return userService.save(userInfoDto);
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value ="/login/request", method = RequestMethod.POST)
    @ResponseBody
    public Long requestLogin(@RequestBody LoginInfoDto loginInfoDto) {
        return userService.login(loginInfoDto);
    }

}