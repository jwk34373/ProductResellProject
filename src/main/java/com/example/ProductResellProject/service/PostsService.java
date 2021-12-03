package com.example.ProductResellProject.service;

import com.example.ProductResellProject.domain.posts.Posts;
import com.example.ProductResellProject.web.dto.PostsResponseDto;
import com.example.ProductResellProject.web.dto.PostsSaveRequestDto;
import com.example.ProductResellProject.web.dto.PostsUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostsService {

    Long save(PostsSaveRequestDto requestDto);
    Long update(Long id, PostsUpdateRequestDto requestDto);
    PostsResponseDto findById (Long id);
    //Page<PostsListResponseDto> findAllDesc(Pageable pageable);
    Page<Posts> findAll(Pageable pageable);
    void delete(Long id);

}
