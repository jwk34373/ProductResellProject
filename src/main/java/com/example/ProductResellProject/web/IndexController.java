package com.example.ProductResellProject.web;

import com.example.ProductResellProject.service.PostsService;
import com.example.ProductResellProject.service.UserService;
import com.example.ProductResellProject.web.dto.PostsResponseDto;
import com.example.ProductResellProject.web.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

    @GetMapping("/posts/detail/{id}")
    public String postDetail(@PathVariable Long id, Model model, UserInfoDto userInfoDto) {
        String user = (String) session.getAttribute("user");
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        String userRole = (String) session.getAttribute("role");

        if(user == null) {
            return "redirect:/";
        }

        if(user.equals(dto.getAuthor()) || userRole.equals("ROLE_ADMIN")) {
            model.addAttribute("writer", true);
        } else {
            model.addAttribute("writer", false);
        }

        return "posts-detail";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
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
    public String login(){
        return "login";
    }

    @GetMapping("/login-select")
    public String loginSelect(){
        return "login-select";
    }

/*    @RequestMapping(value ="/login/request", method = RequestMethod.POST)
    @ResponseBody
    public Long requestLogin(@RequestBody LoginInfoDto loginInfoDto, HttpServletRequest request) {
        return userService.login(loginInfoDto, request);
    }*/
}
