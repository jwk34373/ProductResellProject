package com.example.ProductResellProject.Post.service;

import com.example.ProductResellProject.Post.domain.Post;
import com.example.ProductResellProject.Post.domain.PostsRepository;
import com.example.ProductResellProject.Post.domain.UploadFile;
import com.example.ProductResellProject.domain.users.User;
import com.example.ProductResellProject.domain.users.UsersRepository;
import com.example.ProductResellProject.web.dto.PostSaveRequestForm;
import com.example.ProductResellProject.web.dto.PostsResponseDto;
import com.example.ProductResellProject.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostCrudService {
    private final PostsRepository postsRepository;
    private final UsersRepository usersRepository;
    private final FileStore fileStore;

    @Transactional
    public Long save(PostSaveRequestForm form, String userId) throws IOException {
        User user = usersRepository.findByUserId(userId).get();

        List<UploadFile> uploadFiles = fileStore.storeFiles(form.getImageFiles());
        Post post = Post.builder()
                .content(form.getContent())
                .author(user.getName())
                .title(form.getTitle())
                .uploadFiles(uploadFiles)
                .build();

        return postsRepository.save(post).getId();
    }

//    @Transactional
//    public Long update(Long id, PostsUpdateRequestDto requestDto) {
//        Post posts = postsRepository.findById(id).orElseThrow(() ->
//                new IllegalArgumentException("해당 게시글이 없습니다. id="+ id));
//
//        posts.update(requestDto.getTitle(), requestDto.getContent());
//        return id;
//    }

    public PostsResponseDto findById (Long id){
        Post entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id));

        return new PostsResponseDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        //Post posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.deleteById(id);
    }

    /**
     * 유저가 작성한 post가 맞음 ??
     * return : 맞으면 true, 아니면 false
     */
    @Transactional
    public boolean isMyPost(Long userId, Long postId){

        Post posts = postsRepository.findById(postId).get();

        // posts 객체 저장할때 user 저장 해주게 바꾸면 돌아갈듯 ??
        // 지금은 안돌아감 ㅜ.ㅜ
        if(posts.getUser().getId() == userId){
            return true;
        }
        return false;
    }

    public Page<Post> findAll(Pageable pageable) {
        return postsRepository.findAll(pageable);
    }

    public Page<Post> postsSearchList(String searchKeyword, Pageable pageable) {
        return postsRepository.findByTitleContaining(searchKeyword, pageable);
    }
}