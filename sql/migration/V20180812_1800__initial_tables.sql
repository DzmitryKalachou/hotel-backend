CREATE TABLE application_user (
    id               BIGSERIAL PRIMARY KEY,
    email            VARCHAR(200) NOT NULL UNIQUE,
    password         BYTEA        NOT NULL,
    salt             BYTEA        NOT NULL,
    create_timestamp TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE guest (
    id                  BIGSERIAL PRIMARY KEY,
    create_timestamp    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    first_name          VARCHAR(200) NOT NULL,
    last_name           VARCHAR(200) NOT NULL,
    birthday            DATE         NOT NULL,
    email               VARCHAR(200) NOT NULL,
    passport_identifier VARCHAR(200) NOT NULL,
    checked_out         BOOLEAN      NOT NULL DEFAULT FALSE
);
