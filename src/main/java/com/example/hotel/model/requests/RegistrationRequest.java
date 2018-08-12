package com.example.hotel.model.requests;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RegistrationRequest {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;

}
