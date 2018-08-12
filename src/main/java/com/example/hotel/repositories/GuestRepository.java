package com.example.hotel.repositories;

import com.example.hotel.db.tables.Guest;
import com.example.hotel.db.tables.records.GuestRecord;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.example.hotel.db.tables.Guest.GUEST;

@Repository
public class GuestRepository extends BaseRepository<GuestRecord, Guest> {

    GuestRepository(final DSLContext jooq) {
        super(jooq);
    }

    @Override
    protected Guest table() {
        return GUEST;
    }

    public GuestRecord getGuestById(final Long id) {
        return jooq.selectFrom(GUEST)
            .where(GUEST.ID.eq(id))
            .fetchOne();
    }

    public List<GuestRecord> getGuests(final LocalDate checkInStart, final LocalDate checkInEnd, final String searchText, final boolean checkedOut,
        final Integer offset, final Integer limit) {
        final Condition condition = getGuestsCondition(checkInStart, checkInEnd, searchText, checkedOut);
        return jooq.selectFrom(GUEST)
            .where(condition)
            .offset(offset)
            .limit(limit)
            .fetch();
    }

    public int getGuestsCount(final LocalDate checkInStart, final LocalDate checkInEnd, final String searchText, final boolean checkedOut) {
        return jooq.selectCount()
            .from(GUEST)
            .where(getGuestsCondition(checkInStart, checkInEnd, searchText, checkedOut))
            .fetchOneInto(Integer.class);
    }

    private Condition getGuestsCondition(final LocalDate checkInStart, final LocalDate checkInEnd, final String searchText, final boolean checkedOut) {
        Condition condition = DSL.trueCondition();
        if (checkInStart != null) {
            condition = condition.and(GUEST.CREATE_TIMESTAMP.greaterOrEqual(Timestamp.valueOf(checkInStart.atStartOfDay())));
        }
        if (checkInEnd != null) {
            condition = condition.and(GUEST.CREATE_TIMESTAMP.lessOrEqual(Timestamp.valueOf(checkInEnd.atTime(LocalTime.MAX))));
        }
        if (StringUtils.isNotBlank(searchText)) {
            final String wrappedSearchText = wrapSearchText(searchText);
            final Condition searchTextCondition = GUEST.FIRST_NAME.likeIgnoreCase(wrappedSearchText)
                .or(GUEST.LAST_NAME.likeIgnoreCase(wrappedSearchText))
                .or(GUEST.PASSPORT_IDENTIFIER.likeIgnoreCase(wrappedSearchText))
                .or(GUEST.EMAIL.likeIgnoreCase(wrappedSearchText));
            condition = condition.and(searchTextCondition);
        }
        condition = condition.and(GUEST.CHECKED_OUT.eq(checkedOut));
        return condition;
    }


}
