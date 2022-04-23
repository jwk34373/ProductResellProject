package com.example.ProductResellProject.common.web;
import com.example.ProductResellProject.Post.domain.Post;
import com.example.ProductResellProject.Post.service.PostCrudService;
import com.example.ProductResellProject.configuration.auth.PrincipalDetails;
import com.example.ProductResellProject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {


    private final PostCrudService postCrudService;
    private final UserService userService;

    @GetMapping("/")
    public String index(Authentication authentication, Model model,
                        @PageableDefault(page = 0, size = 6, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                        String searchKeyword) {

        Page<Post> list = null;

        if(searchKeyword == null) {
            list = postCrudService.findAll(pageable);
        } else {
            list = postCrudService.postsSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("posts", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        if (authentication != null) {
            PrincipalDetails principal = (PrincipalDetails)authentication.getPrincipal();
            model.addAttribute("user", principal.getUser().getName());
        }
        return "index";
    }
}
