package com.crypto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CryptoSymbolValidator.class)
@Documented
public @interface ValidCryptoSymbol {
    String message() default "Invalid cryptocurrency symbol";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
