package com.example.ProductResellProject.web;

import com.example.ProductResellProject.service.PostsService;
import com.example.ProductResellProject.service.PostsServiceImpl;
import com.example.ProductResellProject.web.dto.PostsResponseDto;
import com.example.ProductResellProject.web.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;
    private final HttpSession session;

    @GetMapping("/posts/save")
    public String postSave() {
        return "posts-save";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
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
}
