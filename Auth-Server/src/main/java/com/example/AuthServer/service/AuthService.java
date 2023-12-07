package com.example.AuthServer.service;

import com.example.AuthServer.dto.AuthDto;
import com.example.AuthServer.dto.LoginDto;
import com.example.AuthServer.dto.TokenDto;
import com.example.AuthServer.entity.Auth;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.*;

public interface AuthService {

    ResponseEntity<AuthDto> login (LoginDto loginDto, HttpServletResponse response) throws Exception;

    ResponseEntity<?> logout(String id, String token);

    ResponseEntity<?>   getLoginStatus(HttpServletRequest request) throws Exception;
}
