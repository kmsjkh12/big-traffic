package com.example.AuthServer.service;

import com.example.AuthServer.dto.LoginDto;
import com.example.AuthServer.dto.TokenDto;
import com.example.AuthServer.entity.Auth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<TokenDto> login (LoginDto loginDto) throws Exception;

    ResponseEntity<?> logout(String id, String token);
}
