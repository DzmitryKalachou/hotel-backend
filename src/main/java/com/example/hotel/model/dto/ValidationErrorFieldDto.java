package com.example.hotel.model.dto;

import lombok.Data;

@Data
public class ValidationErrorFieldDto {

    private final String field;
    private final String message;

}
