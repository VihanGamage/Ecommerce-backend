package com.example.store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequestDto {
    private String userName;
    private String password;

}
