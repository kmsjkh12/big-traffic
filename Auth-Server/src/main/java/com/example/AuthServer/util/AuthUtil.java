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

    public String getUtils(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName() != null) {
            return authentication.getName();
        }
        else {
            // 사용자 이름이 없는 경우 또는 authentication이 null인 경우 예외 처리
            throw new IllegalStateException("Unable to retrieve current username");
        }
    }

    //유저 반환해주는 util!
}
