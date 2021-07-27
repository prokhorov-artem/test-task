package ru.noveogroup.demo.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import ru.noveogroup.demo.validation.validator.SpouseExistsByIdValidator;

@Constraint(validatedBy = SpouseExistsByIdValidator.class)
@Target({ElementType.TYPE_USE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SpouseNotExists {

    String message() default "Person already has spouse";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
