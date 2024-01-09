CREATE TABLE IF NOT EXISTS address
(
    id      BIGSERIAL,
    uuid    UUID                  NOT NULL,
    area    CHARACTER VARYING(20) NOT NULL,
    country CHARACTER VARYING(25) NOT NULL,
    city    CHARACTER VARYING(20) NOT NULL,
    street  CHARACTER VARYING(30) NOT NULL,
    number  INTEGER               NOT NULL,

    check (number > 0),

    UNIQUE (uuid),
    UNIQUE (country, city, street, number),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS house
(
    id          BIGSERIAL,
    uuid        UUID        NOT NULL,
    address_id  BIGSERIAL   NOT NULL,
    create_date TIMESTAMPTZ NOT NULL,

    UNIQUE (uuid),
    PRIMARY KEY (id),

    CONSTRAINT fk_address
        FOREIGN KEY (address_id)
            REFERENCES address (id)
            ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS passport
(
    id              BIGSERIAL,
    uuid            UUID                 NOT NULL,
    passport_series CHARACTER VARYING(2) NOT NULL,
    passport_number CHARACTER VARYING(7) NOT NULL,

    UNIQUE (uuid),
    UNIQUE (passport_series, passport_number),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS person
(
    id          BIGSERIAL,
    uuid        UUID                  NOT NULL,
    name        CHARACTER VARYING(15) NOT NULL,
    surname     CHARACTER VARYING(15) NOT NULL,
    sex         CHARACTER VARYING(6)  NOT NULL,
    passport_id BIGSERIAL             NOT NULL,
    house_id    BIGSERIAL             NOT NULL,
    create_date TIMESTAMPTZ           NOT NULL,
    update_date TIMESTAMPTZ,

    UNIQUE (uuid),
    PRIMARY KEY (id),

    CONSTRAINT fk_passport
        FOREIGN KEY (passport_id)
            REFERENCES passport (id)
            ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS persons_houses_possessing
(
    id        BIGSERIAL,
    person_id BIGSERIAL NOT NULL,
    house_id  BIGSERIAL NOT NULL,

    UNIQUE (person_id, house_id),
    PRIMARY KEY (id),

    CONSTRAINT fk_person
        FOREIGN KEY (person_id)
            REFERENCES person (id)
            ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT fk_house
        FOREIGN KEY (house_id)
            REFERENCES house (id)
            ON DELETE NO ACTION ON UPDATE NO ACTION
);
