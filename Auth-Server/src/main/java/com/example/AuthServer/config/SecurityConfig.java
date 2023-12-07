package com.example.AuthServer.config;

import com.example.AuthServer.jwt.JwtAccessDeniedHandler;
import com.example.AuthServer.jwt.JwtAuthenticationEntryPoint;
import com.example.AuthServer.jwt.JwtSecurityConfig;
import com.example.AuthServer.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;
    @Autowired
    private TokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .cors(withDefaults())
                .csrf(((csrf) -> csrf.disable()))
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler))
                .sessionManagement((sessionManagement) ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeRequests()
                .and()
                .authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests.requestMatchers(new AntPathRequestMatcher("/**/login")).permitAll()
                .anyRequest().authenticated()
                )
                .apply(new JwtSecurityConfig(tokenProvider));

        return httpSecurity.build();
    }
}
