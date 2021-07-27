package ru.noveogroup.demo.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.noveogroup.demo.model.RelationType;
import ru.noveogroup.demo.repository.RelationshipRepository;
import ru.noveogroup.demo.validation.SpouseNotExists;

@RequiredArgsConstructor
public class SpouseExistsByIdValidator implements ConstraintValidator<SpouseNotExists, Long> {

    private final RelationshipRepository relationshipRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        return !relationshipRepository.existsByRelationFromIdAndRelationTypeEquals(id, RelationType.SPOUSE);
    }
}
