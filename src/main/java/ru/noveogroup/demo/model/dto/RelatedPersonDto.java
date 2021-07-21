package ru.noveogroup.demo.model.dto;

import lombok.Builder;
import lombok.Data;
import ru.noveogroup.demo.validation.PersonExists;

@Data
@Builder
public class RelatedPersonDto {

    @PersonExists
    private Long id;
    private String name;

}
