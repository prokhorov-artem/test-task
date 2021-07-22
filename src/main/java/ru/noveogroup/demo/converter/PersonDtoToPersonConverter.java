package ru.noveogroup.demo.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.noveogroup.demo.model.dto.PersonDto;
import ru.noveogroup.demo.model.entity.Person;
import ru.noveogroup.demo.service.DateService;

@Component
@RequiredArgsConstructor
public class PersonDtoToPersonConverter implements Converter<PersonDto, Person> {

    private final DateService dateService;

    @Override
    public Person convert(PersonDto source) {
        if (source == null) {
            return null;
        }
        return Person.builder()
            .id(source.getId())
            .name(source.getName())
            .birthDate(dateService.convert(source.getBirthDate()))
            .birthPlace(source.getBirthPlace())
            .build();
    }

}
