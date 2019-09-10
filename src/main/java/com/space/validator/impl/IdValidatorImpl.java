package com.space.validator.impl;

import com.space.validator.IdValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdValidatorImpl implements ConstraintValidator<IdValidator, Long> {
    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {

        return false;
    }
}
//https://stackoverflow.com/questions/19419234/how-to-validate-spring-mvc-pathvariable-values