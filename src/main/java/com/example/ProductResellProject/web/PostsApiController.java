package com.example.ProductResellProject.web;

import com.example.ProductResellProject.Post.service.PostCrudService;
import com.example.ProductResellProject.configuration.auth.PrincipalDetails;
import com.example.ProductResellProject.web.dto.PostSaveRequestForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@Slf4j
public class PostsApiController {
    private final PostCrudService postsService;

    @PostMapping("/api/v1/posts")
    public String save(@ModelAttribute PostSaveRequestForm form, Authentication authentication) throws IOException {
        PrincipalDetails principal = (PrincipalDetails)authentication.getPrincipal();

        postsService.save(form, principal.getUsername());
        return "success";
    }

//    @PutMapping("/api/v1/posts/{id}")
//    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto, MultipartHttpServletRequest req) {
//        storageService.store(req.getFile("file"), id);
//        return postsService.update(id, requestDto);
//    }

//    @GetMapping("/api/v1/posts/{id}")
//    public PostsResponseDto findById(@PathVariable Long id) {
//        return postsService.findById(id);
//    }

//    @DeleteMapping("/api/v1/posts/{id}")
//    public Long delete(@PathVariable Long id) {
//        postsService.delete(id);
//        return id;
//    }
}