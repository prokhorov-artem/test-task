package ru.noveogroup.demo.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import ru.noveogroup.demo.validation.validator.CheckDateFormatValidator;

@Constraint(validatedBy = CheckDateFormatValidator.class)
@Target({ElementType.TYPE_USE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckDateFormat {

    String message() default "The date is not in the correct format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String pattern();

}

