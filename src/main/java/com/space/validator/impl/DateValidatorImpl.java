package com.space.validator.impl;

import com.space.validator.DateValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateValidatorImpl implements ConstraintValidator<DateValidator, Date> {
    private static final int yearMIN = 2800;
    private static final int yearMAX = 3019;

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return false;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return year >= yearMIN && year <= yearMAX && date.getTime() >= 0;
    }
}