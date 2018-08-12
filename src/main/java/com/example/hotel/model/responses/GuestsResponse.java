package com.example.hotel.model.responses;

import com.example.hotel.model.dto.GuestDto;
import lombok.Data;

import java.util.List;

@Data
public class GuestsResponse {


    private final List<GuestDto> guests;
    private final int count;

}
