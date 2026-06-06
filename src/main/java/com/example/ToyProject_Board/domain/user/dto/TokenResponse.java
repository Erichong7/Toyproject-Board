package com.example.ToyProject_Board.domain.user.dto;

        import lombok.AllArgsConstructor;
        import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
