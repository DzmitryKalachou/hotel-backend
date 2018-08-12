package com.example.hotel.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class GuestDto {

    private Long id;
    private LocalDateTime checkInTimestamp;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String email;
    private String passportIdentifier;
    private boolean checkedOut;

}
