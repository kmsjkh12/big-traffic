package com.example.AuthServer.controller;

import com.example.AuthServer.dto.LoginDto;
import com.example.AuthServer.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.*;
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
public class AuthController{
    @Autowired
    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto,  HttpServletResponse response){
        try{
            return ResponseEntity.ok(authService.login(loginDto,response));
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/login_status")
    public ResponseEntity<?> getMyInfoBySecurity(HttpServletRequest request) {

        try{
            return ResponseEntity.ok(authService.getLoginStatus(request));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

}
