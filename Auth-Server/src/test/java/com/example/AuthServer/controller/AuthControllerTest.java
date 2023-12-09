package com.example.AuthServer.controller;

import com.example.AuthServer.dto.LoginDto;
import com.example.AuthServer.jwt.TokenProvider;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


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

        Cookie[]  cookies = result.getResponse().getCookies();
        String jwt = null;
        if (cookies != null) {
            // 쿠키 배열을 순회하면서 원하는 쿠키를 찾습니다.
            for (Cookie cookie : cookies) {
                String authorizationValue = cookie.getValue();
                if (authorizationValue.startsWith("Bearer")) {
                    jwt = authorizationValue.substring(6);

                }
            }
        }

        assertEquals(true, tokenProvider.validateToken(jwt));
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