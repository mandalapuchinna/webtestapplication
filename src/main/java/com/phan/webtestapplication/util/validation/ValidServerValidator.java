package com.phan.webtestapplication.util.validation;

import java.net.URL;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidServerValidator implements ConstraintValidator<ValidServer, String> {

    @Override
    public void initialize(ValidServer constraintAnnotation) {
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        try {
            new URL(object);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}