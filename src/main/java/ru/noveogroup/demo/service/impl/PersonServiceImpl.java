package ru.noveogroup.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.noveogroup.demo.model.RelationType;
import ru.noveogroup.demo.model.dto.MarriageDto;
import ru.noveogroup.demo.model.dto.PersonDto;
import ru.noveogroup.demo.model.dto.RelatedPersonDto;
import ru.noveogroup.demo.model.entity.Person;
import ru.noveogroup.demo.model.entity.Relationship;
import ru.noveogroup.demo.repository.PersonRepository;
import ru.noveogroup.demo.repository.RelationshipRepository;
import ru.noveogroup.demo.service.PersonService;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final RelationshipRepository relationshipRepository;
    private final ConversionService conversionService;

    @Transactional(readOnly = true)
    public PersonDto findById(Long id) {
        log.info("Trying to find person with id: {}", id);
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (!optionalPerson.isPresent()) {
            return null;
        }
        Person person = optionalPerson.get();
        PersonDto personDto = conversionService.convert(person, PersonDto.class);
        if (personDto != null) {
            log.info("Trying to find all relations for person with id: {}", id);
            relationshipRepository.findAllByRelationFromId(person.getId()).forEach(relationship -> {
                if (relationship.getRelationType() == RelationType.SPOUSE) {
                    personDto.setSpouse(conversionService.convert(relationship.getRelationTo(),
                        RelatedPersonDto.class));
                } else {
                    personDto.getParents()
                        .add(conversionService.convert(relationship.getRelationTo(), RelatedPersonDto.class));
                }
            });
        }
        return personDto;
    }

    @Transactional
    public Long addPerson(PersonDto personDto) {
        log.info("Trying to add person with data: {}", personDto);
        Person person = conversionService.convert(personDto, Person.class);
        if (person == null) {
            return null;
        }
        personRepository.save(person);
        if (personDto.getParents() == null) {
            return person.getId();
        }
        log.info("Trying to find person's parents and save info to relationship table");
        List<Relationship> relationships = personDto.getParents().stream()
            .map(relatedPersonDto -> conversionService.convert(relatedPersonDto, Person.class))
            .map(parent -> Relationship.builder()
                .relationType(RelationType.PARENT)
                .relationFrom(person)
                .relationTo(parent)
                .build())
            .collect(Collectors.toList());
        relationshipRepository.saveAll(relationships);
        return person.getId();
    }

    @Transactional
    public synchronized boolean addMarriage(MarriageDto marriageDto) {
        log.info("Trying to find spouses and save info to relationship table");
        if (relationshipRepository
            .existsByRelationFromIdAndRelationTypeEquals(marriageDto.getFirstPerson(), RelationType.SPOUSE) ||
            relationshipRepository
                .existsByRelationFromIdAndRelationTypeEquals(marriageDto.getSecondPerson(), RelationType.SPOUSE)) {
            return false;
        }
        Person firstPerson = Person.builder()
            .id(marriageDto.getFirstPerson())
            .build();
        Person secondPerson = Person.builder()
            .id(marriageDto.getSecondPerson())
            .build();
        List<Relationship> relationships = new ArrayList<>();
        relationships.add(Relationship.builder()
            .relationType(RelationType.SPOUSE)
            .relationFrom(firstPerson)
            .relationTo(secondPerson)
            .build());
        relationships.add(Relationship.builder()
            .relationType(RelationType.SPOUSE)
            .relationFrom(secondPerson)
            .relationTo(firstPerson)
            .build());
        relationshipRepository.saveAll(relationships);
        return true;
    }
}
