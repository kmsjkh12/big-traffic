package com.example.AuthServer.repository;

import com.example.AuthServer.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    Auth findByNicknameAndPassword(String nickname, String password);
    boolean existsByNickname(String nickname);

    Optional<Auth> findByNickname(String nickname);

}
