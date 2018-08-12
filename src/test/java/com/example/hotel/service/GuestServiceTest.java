package com.example.hotel.service;

import com.example.hotel.db.tables.records.GuestRecord;
import com.example.hotel.errors.exceptions.GuestAlreadyCheckedOutException;
import com.example.hotel.errors.exceptions.GuestNotExistsException;
import com.example.hotel.model.requests.CheckInRequest;
import com.example.hotel.model.responses.GuestsResponse;
import com.example.hotel.repositories.GuestRepository;
import com.example.hotel.services.GuestService;
import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GuestServiceTest {

    private static final String EMAIL = "test_email";
    private static final Long ID = 1L;
    private static final String FIRST_NAME = "test_fn";
    private static final String LAST_NAME = "test_ln";
    private static final String PASSPORT_IDENTIFIER = "test_pi";


    private final GuestRepository guestRepository = mock(GuestRepository.class);
    private final GuestService guestService = new GuestService(guestRepository);

    @Test(expected = GuestNotExistsException.class)
    public void checkOutNotExistsGuestTest() {
        when(guestRepository.getGuestById(1L)).thenReturn(null);
        guestService.checkOutGuest(1L);
    }

    @Test(expected = GuestAlreadyCheckedOutException.class)
    public void checkOutAlreadyCheckedOutGuestTest() {
        final GuestRecord guestRecordMock = mock(GuestRecord.class);
        when(guestRecordMock.getCheckedOut()).thenReturn(true);
        when(guestRepository.getGuestById(1L)).thenReturn(guestRecordMock);
        guestService.checkOutGuest(1L);
    }


    @Test
    public void checkOutGuestTest() {
        final GuestRecord guestRecordMock = mock(GuestRecord.class);
        when(guestRecordMock.getCheckedOut()).thenReturn(false);
        when(guestRepository.getGuestById(1L)).thenReturn(guestRecordMock);

        guestService.checkOutGuest(1L);

        verify(guestRecordMock, times(1)).setCheckedOut(true);
        verify(guestRecordMock, times(1)).getCheckedOut();
        verify(guestRepository, times(1)).getGuestById(1L);
        verify(guestRepository, times(1)).store(guestRecordMock);
        verifyNoMoreInteractions(guestRecordMock, guestRepository);
    }


    @Test
    public void getGuestsTest() {

        final LocalDate now = LocalDate.now();
        final LocalDateTime nowTimestamp = LocalDateTime.now();

        final GuestRecord guestRecordMock = mock(GuestRecord.class);

        when(guestRecordMock.getCheckedOut()).thenReturn(false);
        when(guestRecordMock.getFirstName()).thenReturn(FIRST_NAME);
        when(guestRecordMock.getLastName()).thenReturn(LAST_NAME);
        when(guestRecordMock.getBirthday()).thenReturn(Date.valueOf(now));
        when(guestRecordMock.getEmail()).thenReturn(EMAIL);
        when(guestRecordMock.getId()).thenReturn(ID);
        when(guestRecordMock.getCreateTimestamp()).thenReturn(Timestamp.valueOf(nowTimestamp));
        when(guestRecordMock.getPassportIdentifier()).thenReturn(PASSPORT_IDENTIFIER);

        final LocalDate start = LocalDate.now();
        final LocalDate end = LocalDate.now();
        final String searchText = "some search text";

        when(guestRepository.getGuests(start, end, searchText, false, 0, 1)).thenReturn(Collections.singletonList(guestRecordMock));
        when(guestRepository.getGuestsCount(start, end, searchText, false)).thenReturn(1);


        final GuestsResponse guests = guestService.getGuests(start, end, searchText, false, 0, 1);
        assertThat(guests.getCount()).isEqualTo(1);
        assertThat(guests.getGuests()).hasSize(1);
        assertThat(guests.getGuests()).allMatch(e -> e.getBirthday()
            .equals(now) && e.getCheckInTimestamp()
            .equals(nowTimestamp) && e.getEmail()
            .equals(EMAIL) && e.getFirstName()
            .equals(FIRST_NAME) && e.getLastName()
            .equals(LAST_NAME) && e.getId()
            .equals(ID) && e.getPassportIdentifier()
            .equals(PASSPORT_IDENTIFIER));

        verify(guestRecordMock, times(1)).getFirstName();
        verify(guestRecordMock, times(1)).getLastName();
        verify(guestRecordMock, times(1)).getEmail();
        verify(guestRecordMock, times(1)).getId();
        verify(guestRecordMock, times(1)).getPassportIdentifier();
        verify(guestRecordMock, times(1)).getCheckedOut();
        verify(guestRecordMock, times(1)).getBirthday();
        verify(guestRecordMock, times(1)).getCreateTimestamp();

        verify(guestRepository, times(1)).getGuests(start, end, searchText, false, 0, 1);
        verify(guestRepository, times(1)).getGuestsCount(start, end, searchText, false);

        verifyNoMoreInteractions(guestRecordMock, guestRepository);
    }


    @Test
    public void checkInGuestTest() {

        final LocalDate now = LocalDate.now();

        final GuestRecord guestRecordMock = mock(GuestRecord.class);
        when(guestRecordMock.getCheckedOut()).thenReturn(false);
        when(guestRepository.newRecord()).thenReturn(guestRecordMock);
        when(guestRecordMock.setFirstName(FIRST_NAME)).thenReturn(guestRecordMock);
        when(guestRecordMock.setLastName(LAST_NAME)).thenReturn(guestRecordMock);
        when(guestRecordMock.setBirthday(Date.valueOf(now))).thenReturn(guestRecordMock);
        when(guestRecordMock.setEmail(EMAIL)).thenReturn(guestRecordMock);
        when(guestRecordMock.setPassportIdentifier(PASSPORT_IDENTIFIER)).thenReturn(guestRecordMock);

        final CheckInRequest checkInRequest = new CheckInRequest();
        checkInRequest.setEmail(EMAIL)
            .setFirstName(FIRST_NAME)
            .setLastName(LAST_NAME)
            .setPassportIdentifier(PASSPORT_IDENTIFIER)
            .setBirthday(now);

        guestService.checkIn(checkInRequest);

        verify(guestRecordMock, times(1)).setFirstName(FIRST_NAME);
        verify(guestRecordMock, times(1)).setLastName(LAST_NAME);
        verify(guestRecordMock, times(1)).setEmail(EMAIL);
        verify(guestRecordMock, times(1)).setBirthday(Date.valueOf(now));
        verify(guestRecordMock, times(1)).setPassportIdentifier(PASSPORT_IDENTIFIER);

        verify(guestRepository, times(1)).newRecord();
        verify(guestRepository, times(1)).store(guestRecordMock);

        verifyNoMoreInteractions(guestRecordMock, guestRepository);
    }

}
