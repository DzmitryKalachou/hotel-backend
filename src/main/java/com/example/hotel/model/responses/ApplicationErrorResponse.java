package com.example.hotel.model.responses;

import lombok.Data;

@Data
public class ApplicationErrorResponse {

    private final int errorCode;
    private final String message;

}
