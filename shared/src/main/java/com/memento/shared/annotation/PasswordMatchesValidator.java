package com.memento.shared.annotation;

import lombok.SneakyThrows;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    @SneakyThrows
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        final String password = String.valueOf(object.getClass().getMethod("getPassword").invoke(object));
        final String confirmPassword = String.valueOf(object.getClass().getMethod("getConfirmPassword").invoke(object));
        return password.equals(confirmPassword);
    }
}
