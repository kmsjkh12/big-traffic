package com.example.AuthServer.controller;

import com.example.AuthServer.dto.LoginDto;
import com.example.AuthServer.entity.Auth;
import com.example.AuthServer.jwt.TokenProvider;
import com.example.AuthServer.repository.AuthRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TokenProvider tokenProvider;

    @Test
    public void 토큰_일치_테스트() throws Exception {
        LoginDto loginDto1 = new LoginDto("abcd", "1234");

        MvcResult result =mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                               loginDto1
                        )))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String resultLine = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();

        // JSON 파싱
        JsonNode rootNode = objectMapper.readTree(resultLine);

        // "body" 객체 안의 "token" 값을 추출
        JsonNode bodyNode = rootNode.path("body");
        String tokenValue = bodyNode.path("token").asText();

        //then
        assertEquals(true, tokenProvider.validateToken(tokenValue));
    }
    @Test
    public void 토큰_실패_테스트() throws Exception {
        LoginDto loginDto1 = new LoginDto("kmsjkh123", "1234");

        MvcResult result =mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                loginDto1
                        )))
                .andExpect(status().is(400))
                .andDo(print())
                .andReturn();

        String resultLine = result.getResponse().getContentAsString();

        //then
        assertEquals("없는 닉네임입니다",resultLine);
    }
}