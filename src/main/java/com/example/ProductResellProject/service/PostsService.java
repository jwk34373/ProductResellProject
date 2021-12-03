package com.example.ProductResellProject.service;

import com.example.ProductResellProject.domain.posts.Posts;
import com.example.ProductResellProject.domain.posts.PostsRepository;
import com.example.ProductResellProject.domain.users.UsersRepository;
import com.example.ProductResellProject.web.dto.PostsResponseDto;
import com.example.ProductResellProject.web.dto.PostsSaveRequestDto;
import com.example.ProductResellProject.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final UsersRepository usersRepository;

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
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id));

        return new PostsResponseDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        //Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.deleteById(id);
    }

    /**
     * 유저가 작성한 post가 맞음 ??
     * return : 맞으면 true, 아니면 false
     */
    @Transactional
    public boolean isMyPost(Long userId, Long postId){

        Posts posts = postsRepository.findById(postId).get();

        // posts 객체 저장할때 user 저장 해주게 바꾸면 돌아갈듯 ??
        // 지금은 안돌아감 ㅜ.ㅜ
        if(posts.getUser().getId() == userId){
            return true;
        }
        return false;
    }

    public Page<Posts> findAll(Pageable pageable) {
        return postsRepository.findAll(pageable);
    }

    public Page<Posts> postsSearchList(String searchKeyword, Pageable pageable) {
        return postsRepository.findByTitleContaining(searchKeyword, pageable);
    }
}