package com.example.AuthServer.jwt;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.*;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;
    //실제 필터링 로직
    //토큰의 인증정보 저장
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
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
        String requestURI = httpServletRequest.getRequestURI();
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {
            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }
        chain.doFilter(request, response);
    }

    String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
       if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}