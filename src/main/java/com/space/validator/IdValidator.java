package com.space.validator;

import com.space.validator.impl.DateValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Constraint(validatedBy = DateValidatorImpl.class)
@Retention(RUNTIME)
@Documented
public @interface IdValidator {
    String message() default "ID is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
//https://stackoverflow.com/questions/19419234/how-to-validate-spring-mvc-pathvariable-values