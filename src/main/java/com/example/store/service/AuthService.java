package com.example.store.service;

import com.example.store.dto.request.LoginRequestDto;
import com.example.store.dto.request.RegisterRequestDto;
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

    public void register(RegisterRequestDto registerRequestDto){
        if (appUserRepo.existsAppUserByUserName(registerRequestDto.getUserName())){
            throw new RuntimeException("Username already exists");
        }else {
            AppUser appUser = new AppUser();
            appUser.setUserName(registerRequestDto.getUserName());
            appUser.setPassword(registerRequestDto.getPassword());
            appUser.setRole(Role.CUSTOMER);
            appUserRepo.save(appUser);
        }
    }

    public boolean login(LoginRequestDto loginRequestDto){
        String userName = loginRequestDto.getUserName();
        String password = loginRequestDto.getPassword();
        AppUser appUser = appUserRepo.findByUserName(userName);
        if (appUser==null){
            return false;
        }
        return (password.equals(appUser.getPassword()));
    }

}
