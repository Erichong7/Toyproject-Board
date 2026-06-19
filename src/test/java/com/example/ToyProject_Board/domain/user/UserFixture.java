package com.example.ToyProject_Board.domain.user;

import org.springframework.test.util.ReflectionTestUtils;

public class UserFixture {

    public static User create() {
        return User.builder()
                .email("test@test.com")
                .password("encoded_password")
                .nickname("테스터")
                .build();
    }

    public static User createWithId(Long id) {
        User user = create();
        ReflectionTestUtils.setField(user, "id", id);
        return user;
    }
}