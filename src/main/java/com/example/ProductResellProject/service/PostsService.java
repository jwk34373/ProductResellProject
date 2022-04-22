package com.example.ProductResellProject.service;

import com.example.ProductResellProject.domain.posts.Posts;
import com.example.ProductResellProject.domain.posts.PostsRepository;
import com.example.ProductResellProject.domain.posts.UploadFile;
import com.example.ProductResellProject.domain.users.User;
import com.example.ProductResellProject.domain.users.UsersRepository;
import com.example.ProductResellProject.file.FileStore;
import com.example.ProductResellProject.web.dto.PostForm;
import com.example.ProductResellProject.web.dto.PostsResponseDto;
import com.example.ProductResellProject.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final UsersRepository usersRepository;
    private final FileStore fileStore;

    @Transactional
    public Long save(PostForm form, String userId) throws IOException {
        User user = usersRepository.findByUserId(userId).get();
        Posts post = Posts.builder()
                .content(form.getContent())
                .author(user.getName())
                .title(form.getTitle())
                .build();

        post.addUploadFile(fileStore.storeFile(form.getImageFile()));
        post.addUser(user);
        return postsRepository.save(post).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다. id="+ id));

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