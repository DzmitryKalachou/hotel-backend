package com.example.hotel.services;

import com.example.hotel.db.tables.records.GuestRecord;
import com.example.hotel.errors.exceptions.GuestAlreadyCheckedOutException;
import com.example.hotel.errors.exceptions.GuestNotExistsException;
import com.example.hotel.model.ConvertUtil;
import com.example.hotel.model.requests.CheckInRequest;
import com.example.hotel.model.responses.GuestsResponse;
import com.example.hotel.repositories.GuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class GuestService {

    private final GuestRepository guestRepository;

    public void checkOutGuest(final Long id) {
        final GuestRecord guestById = guestRepository.getGuestById(id);
        if (guestById == null) {
            throw new GuestNotExistsException();
        }
        if (guestById.getCheckedOut()) {
            throw new GuestAlreadyCheckedOutException();
        }
        guestById.setCheckedOut(true);
        guestRepository.store(guestById);
    }

    public GuestsResponse getGuests(final LocalDate checkInStart, final LocalDate checkInEnd, final String searchText, final boolean checkedOut,
        final Integer offset, final Integer limit) {
        return new GuestsResponse(guestRepository.getGuests(checkInStart, checkInEnd, searchText, checkedOut, offset, limit)
            .stream()
            .map(ConvertUtil::convert)
            .collect(Collectors.toList()), guestRepository.getGuestsCount(checkInStart, checkInEnd, searchText, checkedOut));
    }

    public void checkIn(final CheckInRequest request) {
        final GuestRecord guestRecord = guestRepository.newRecord();
        guestRecord.setFirstName(request.getFirstName())
            .setLastName(request.getLastName())
            .setEmail(request.getEmail())
            .setBirthday(Date.valueOf(request.getBirthday()))
            .setPassportIdentifier(request.getPassportIdentifier());
        guestRepository.store(guestRecord);
    }
}
