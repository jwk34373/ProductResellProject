package com.example.ProductResellProject.domain.posts;

import com.example.ProductResellProject.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

//    Optional<Posts> findOptionalByIdAndUser_CodeId(Long id, Long userId);
}
