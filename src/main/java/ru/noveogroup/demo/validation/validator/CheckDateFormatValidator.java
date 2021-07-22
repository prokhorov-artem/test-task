package ru.noveogroup.demo.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.noveogroup.demo.service.DateService;
import ru.noveogroup.demo.validation.CheckDateFormat;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckDateFormatValidator implements ConstraintValidator<CheckDateFormat, String> {

    private final DateService dateService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return dateService.convert(value) != null;
    }
}
