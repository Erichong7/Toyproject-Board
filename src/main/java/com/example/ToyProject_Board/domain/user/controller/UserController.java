package com.example.ToyProject_Board.domain.user.controller;

import com.example.ToyProject_Board.domain.user.dto.LoginRequest;
import com.example.ToyProject_Board.domain.user.dto.SignupRequest;
import com.example.ToyProject_Board.domain.user.dto.TokenResponse;
import com.example.ToyProject_Board.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest request) {
        userService.signup(request);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@RequestHeader("Refresh-Token") String refreshToken) {
        return ResponseEntity.ok(userService.reissue(refreshToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestAttribute("userId") Long userId) {
        userService.logout(userId);
        return ResponseEntity.ok("로그아웃 성공");
    }
}
