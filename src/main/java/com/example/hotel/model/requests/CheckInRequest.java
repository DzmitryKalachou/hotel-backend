package com.example.hotel.model.requests;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class CheckInRequest {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private LocalDate birthday;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String passportIdentifier;

}
