package com.phan.webtestapplication.util.validation;

import java.io.File;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidStorageValidator implements ConstraintValidator<ValidStorage, String> {

    @Override
    public void initialize(ValidStorage constraintAnnotation) {
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        File binary = new File(object);
        if (binary.exists() && binary.canWrite()) {
            return true;
        }

        return false;
    }

}
