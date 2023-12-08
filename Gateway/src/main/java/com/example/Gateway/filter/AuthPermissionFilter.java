package com.example.Gateway.filter;

import com.example.Gateway.jwt.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;

import javax.servlet.http.HttpServletRequest;

public class AuthPermissionFilter extends AbstractGatewayFilterFactory<AuthPermissionFilter.Config> {
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthPermissionFilter(JwtUtil jwtUtil) {
        super(AuthPermissionFilter.Config.class);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            String access = jwtUtil.resolveToken((HttpServletRequest) request);

            if(access !=null ){
                return jwtUtil.onError(exchange, "No Access token", HttpStatus.UNAUTHORIZED);
            }
            if (!jwtUtil.tokenValidation(access)) {
                return jwtUtil.onError(exchange, "AccessToken is not Valid", HttpStatus.UNAUTHORIZED);
            }
            /*정상 토큰이 존재하는 경우*/
            request.mutate().header("Auth", "true").build();
            request.mutate().header("Account-Value", jwtUtil.getUserNickname(access)).build();
            return chain.filter(exchange);
        };
    }

    public static class Config {
        //config
    }
}
