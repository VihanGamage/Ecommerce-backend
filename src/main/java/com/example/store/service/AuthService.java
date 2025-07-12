package com.example.store.service;

import com.example.store.dto.request.LoginRequestDto;
import com.example.store.dto.request.RegisterRequestDto;
import com.example.store.dto.response.JwtResponseDto;
import com.example.store.entity.AppUser;
import com.example.store.entity.Role;
import com.example.store.repository.AppUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AppUserRepo appUserRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequestDto registerRequestDto){
        if (appUserRepo.existsAppUserByUserName(registerRequestDto.getUserName())){
            throw new RuntimeException("Username already exists");
        }else {
            AppUser appUser = new AppUser();
            appUser.setUserName(registerRequestDto.getUserName());
            appUser.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
            appUser.setRole(Role.CUSTOMER);
            appUserRepo.save(appUser);
        }
    }

    public JwtResponseDto login(LoginRequestDto loginRequestDto){
        String userName = loginRequestDto.getUserName();
        String password = loginRequestDto.getPassword();
        AppUser appUser = appUserRepo.findByUserName(userName);

        if (appUser==null || !passwordEncoder.matches(password, appUser.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }else {
            String jwt = jwtService.generateToken(appUser.getUserName());
            return new JwtResponseDto(jwt);
        }

    }

}
