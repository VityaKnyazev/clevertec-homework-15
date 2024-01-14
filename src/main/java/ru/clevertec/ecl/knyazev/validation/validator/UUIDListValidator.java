package ru.clevertec.ecl.knyazev.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.clevertec.ecl.knyazev.validation.constraint.ListUUID;

import java.util.List;
import java.util.UUID;

/**
 * Represents validator for validating list string with UUID
 */
public class UUIDListValidator implements ConstraintValidator<ListUUID, List<String>> {
    /**
     *
     * Validate list string with uuid
     *
     * @param values object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return true - if string UUIDs in list is valid, otherwise - false
     */
    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (values == null || values.size() == 0) {
            return true;
        }

        try {
            for (String value : values) {
                UUID.fromString(value);
            }
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
