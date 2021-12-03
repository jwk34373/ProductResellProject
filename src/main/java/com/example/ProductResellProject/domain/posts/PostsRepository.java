package com.example.ProductResellProject.domain.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    //@Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    //List<Posts> findAllDesc();

    Page<Posts> findAll(Pageable pageable);

    Page<Posts> findByTitleContaining(String searchKeyword, Pageable pageable);

}
