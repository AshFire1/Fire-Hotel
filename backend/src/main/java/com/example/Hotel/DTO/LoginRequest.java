package com.example.Hotel.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull(message = "Email is required")
    private String email;
    @NotNull(message = "Password is Required")
    private String password;

}
