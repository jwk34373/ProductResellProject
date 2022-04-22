package com.example.ProductResellProject.web;

import com.example.ProductResellProject.Post.service.PostCrudService;
import com.example.ProductResellProject.configuration.auth.PrincipalDetails;
import com.example.ProductResellProject.domain.users.RoleType;
import com.example.ProductResellProject.web.dto.PostsResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostsController {

    private final PostCrudService postsService;
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
    @ApiOperation(value = "물품 상세보기", notes = "등록한 물품 상세 볼 수 있음.")
    public String postDetail(@PathVariable String id, Model model, Authentication authentication) {

        PostsResponseDto dto = postsService.findById(Long.parseLong(id));
        model.addAttribute("post", dto);

        if(authentication == null){
            return "redirect:/";
        }

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        for (GrantedAuthority authority : (List<GrantedAuthority>) principal.getAuthorities()) {
            if(authority.equals(RoleType.ADMIN)) {
                // admin 일때 처리 ...
            }
        }

        // user가 그 post를 작성했나 ??
        Boolean writer = postsService.isMyPost(principal.getUser().getId(), Long.parseLong(id));
        model.addAttribute("writer", writer);

        return "posts-detail";
    }
}
