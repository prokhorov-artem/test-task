package ru.noveogroup.demo.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import ru.noveogroup.demo.model.dto.MarriageDto;
import ru.noveogroup.demo.validation.SpousesAreNotSame;

public class SpousesAreNotSameValidator implements ConstraintValidator<SpousesAreNotSame, MarriageDto> {

    @Override
    public boolean isValid(MarriageDto dto, ConstraintValidatorContext constraintValidatorContext) {
        return !dto.getFirstPerson().equals(dto.getSecondPerson());
    }
}
