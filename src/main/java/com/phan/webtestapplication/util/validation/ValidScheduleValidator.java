package com.phan.webtestapplication.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.phan.webtestapplication.util.CronExpression;

public class ValidScheduleValidator implements ConstraintValidator<ValidSchedule, String> {

    @Override
    public void initialize(ValidSchedule constraintAnnotation) {
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        if (object != null && !object.isEmpty()) {
            return CronExpression.isValidExpression(object);
        }
        return true;
    }

}