package ru.noveogroup.demo.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import ru.noveogroup.demo.validation.validator.PersonExistsByIdValidator;

@Constraint(validatedBy = PersonExistsByIdValidator.class)
@Target({ElementType.TYPE_USE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PersonExists {

    String message() default "Person does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
