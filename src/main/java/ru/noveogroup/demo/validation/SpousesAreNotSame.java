package ru.noveogroup.demo.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import ru.noveogroup.demo.validation.validator.SpousesAreNotSameValidator;

@Constraint(validatedBy = SpousesAreNotSameValidator.class)
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpousesAreNotSame {

    String message() default "People for marriage are same";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
