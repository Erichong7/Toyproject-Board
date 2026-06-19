package com.example.ToyProject_Board.domain.post.controller;

import com.example.ToyProject_Board.domain.post.dto.request.PostCreateRequest;
import com.example.ToyProject_Board.domain.post.dto.response.PostResponse;
import com.example.ToyProject_Board.domain.post.service.PostService;
import com.example.ToyProject_Board.domain.support.ControllerTestSupport;
import com.example.ToyProject_Board.global.security.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@Import(SecurityConfig.class) // SecurityConfig를 import하여 JwtAuthenticationFilter가 빈으로 등록되도록 함
public class PostControllerTest extends ControllerTestSupport {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    // todo 테스트 코드 작성
    @Test
    @DisplayName("게시글 작성 성공")
    void createSuccess() throws Exception {
        //given
        PostCreateRequest request = new PostCreateRequest("테스트 제목", "테스트 내용");
        PostResponse response = new PostResponse(1L, "테스트 제목", "테스트 내용", "테스터", null, null);
        given(postService.create(any(), any())).willReturn(response);

        // when & then
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .requestAttr("userId", 1L)) // userId를 request attribute로 설정
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("테스트 제목"))
                .andExpect(jsonPath("$.content").value("테스트 내용"))
                .andDo(print());
    }

}
