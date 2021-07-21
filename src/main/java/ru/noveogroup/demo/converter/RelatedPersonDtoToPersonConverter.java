package ru.noveogroup.demo.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.noveogroup.demo.model.dto.RelatedPersonDto;
import ru.noveogroup.demo.model.entity.Person;

@Component
public class RelatedPersonDtoToPersonConverter implements Converter<RelatedPersonDto, Person> {

    @Override
    public Person convert(RelatedPersonDto source) {
        if (source == null) {
            return null;
        }
        return Person.builder()
            .id(source.getId())
            .name(source.getName())
            .build();
    }

}
