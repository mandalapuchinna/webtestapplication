package com.phan.webtestapplication.util.validation;

import java.io.File;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidBinaryValidator implements ConstraintValidator<ValidBinary, String> {

    @Override
    public void initialize(ValidBinary constraintAnnotation) {
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        File binary = new File(object + "/bin/webtest.bat");
        if (binary.exists()) {
            return true;
        }

        return false;
    }

}