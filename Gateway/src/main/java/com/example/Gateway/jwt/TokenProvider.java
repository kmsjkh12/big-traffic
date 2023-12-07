package com.example.Gateway.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean { //빈 초기화
    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private final String secret;
    private Key key;



    public TokenProvider(
            @Value("${jwt.secret}") String secret
    ) {
        this.secret = secret;
    }

    //빈이 생성되고 주입을 받은 후에 시크릿 값을 base64 decode해서 key 할당
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }
        catch (SecurityException | MalformedJwtException e){
            logger.info("잘못된 JWT 서명입니다.");
        }
        catch (ExpiredJwtException e){
            logger.info("만료된 JWT 서명입니다.");
        }
        catch (UnsupportedJwtException e){
            logger.info("지원되지 않는 JWT 서명입니다.");
        }
        catch (IllegalArgumentException e){
            logger.info(" JWT 토큰이 잘못되었습니다.");
        }

        return false;
    }
}