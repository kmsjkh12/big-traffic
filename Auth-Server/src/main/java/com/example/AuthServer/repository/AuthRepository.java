package com.example.AuthServer.repository;

import com.example.AuthServer.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    Auth findByUnicknameAndUpw(String nickname, String password);
    Auth findByUnickname(String nickname);

}
