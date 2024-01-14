package ru.clevertec.ecl.knyazev.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.clevertec.ecl.knyazev.validation.validator.UUIDListValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that using for validation string UUIDs in list
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UUIDListValidator.class })
public @interface ListUUID {
    String message() default "Invalid UUID list";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
