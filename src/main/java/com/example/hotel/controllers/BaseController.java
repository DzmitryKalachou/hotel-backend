package com.example.hotel.controllers;

import com.example.hotel.errors.exceptions.NeedTokenException;
import com.example.hotel.security.SecurityContextHolder;

public interface BaseController {

    default void checkThatUserAuthenticated() {
        if (SecurityContextHolder.getInstance()
            .getUserId() == null) {
            throw new NeedTokenException();
        }
    }


}
