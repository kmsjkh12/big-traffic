package com.example.User.repository;

import com.example.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserid(Long id);
    User findByUsernickname(String nickname);
}