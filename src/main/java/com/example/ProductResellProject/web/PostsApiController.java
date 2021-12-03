package com.example.ProductResellProject.web;

import com.example.ProductResellProject.configuration.auth.PrincipalDetails;
import com.example.ProductResellProject.service.FileSystemStorageService;
import com.example.ProductResellProject.service.PostsService;
import com.example.ProductResellProject.web.dto.PostsResponseDto;
import com.example.ProductResellProject.web.dto.PostsSaveRequestDto;
import com.example.ProductResellProject.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
@Slf4j
public class PostsApiController {
    private final PostsService postsService;
    private final FileSystemStorageService storageService;

    @PostMapping("/api/v1/posts")
    public Long save(MultipartHttpServletRequest req, Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails)authentication.getPrincipal();

        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .title(req.getParameter("title"))
                .author(principal.getUser().getName())
                .content(req.getParameter("content"))
                .build();

        Long postId = postsService.save(dto);
        storageService.store(req.getFile("file"), postId);
        return 1L;
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto, MultipartHttpServletRequest req) {
        storageService.store(req.getFile("file"), id);
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
}