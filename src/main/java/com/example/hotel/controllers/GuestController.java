package com.example.hotel.controllers;

import com.example.hotel.model.requests.CheckInRequest;
import com.example.hotel.services.GuestService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/guest")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @PostMapping("/check-in")
    @ApiOperation(value = "Check In", notes = "birthday in format yyyy-MM-dd")
    public ResponseEntity<String> checkIn(@RequestBody @Valid CheckInRequest request) {
        guestService.checkIn(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
