package com.example.Gateway.filter;

import com.example.Gateway.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import org.springframework.http.server.reactive.ServerHttpRequest;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@Component
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtUtil jwtUtil;

    @Autowired
    public GlobalFilter(JwtUtil jwtUtil){
        super(Config.class);
        this.jwtUtil=jwtUtil;

    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            String access = jwtUtil.resolveToken((HttpServletRequest) request);

            if(access !=null && jwtUtil.tokenValidation(access)){
                request.mutate().header("Auth", "true").build();
                request.mutate().header("Account-Value", jwtUtil.getUserNickname(access)).build();
                return chain.filter(exchange);
            }
            request.mutate().header("Auth", "false").build();
            return chain.filter(exchange);
        };
    }
    public static class Config{

    }


}
