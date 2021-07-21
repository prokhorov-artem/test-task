package ru.noveogroup.demo.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import ru.noveogroup.demo.model.RelationType;
import ru.noveogroup.demo.repository.RelationshipRepository;
import ru.noveogroup.demo.validation.SpouseExists;

@RequiredArgsConstructor
public class SpouseExistsByIdValidator implements ConstraintValidator<SpouseExists, Long> {

    private final RelationshipRepository relationshipRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        return !relationshipRepository.existsByRelationFromIdAndRelationTypeEquals(id, RelationType.SPOUSE);
    }
}
