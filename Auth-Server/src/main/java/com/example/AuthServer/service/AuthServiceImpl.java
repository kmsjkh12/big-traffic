package com.example.AuthServer.service;

import com.example.AuthServer.dto.AuthDto;
import com.example.AuthServer.dto.LoginDto;
import com.example.AuthServer.entity.Auth;
import com.example.AuthServer.jwt.JwtCheck;
import com.example.AuthServer.jwt.JwtFilter;
import com.example.AuthServer.jwt.TokenProvider;
import com.example.AuthServer.repository.AuthRepository;
import com.example.AuthServer.util.AuthUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private JwtCheck jwtCheck;

    public AuthServiceImpl(AuthRepository authRepository, AuthUtil authUtil, TokenProvider tokenProvider, JwtCheck jwtCheck) {
        this.authRepository = authRepository;
        this.authUtil = authUtil;
        this.tokenProvider = tokenProvider;
        this.jwtCheck = jwtCheck;
    }


    //로그인
    @Override
    public ResponseEntity<AuthDto> login(LoginDto loginDto, HttpServletResponse response) throws Exception {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER"); //객체에 권한 표시
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDto.getUid(), null, Collections.singleton(simpleGrantedAuthority));
        //사용자 인증에 사용되는 클래스 , 인터페이스

        Auth auth = authRepository.findByUnicknameAndUpw(loginDto.getUid(), loginDto.getUpw());
        //아이디 확인
        if (auth == null) {
            throw new Exception("없는 닉네임입니다");
        }

        String jwt = tokenProvider.createToken(authentication);
        //토큰 생성
        Cookie cookie = new Cookie(JwtFilter.AUTHORIZATION_HEADER, "Bearer" + jwt);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setPath("/");
        response.addCookie(cookie);

        return new ResponseEntity<>(new AuthDto(auth), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> logout(String id, String token) {
        return null;
    }

    @Override
    public ResponseEntity<AuthDto> getLoginStatus(HttpServletRequest request) throws Exception {
        Auth auth = authUtil.getUtils();
        if (!jwtCheck.JwtCheck(request)) {
            throw new Exception("일치하지않는 계정입니다.");
        }
        AuthDto authDto = AuthDto.builder().uid(auth.getUid()).upw(auth.getUpw()).unickname(auth.getUnickname())
                .uaddr(auth.getUaddr()).uemail(auth.getUemail()).ubirth(auth.getUbirth()).ujoindate(auth.getUjoindate())
                .build();

        return new ResponseEntity<>(authDto, HttpStatus.OK);
    }
}
