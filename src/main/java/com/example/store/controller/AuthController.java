package com.example.store.controller;

import com.example.store.dto.request.LoginRequestDto;
import com.example.store.dto.request.RegisterRequestDto;
import com.example.store.dto.response.JwtResponseDto;
import com.example.store.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequestDto registerRequestDto){
        authService.register(registerRequestDto);
    }

    //login
    @PostMapping("/login")
    public JwtResponseDto login(@RequestBody LoginRequestDto loginRequestDto){
        return authService.login(loginRequestDto);
    }

}
