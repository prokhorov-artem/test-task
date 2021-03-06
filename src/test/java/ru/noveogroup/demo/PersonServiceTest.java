package ru.noveogroup.demo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.noveogroup.demo.model.RelationType;
import ru.noveogroup.demo.model.dto.MarriageDto;
import ru.noveogroup.demo.model.dto.PersonDto;
import ru.noveogroup.demo.model.dto.RelatedPersonDto;
import ru.noveogroup.demo.model.entity.Person;
import ru.noveogroup.demo.model.entity.Relationship;
import ru.noveogroup.demo.repository.PersonRepository;
import ru.noveogroup.demo.repository.RelationshipRepository;
import ru.noveogroup.demo.service.DateService;
import ru.noveogroup.demo.service.PersonService;

@SpringBootTest(classes = {TestTaskApplication.class, H2JpaConfig.class}, webEnvironment =
    SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private DateService dateService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    private Long personId;
    private Long firstParentId;
    private Long secondParentId;

    private final List<Relationship> relationships = new ArrayList<>();

    @BeforeEach
    void init() {
        Person person = Person.builder()
            .name("person")
            .birthPlace("test")
            .build();
        Person firstParent = Person.builder()
            .name("firstParent")
            .birthPlace("test")
            .build();
        Person secondParent = Person.builder()
            .name("secondParent")
            .birthPlace("test")
            .build();
        Person spouse = Person.builder()
            .name("spouse")
            .birthPlace("test")
            .build();
        Person personForConcurrent = Person.builder()
            .name("personForConcurrent")
            .birthPlace("test")
            .build();
        personId = personRepository.save(person).getId();
        firstParentId = personRepository.save(firstParent).getId();
        secondParentId = personRepository.save(secondParent).getId();
        personRepository.save(spouse);
        personRepository.save(personForConcurrent);
        Relationship personWithFirstParent = Relationship.builder()
            .relationFrom(person)
            .relationTo(firstParent)
            .relationType(RelationType.PARENT)
            .build();
        relationships.add(personWithFirstParent);
        Relationship personWithSecondParent = Relationship.builder()
            .relationFrom(person)
            .relationTo(secondParent)
            .relationType(RelationType.PARENT)
            .build();
        relationships.add(personWithSecondParent);
        Relationship personWithSpouse = Relationship.builder()
            .relationFrom(person)
            .relationTo(spouse)
            .relationType(RelationType.SPOUSE)
            .build();
        relationships.add(personWithSpouse);
        Relationship spouseWithPerson = Relationship.builder()
            .relationFrom(spouse)
            .relationTo(person)
            .relationType(RelationType.SPOUSE)
            .build();
        relationships.add(spouseWithPerson);
        relationshipRepository.saveAll(relationships);
    }

    @AfterEach
    void clear() {
        relationshipRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    public void findByIdTest() {
        PersonDto expected = PersonDto.builder()
            .name("person")
            .birthPlace("test")
            .spouse(RelatedPersonDto.builder()
                .name("spouse")
                .build())
            .parents(Arrays.asList(RelatedPersonDto.builder()
                    .name("firstParent")
                    .build(),
                RelatedPersonDto.builder()
                    .name("secondParent")
                    .build()))
            .build();

        PersonDto actual = personService.findById(personId);

        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getBirthDate(), actual.getBirthDate());
        Assertions.assertEquals(expected.getBirthPlace(), actual.getBirthPlace());
        Assertions.assertEquals(expected.getSpouse().getName(), actual.getSpouse().getName());
        Assertions.assertEquals(expected.getParents().size(), actual.getParents().size());
        List<String> actualParentNames = actual.getParents().stream()
            .map(RelatedPersonDto::getName)
            .collect(Collectors.toList());
        List<String> expectedParentNames = expected.getParents().stream()
            .map(RelatedPersonDto::getName)
            .collect(Collectors.toList());
        Assertions.assertEquals(expectedParentNames, actualParentNames);
    }

    @Test
    public void findByIdTest_wrong() {
        PersonDto actual = personService.findById(Long.MAX_VALUE);

        Assertions.assertNull(actual);
    }

    @Test
    public void addPersonTest() {
        PersonDto personWithParents = PersonDto.builder()
            .name("person")
            .birthPlace("test")
            .spouse(null)
            .parents(Arrays.asList(RelatedPersonDto.builder()
                    .id(firstParentId)
                    .build(),
                RelatedPersonDto.builder()
                    .id(secondParentId)
                    .build()))
            .build();

        long before = personRepository.count();
        Long addedPersonId = personService.addPerson(personWithParents);
        Assertions.assertNotNull(addedPersonId);
        Assertions.assertEquals(before + 1, personRepository.count());

        personRepository.findById(addedPersonId).ifPresent(person -> {
            Assertions.assertEquals(personWithParents.getName(), person.getName());
            Assertions.assertEquals(personWithParents.getBirthPlace(), person.getBirthPlace());
            Assertions.assertEquals(personWithParents.getBirthDate(), person.getBirthDate());
        });

        PersonDto personWithoutParents = PersonDto.builder()
            .name("person")
            .birthPlace("test")
            .spouse(null)
            .parents(null)
            .build();

        before = personRepository.count();
        Long addedPersonWithoutParents = personService.addPerson(personWithoutParents);
        Assertions.assertNotNull(addedPersonWithoutParents);
        Assertions.assertEquals(before + 1, personRepository.count());

        personRepository.findById(addedPersonWithoutParents).ifPresent(person -> {
            Assertions.assertEquals(personWithoutParents.getName(), person.getName());
            Assertions.assertEquals(personWithoutParents.getBirthPlace(), person.getBirthPlace());
            Assertions.assertEquals(personWithoutParents.getBirthDate(), person.getBirthDate());
        });
    }

    @Test
    public void addPersonTest_wrong() {
        long before = personRepository.count();

        Assertions.assertNull(personService.addPerson(null));
        Assertions.assertEquals(before, personRepository.count());
    }

    @Test
    public void addMarriageTest() {
        MarriageDto marriageDto = MarriageDto.builder()
            .firstPerson(firstParentId)
            .secondPerson(secondParentId)
            .build();

        Assertions.assertFalse(relationshipRepository
            .existsByRelationFromIdAndRelationTypeEquals(firstParentId, RelationType.SPOUSE));
        Assertions.assertFalse(relationshipRepository
            .existsByRelationFromIdAndRelationTypeEquals(secondParentId, RelationType.SPOUSE));

        personService.addMarriage(marriageDto);

        Assertions.assertTrue(relationshipRepository
            .existsByRelationFromIdAndRelationTypeEquals(firstParentId, RelationType.SPOUSE));
        Assertions.assertTrue(relationshipRepository
            .existsByRelationFromIdAndRelationTypeEquals(secondParentId, RelationType.SPOUSE));
    }

    @Test
    public void testConcurrentForMarriage() throws URISyntaxException {

        String url = "http://localhost:8070/people/relationships";
        final URI uri = new URI(url);
        List<MarriageDto> dtos = new ArrayList<>();
        dtos.add(MarriageDto.builder()
            .firstPerson(4L)
            .secondPerson(5L)
            .build());
        dtos.add(MarriageDto.builder()
            .firstPerson(4L)
            .secondPerson(7L)
            .build());
        ExecutorService service = Executors.newFixedThreadPool(2);

        for (MarriageDto dto : dtos) {
            service.execute(() -> {
                HttpEntity<MarriageDto> request = new HttpEntity<>(dto);
                ResponseEntity<Object> exchange = testRestTemplate.exchange(
                    uri,
                    HttpMethod.POST,
                    request,
                    Object.class);
                System.out.println(exchange.getStatusCode());
                System.out.println(exchange.getBody());
            });
        }
        while (!service.isTerminated()) {
            service.shutdown();
        }
    }
}

