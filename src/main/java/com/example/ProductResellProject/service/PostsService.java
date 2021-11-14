package com.example.ProductResellProject.service;

import com.example.ProductResellProject.web.dto.PostsListResponseDto;
import com.example.ProductResellProject.web.dto.PostsResponseDto;
import com.example.ProductResellProject.web.dto.PostsSaveRequestDto;
import com.example.ProductResellProject.web.dto.PostsUpdateRequestDto;

import java.util.List;

public interface PostsService {

    Long save(PostsSaveRequestDto requestDto);
    Long update(Long id, PostsUpdateRequestDto requestDto);
    PostsResponseDto findById (Long id);
    List<PostsListResponseDto> findAllDesc();
    void delete(Long id);

}
