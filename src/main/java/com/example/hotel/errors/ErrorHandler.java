package com.example.hotel.errors;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.hotel.errors.exceptions.ApplicationException;
import com.example.hotel.errors.exceptions.NeedTokenException;
import com.example.hotel.model.dto.ValidationErrorFieldDto;
import com.example.hotel.model.responses.ApplicationErrorResponse;
import com.example.hotel.model.responses.ValidationErrorsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@ControllerAdvice
public class ErrorHandler {


    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApplicationErrorResponse handleApplicationException(final Exception e) {
        return handleException(e);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorsResponse processValidationError(final MethodArgumentNotValidException ex) {
        final BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        return processFieldErrors(fieldErrors);
    }

    @ExceptionHandler({NeedTokenException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ApplicationErrorResponse processNeedTokenException(final NeedTokenException ex) {
        LOG.info("Need JWT token");
        return buildErrorResponse(ErrorCode.NEED_TOKEN);
    }

    @ExceptionHandler({TokenExpiredException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ApplicationErrorResponse processExpiredTokenException(final TokenExpiredException ex) {
        LOG.info("JWT token expired");
        return buildErrorResponse(ErrorCode.TOKEN_EXPIRED);
    }


    @ExceptionHandler({SignatureVerificationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ApplicationErrorResponse signatureVerificationException(final SignatureVerificationException ex) {
        LOG.info("Invalid JWT token");
        return buildErrorResponse(ErrorCode.INVALID_TOKEN);
    }

    private ApplicationErrorResponse handleException(final Exception e) {
        final ErrorCode errorCode = getErrorCodeForException(e);
        LOG.warn(e.getMessage(), e);
        return buildErrorResponse(errorCode);
    }

    private ApplicationErrorResponse buildErrorResponse(final ErrorCode errorCode) {
        return new ApplicationErrorResponse(errorCode.getErrorCode(), errorCode.getMessage());
    }

    private ValidationErrorsResponse processFieldErrors(final List<FieldError> fieldErrors) {
        return new ValidationErrorsResponse(fieldErrors.stream()
            .map(fieldError -> new ValidationErrorFieldDto(fieldError.getField(), fieldError.getDefaultMessage()))
            .collect(Collectors.toList()));
    }

    private ErrorCode getErrorCodeForException(final Exception e) {
        if (e instanceof ApplicationException) {
            return ((ApplicationException) e).getErrorCode();
        } else {
            return ErrorCode.INTERNAL_SERVER;
        }
    }
}
