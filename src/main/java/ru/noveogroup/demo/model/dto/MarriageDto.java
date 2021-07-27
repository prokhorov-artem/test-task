package ru.noveogroup.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.noveogroup.demo.validation.PersonExists;
import ru.noveogroup.demo.validation.SpousesAreNotSame;

@Data
@Builder
@AllArgsConstructor
@SpousesAreNotSame
public class MarriageDto {

    @PersonExists
    private Long firstPerson;
    @PersonExists
    private Long secondPerson;

}
