package com.example.hotel.model.responses;

import com.example.hotel.errors.ErrorCode;
import com.example.hotel.model.dto.ValidationErrorFieldDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ValidationErrorsResponse {

    private final int errorCode = ErrorCode.NOT_VALID_REQUEST_DATA.getErrorCode();
    private final List<ValidationErrorFieldDto> errors;

}
