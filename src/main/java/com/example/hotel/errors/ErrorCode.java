package com.example.hotel.errors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    INTERNAL_SERVER(1, "Server error. Please, try later"),
    NOT_VALID_REQUEST_DATA(2, "Not valid data"),
    NEED_TOKEN(3, "User is not authorized"),
    INVALID_TOKEN(4, "Invalid authorization. Try to relogin"),
    GUEST_NOT_EXISTS(5, "Guest not exists"),
    INVALID_PASSWORD(6, "Invalid password"),
    USER_NOT_EXISTS(7, "User not exists"),
    TOKEN_EXPIRED(8, "JWT token expired"),
    GUEST_ALREADY_CHECKED_OUT(9, "User already checked out");

    private final int errorCode;
    private final String message;

}
