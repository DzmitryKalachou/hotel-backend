package com.example.hotel.controllers;

import com.example.hotel.model.requests.LoginRequest;
import com.example.hotel.security.AuthInterceptor;
import com.example.hotel.services.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    @ApiOperation(value = "Login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request, final HttpServletResponse response) {
        final String token = userService.login(request);
        response.addHeader(AuthInterceptor.AUTHORIZATION_HEADER, AuthInterceptor.TOKEN_PREFIX + token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
