package com.example.hotel.errors.exceptions;

import com.example.hotel.errors.ErrorCode;

public class NeedTokenException extends ApplicationException {

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NEED_TOKEN;
    }

}
