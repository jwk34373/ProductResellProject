package com.example.ProductResellProject.web;

import com.example.ProductResellProject.service.FileSystemStorageService;
import com.example.ProductResellProject.service.PostsService;
import com.example.ProductResellProject.web.dto.PostsResponseDto;
import com.example.ProductResellProject.web.dto.PostsSaveRequestDto;
import com.example.ProductResellProject.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
@Slf4j
public class PostsApiController {
    private final PostsService postsService;
    private final FileSystemStorageService storageService;
    private final HttpSession session;

    @PostMapping("/api/v1/posts")
    public Long save(MultipartHttpServletRequest req) {
        if(session == null) throw new IllegalStateException("login 필요");

        String user = session.getAttribute("user").toString();

        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .title(req.getParameter("title"))
                .author(user)
                .content(req.getParameter("content"))
                .build();

        Long postId = postsService.save(dto);
        storageService.store(req.getFile("file"), postId);
        return 1L;
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
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