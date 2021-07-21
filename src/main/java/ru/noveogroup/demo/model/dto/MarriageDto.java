package ru.noveogroup.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.noveogroup.demo.validation.PersonExists;
import ru.noveogroup.demo.validation.SpouseExists;

@Data
@Builder
@AllArgsConstructor
public class MarriageDto {

    @PersonExists
    @SpouseExists
    private Long firstPerson;
    @PersonExists
    @SpouseExists
    private Long secondPerson;

}
