package com.reroute.tmsfm.utility;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraints.UUID;

public class NotBlankUUIDValidator implements ConstraintValidator<NotBlankUUID, UUID> {

    @Override
    public boolean isValid(UUID value, ConstraintValidatorContext context) {
        return value != null;
    }

}
