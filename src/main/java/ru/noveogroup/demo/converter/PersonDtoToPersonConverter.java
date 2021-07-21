package ru.noveogroup.demo.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.noveogroup.demo.model.dto.PersonDto;
import ru.noveogroup.demo.model.entity.Person;

@Component
public class PersonDtoToPersonConverter implements Converter<PersonDto, Person> {

    @Override
    public Person convert(PersonDto source) {
        if (source == null) {
            return null;
        }
        return Person.builder()
            .id(source.getId())
            .name(source.getName())
            .birthDate(source.getBirthDate())
            .birthPlace(source.getBirthPlace())
            .build();
    }

}
