package com.example.AuthServer.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class JwtCheck {
    private TokenProvider tokenProvider;
    private JwtFilter jwtFilter;

    public JwtCheck(TokenProvider tokenProvider,JwtFilter jwtFilter){
        this.tokenProvider=tokenProvider;
        this.jwtFilter=jwtFilter;
    }
    // Jwt 토큰의 유효성을 검사한 뒤 예외처리를 해주는 메소드
    public boolean JwtCheck (HttpServletRequest request) {
        // 만약에 Access 토큰에 대한 검증이 필요한 경우
            // Request Header에서 Access 토큰을 꺼내 jwt 변수에 저장
        Cookie[] cookies =  request.getCookies();
        String jwt=null;
        if (cookies != null) {
            // 쿠키 배열을 순회하면서 원하는 쿠키를 찾습니다.
            for (Cookie cookie : cookies) {
                String authorizationValue = cookie.getValue();
                if (authorizationValue.startsWith("Bearer")) {
                    jwt = authorizationValue.substring(6);

                }
            }
        }
        System.out.print(jwt);
        // 추출한 Access 토큰에 대한 유효성 검사 실행
        return tokenProvider.validateToken(jwt);
    }

}
