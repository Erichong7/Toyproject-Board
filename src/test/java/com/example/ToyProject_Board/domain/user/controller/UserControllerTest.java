package com.example.ToyProject_Board.domain.user.controller;

import com.example.ToyProject_Board.domain.support.ControllerTestSupport;
import com.example.ToyProject_Board.domain.user.dto.TokenResponse;
import com.example.ToyProject_Board.domain.user.service.UserService;
import com.example.ToyProject_Board.global.security.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
public class UserControllerTest extends ControllerTestSupport {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("회원가입 성공")
    void signupSuccess() throws Exception {
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "test@test.com",
                                    "password": "password123",
                                    "nickname": "테스터"
                                }
                                """))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 실패 - 이메일 형식 오류")
    void signupFail_invalidEmail() throws Exception {
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "not-an-email",
                                    "password": "password123",
                                    "nickname": "테스터"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 실패 - 비밀번호 최소 길이 미달")
    void signupFail_shortPassword() throws Exception {
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "test@test.com",
                                    "password": "1234567",
                                    "nickname": "테스터"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() throws Exception {
        given(userService.login(any())).willReturn(new TokenResponse("access_token", "refresh_token"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "test@test.com",
                                    "password": "password123"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access_token"))
                .andExpect(jsonPath("$.refreshToken").value("refresh_token"))
                .andDo(print());
    }

    @Test
    @DisplayName("토큰 재발급 성공")
    void reissueSuccess() throws Exception {
        given(userService.reissue(any())).willReturn(new TokenResponse("new_access", "new_refresh"));

        mockMvc.perform(post("/api/auth/reissue")
                        .header("Refresh-Token", "valid_refresh_token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("new_access"))
                .andExpect(jsonPath("$.refreshToken").value("new_refresh"))
                .andDo(print());
    }

    @Test
    @DisplayName("로그아웃 성공")
    void logoutSuccess() throws Exception {
        mockMvc.perform(post("/api/auth/logout")
                        .requestAttr("userId", 1L))
                .andExpect(status().isOk())
                .andDo(print());
    }
}