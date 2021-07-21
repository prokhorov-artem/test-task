package ru.noveogroup.demo.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import ru.noveogroup.demo.repository.PersonRepository;
import ru.noveogroup.demo.validation.PersonExists;

@RequiredArgsConstructor
public class PersonExistsByIdValidator implements ConstraintValidator<PersonExists, Long> {

    private final PersonRepository personRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        return personRepository.existsById(id);
    }
}
