package com.example.hotel.errors.exceptions;

import com.example.hotel.errors.ErrorCode;

public class UserNotExistsException extends ApplicationException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.USER_NOT_EXISTS;
    }
}
