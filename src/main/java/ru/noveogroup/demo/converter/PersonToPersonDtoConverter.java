package ru.noveogroup.demo.converter;

import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.noveogroup.demo.model.dto.PersonDto;
import ru.noveogroup.demo.model.entity.Person;
import ru.noveogroup.demo.service.DateService;

@Component
@RequiredArgsConstructor
public class PersonToPersonDtoConverter implements Converter<Person, PersonDto> {

    private final DateService dateService;

    @Override
    public PersonDto convert(Person source) {
        if (source == null) {
            return null;
        }
        return PersonDto.builder()
            .id(source.getId())
            .name(source.getName())
            .birthDate(dateService.convert(source.getBirthDate()))
            .birthPlace(source.getBirthPlace())
            .parents(new ArrayList<>())
            .build();
    }

}
