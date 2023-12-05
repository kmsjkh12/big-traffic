package com.example.User.service;

import com.example.User.dto.LoginDto;
import com.example.User.dto.TokenDto;
import com.example.User.entity.User;
import com.example.User.jwt.JwtFilter;
import com.example.User.jwt.TokenProvider;
import com.example.User.repository.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {


    private UserRepository userRepository;
    private TokenProvider tokenProvider;

    public UserService(UserRepository userRepository, TokenProvider tokenProvider){
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;

    }
    public ResponseEntity<TokenDto> login (LoginDto loginDto){
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER"); //권한
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDto.getNickname(), null , Collections.singleton(simpleGrantedAuthority));
        User user = userRepository.findByUsernickname(loginDto.getNickname());
        if(user == null){
            throw new com.example.numble_insta.exception.ExistUserException("없는 닉네임입니다");
        }
        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER,"Bearer" + jwt);  //헤더에 넣기

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);  //응답
    }
}
