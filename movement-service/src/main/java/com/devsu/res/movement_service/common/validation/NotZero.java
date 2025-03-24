package com.devsu.res.movement_service.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = NotZeroValidator.class)
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface NotZero {
    String message() default "El valor no puede ser cero";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}