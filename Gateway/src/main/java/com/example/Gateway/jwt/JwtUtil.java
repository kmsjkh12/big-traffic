package com.example.Gateway.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;

import static com.example.Gateway.filter.GlobalFilter.AUTHORIZATION_HEADER;

@Component
public class JwtUtil {
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long ACCESS_TIME = 60 * 60 * 1000L; // ACCESS_TIME 1시간
    private static final long REFRESH_TIME = 7 * 24 * 60 * 60 * 1000L;  // REFRESH_TIME 7일
    private static final String ACCESS_TOKEN = "Access_Token";
    private static final String REFRESH_TOKEN = "Refresh_Token";
    private static final String AUTHORITIES_KEY = "auth";

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private Key key;
    @Value("${jwt.secret}")
    private String secretKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }


    //토큰 검증
    public Boolean tokenValidation(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    //토큰에서 id 추출
    public String getUserNickname(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getId();
    }

    //error
    public Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus) {
        ServerHttpResponse response = (ServerHttpResponse) exchange.getResponse();
        response.setStatusCode(httpStatus);
        logger.error(error);

        return Mono.just(response).then();

    }



}
