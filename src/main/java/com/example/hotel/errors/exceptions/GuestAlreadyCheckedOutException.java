package com.example.hotel.errors.exceptions;

import com.example.hotel.errors.ErrorCode;

public class GuestAlreadyCheckedOutException extends ApplicationException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.GUEST_ALREADY_CHECKED_OUT;
    }
}
