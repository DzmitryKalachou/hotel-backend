package com.example.hotel.errors.exceptions;

import com.example.hotel.errors.ErrorCode;

public class GuestNotExistsException extends ApplicationException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.GUEST_NOT_EXISTS;
    }
}
