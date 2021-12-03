package com.example.ProductResellProject.service;

import com.example.ProductResellProject.domain.posts.Posts;
import com.example.ProductResellProject.domain.posts.PostsRepository;
import com.example.ProductResellProject.web.dto.PostsResponseDto;
import com.example.ProductResellProject.web.dto.PostsSaveRequestDto;
import com.example.ProductResellProject.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsServiceImpl implements PostsService{
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        Posts posts = requestDto.toEntity();

        postsRepository.save(posts);
        return posts.imageUpdate(Long.toString(posts.getId())).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById (Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id));

        return new PostsResponseDto(entity);
    }


    @Override
    public Page<Posts> findAll(Pageable pageable) {
        return postsRepository.findAll(pageable);
    }

    public Page<Posts> postsSearchList(String searchKeyword, Pageable pageable) {
        return postsRepository.findByTitleContaining(searchKeyword, pageable);
    }

/*    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // (post) -> {PostsListResponseDto(post)}
                .collect(Collectors.toList());  // stream -> List

    }*/

    @Transactional
    public void delete(Long id) {
        //Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.deleteById(id);
    }
}