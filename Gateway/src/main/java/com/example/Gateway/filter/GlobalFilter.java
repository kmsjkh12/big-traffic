package com.example.Gateway.filter;

import com.example.Gateway.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

import org.springframework.http.HttpStatus;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@Component
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
    private static final Logger logger = LoggerFactory.getLogger(GlobalFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;

    @Autowired
    public GlobalFilter(TokenProvider tokenProvider){
        super(Config.class);
        this.tokenProvider=tokenProvider;

    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String jwt = resolveToken(request);

            if(!StringUtils.hasText(jwt) ){
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            if(!tokenProvider.validateToken(jwt)){
                return onError(exchange, "no valid token", HttpStatus.UNAUTHORIZED);
            }
            //여기까지는 도달함
            return chain.filter(exchange);
        };
    }
    public static class Config{

    }
    // mono, flux -> webflux : 단위 값이면 mono로 반환
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {

        ServerHttpResponse response = exchange.getResponse();
        return response.setComplete();
    }
    private String resolveToken(ServerHttpRequest request) {
        if(request.getHeaders().size() ==0){
            logger.error("error error error");
            return null;
        }
        String bearerToken = request.getHeaders().getFirst(AUTHORIZATION_HEADER);
        System.out.println(bearerToken);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }


}
