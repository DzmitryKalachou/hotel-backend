package com.example.hotel.errors.exceptions;

import com.example.hotel.errors.ErrorCode;

public class InvalidPasswordException extends ApplicationException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_PASSWORD;
    }
}
