package ru.noveogroup.demo.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.noveogroup.demo.model.dto.RelatedPersonDto;
import ru.noveogroup.demo.model.entity.Person;

@Component
public class PersonToRelatedPersonDtoConverter implements Converter<Person, RelatedPersonDto> {

    @Override
    public RelatedPersonDto convert(Person source) {
        if (source == null) {
            return null;
        }
        return RelatedPersonDto.builder()
            .id(source.getId())
            .name(source.getName())
            .build();
    }

}
