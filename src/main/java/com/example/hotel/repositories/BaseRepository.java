package com.example.hotel.repositories;

import org.jooq.DSLContext;
import org.jooq.UpdatableRecord;
import org.jooq.impl.TableImpl;

public abstract class BaseRepository<R extends UpdatableRecord, T extends TableImpl<R>> {

    protected static final String SORT_ALIAS = "custom_sort_column";

    protected final DSLContext jooq;

    BaseRepository(DSLContext jooq) {this.jooq = jooq;}

    protected DSLContext jooq() {
        return jooq;
    }

    public R newRecord() {
        return jooq().newRecord(table());
    }

    public void store(R record) {
        record.store();
    }

    public void delete(R record) {
        record.delete();
    }

    protected String wrapSearchText(String text) {
        return "%" + text + "%";
    }

    protected abstract T table();


}
