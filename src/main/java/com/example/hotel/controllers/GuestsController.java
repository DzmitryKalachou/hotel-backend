package com.example.hotel.controllers;

import com.example.hotel.model.responses.GuestsResponse;
import com.example.hotel.security.AuthInterceptor;
import com.example.hotel.services.GuestService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/guests")
@RequiredArgsConstructor
public class GuestsController implements BaseController {

    private final GuestService guestService;

    @GetMapping
    @ApiOperation(value = "Get guests", response = GuestsResponse.class, notes = "checkInStart and checkInEnd in format yyyy-MM-dd")
    public ResponseEntity<Object> getGuests(@RequestHeader(AuthInterceptor.AUTHORIZATION_HEADER) String token, @RequestParam(value = "offset", defaultValue = "0") Integer offset,
        @RequestParam(value = "limit", defaultValue = "10") Integer limit, @RequestParam(value = "searchText", required = false) String searchText,
        @RequestParam(value = "checkInStart", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInStart,
        @RequestParam(value = "checkInEnd", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInEnd, @RequestParam(value = "checkedOut", required = false) boolean checkedOut) {
        checkThatUserAuthenticated();
        return new ResponseEntity<>(guestService.getGuests(checkInStart, checkInEnd, searchText, checkedOut, offset, limit), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Checkout guest by id")
    public ResponseEntity<String> checkOut(@RequestHeader(AuthInterceptor.AUTHORIZATION_HEADER) String token, @PathVariable("id") Long id) {
        checkThatUserAuthenticated();
        guestService.checkOutGuest(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
