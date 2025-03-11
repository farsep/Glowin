package com.glowin.validations;

import com.glowin.models.enums.Rol;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.glowin.validation.ValidRol;

public class RolValidator implements ConstraintValidator<ValidRol, String> {

    @Override
    public void initialize(ValidRol constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        try {
            Rol.fromString(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}