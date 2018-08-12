package com.example.hotel.repositories;

import com.example.hotel.db.tables.ApplicationUser;
import com.example.hotel.db.tables.records.ApplicationUserRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static com.example.hotel.db.tables.ApplicationUser.APPLICATION_USER;

@Repository
public class UserRepository extends BaseRepository<ApplicationUserRecord, ApplicationUser> {

    UserRepository(final DSLContext jooq) {
        super(jooq);
    }

    @Override
    protected ApplicationUser table() {
        return APPLICATION_USER;
    }

    public ApplicationUserRecord getUserByEmail(final String email) {
        return jooq.selectFrom(APPLICATION_USER)
            .where(APPLICATION_USER.EMAIL.equalIgnoreCase(email))
            .fetchOne();
    }

    public boolean isUserExistsById(final Long userId) {
        return jooq.fetchExists(APPLICATION_USER, APPLICATION_USER.ID.eq(userId));
    }

    public boolean isEmailExists(final String email) {
        return jooq.fetchExists(APPLICATION_USER, APPLICATION_USER.EMAIL.equalIgnoreCase(email));
    }
}
