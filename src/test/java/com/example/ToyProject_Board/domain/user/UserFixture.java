package com.example.ToyProject_Board.domain.user;

import java.lang.reflect.Field;

public class UserFixture {
    public static User createWithId(Long id) throws Exception {
        User user = User.builder()
                .email("test@test.com")
                .password("test1234")
                .nickname("테스터")
                .build();

        Field idField = User.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(user, id);

        return user;
    }
}
