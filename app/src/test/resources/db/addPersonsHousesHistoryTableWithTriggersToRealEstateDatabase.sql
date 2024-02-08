CREATE DOMAIN person_status AS
    CHARACTER VARYING(6) NOT NULL CHECK (value ~* '^(tenant|owner)$');

CREATE TABLE IF NOT EXISTS persons_houses_history
(
    id        BIGSERIAL,
    uuid      UUID        NOT NULL,
    person_id BIGSERIAL   NOT NULL,
    house_id  BIGSERIAL   NOT NULL,
    type      person_status,
    date      TIMESTAMPTZ NOT NULL,

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

-- Function should call on events:
-- AFTER INSERT AND AFTER UPDATE
-- ON table:
-- persons_houses_possessing
CREATE OR REPLACE FUNCTION log_person_possessing_house()
    RETURNS TRIGGER
AS
'
    DECLARE
        person_type CHARACTER VARYING = ''owner'';
        update_op   CHARACTER VARYING = ''UPDATE'';
        insert_op   CHARACTER VARYING = ''INSERT'';
    BEGIN

        IF TG_OP = update_op AND OLD <> NULL THEN
            UPDATE persons_houses_history
            SET person_id = NEW.persson_id,
                house_id  = NEW.house_id,
                date      = NOW()
            WHERE person_id = OLD.person_id
              AND house_id = OLD.house_id
              AND type = person_type;
        ELSEIF TG_OP = insert_op THEN
            INSERT INTO persons_houses_history(uuid, person_id, house_id, type, date)
            VALUES (gen_random_uuid(),NEW.person_id, NEW.house_id, person_type, NOW());
        END IF;

        RETURN NEW;
    END;
' LANGUAGE plpgsql;

-- Function should call on events:
-- AFTER INSERT AND AFTER UPDATE
-- ON table:
-- person
CREATE OR REPLACE FUNCTION log_person_living_house()
    RETURNS TRIGGER
AS
'
    DECLARE
        person_type CHARACTER VARYING = ''tenant'';
        update_op   CHARACTER VARYING = ''UPDATE'';
        insert_op   CHARACTER VARYING = ''INSERT'';
    BEGIN

        IF TG_OP = update_op THEN
            UPDATE persons_houses_history
            SET person_id = NEW.id,
                house_id  = NEW.house_id,
                date      = NOW()
            WHERE person_id = OLD.id
              AND house_id = OLD.house_id
              AND type = person_type;
        ELSEIF TG_OP = insert_op THEN
            INSERT INTO persons_houses_history(uuid, person_id, house_id, type, date)
            VALUES (gen_random_uuid(), NEW.id, NEW.house_id, person_type, NOW());
        END IF;

        RETURN NEW;
    END;
' LANGUAGE plpgsql;

CREATE TRIGGER log_person_possessing_house
    AFTER INSERT OR UPDATE
    ON persons_houses_possessing
    FOR EACH ROW
EXECUTE PROCEDURE log_person_possessing_house();

CREATE TRIGGER log_person_living_house
    AFTER INSERT OR UPDATE
    ON person
    FOR EACH ROW
EXECUTE PROCEDURE log_person_living_house();
