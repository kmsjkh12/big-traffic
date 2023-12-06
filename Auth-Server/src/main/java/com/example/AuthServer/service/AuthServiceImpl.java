package com.example.AuthServer.service;

import com.example.AuthServer.dto.LoginDto;
import com.example.AuthServer.dto.TokenDto;
import com.example.AuthServer.entity.Auth;
import com.example.AuthServer.jwt.JwtFilter;
import com.example.AuthServer.jwt.TokenProvider;
import com.example.AuthServer.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthServiceImpl implements AuthService  {

    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private TokenProvider tokenProvider;

    public AuthServiceImpl(AuthRepository authRepository, JwtFilter jwtFilter,TokenProvider tokenProvider){
        this.authRepository = authRepository;
        this.jwtFilter = jwtFilter;
        this.tokenProvider = tokenProvider;
    }


    //로그인
    @Override
    public ResponseEntity<TokenDto> login(LoginDto loginDto) throws Exception {
        System.out.print(loginDto.getNickname());
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER"); //객체에 권한 표시
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDto.getNickname(), null , Collections.singleton(simpleGrantedAuthority));
        //사용자 인증에 사용되는 클래스 , 인터페이스

        Auth auth = authRepository.findByNicknameAndPassword(loginDto.getNickname(), loginDto.getPassword());
        //아이디 확인
        if(auth == null){
            throw new Exception("없는 닉네임입니다");
        }

        String jwt =tokenProvider.createToken(authentication);
        //토큰 생성


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(jwtFilter.AUTHORIZATION_HEADER,"Bearer" + jwt);  //헤더에 넣기
        //헤더에 추가
        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);  //응답
    }

    @Override
    public ResponseEntity<?> logout(String id, String token) {
        return null;
    }


}
