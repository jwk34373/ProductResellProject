package com.example.ProductResellProject.web;

import com.example.ProductResellProject.domain.posts.Posts;
import com.example.ProductResellProject.service.FileSystemStorageService;
import com.example.ProductResellProject.service.PostsService;
import com.example.ProductResellProject.service.PostsServiceImpl;
import com.example.ProductResellProject.service.UserService;
import com.example.ProductResellProject.web.dto.LoginInfoDto;
import com.example.ProductResellProject.web.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    private final HttpSession session;
    private final FileSystemStorageService storageService;
    private final PostsServiceImpl postsServiceImpl;

    @GetMapping("/")
    public String index(Model model, @PageableDefault(page = 0, size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String searchKeyword) {

        Page<Posts> list = null;

        if(searchKeyword == null) {
            list = postsService.findAll(pageable);
        } else {
            list = postsServiceImpl.postsSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("posts", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        String user = (String) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "index.html";
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
