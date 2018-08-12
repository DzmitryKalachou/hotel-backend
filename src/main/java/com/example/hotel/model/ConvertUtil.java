package com.example.hotel.model;

import com.example.hotel.db.tables.records.GuestRecord;
import com.example.hotel.model.dto.GuestDto;

public class ConvertUtil {


    public static GuestDto convert(final GuestRecord guestRecord) {
        return new GuestDto().setEmail(guestRecord.getEmail())
            .setFirstName(guestRecord.getFirstName())
            .setLastName(guestRecord.getLastName())
            .setId(guestRecord.getId())
            .setPassportIdentifier(guestRecord.getPassportIdentifier())
            .setCheckInTimestamp(guestRecord.getCreateTimestamp()
                .toLocalDateTime())
            .setBirthday(guestRecord.getBirthday()
                .toLocalDate())
            .setCheckedOut(guestRecord.getCheckedOut());
    }


}
