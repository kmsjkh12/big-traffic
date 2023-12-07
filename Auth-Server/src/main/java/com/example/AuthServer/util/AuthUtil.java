package com.example.AuthServer.util;

import com.example.AuthServer.entity.Auth;
import com.example.AuthServer.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    private final AuthRepository authRepository;

    public AuthUtil(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public Auth getUtils(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Authentication 객체는 사용자의 인증 및 권한 정보
        String nickname = authentication.getName();
        return authRepository.findByUnickname(nickname);
    }

    //유저 반환해주는 util!
}
