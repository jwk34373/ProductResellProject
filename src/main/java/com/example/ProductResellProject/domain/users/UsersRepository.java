package com.example.ProductResellProject.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {

    // select * from user where userid = 1?
    Optional<User> findByUserId(String userid);
}
