package com.example.ProductResellProject.Post.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostsRepository extends JpaRepository<Post, Long> {

    //@Query("SELECT p FROM Post p ORDER BY p.id DESC")
    //List<Post> findAllDesc();

    Page<Post> findAll(Pageable pageable);

    Page<Post> findByTitleContaining(String searchKeyword, Pageable pageable);

//    Optional<Post> findOptionalByIdAndUser_CodeId(Long id, Long userId);
}
