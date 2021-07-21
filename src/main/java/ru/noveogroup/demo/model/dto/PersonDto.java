package ru.noveogroup.demo.model.dto;

import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PersonDto {

    private Long id;
    private String name;
    private Date birthDate;
    private String birthPlace;
    @Valid
    private RelatedPersonDto spouse;
    @Size(max = 2)
    private List<@Valid RelatedPersonDto> parents;

}
