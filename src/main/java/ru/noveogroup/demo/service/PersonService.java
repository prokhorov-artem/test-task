package ru.noveogroup.demo.service;

import ru.noveogroup.demo.model.dto.MarriageDto;
import ru.noveogroup.demo.model.dto.PersonDto;

public interface PersonService {

    PersonDto findById(Long id);

    Long addPerson(PersonDto personDto);

    boolean addMarriage(MarriageDto marriageDto);
}
