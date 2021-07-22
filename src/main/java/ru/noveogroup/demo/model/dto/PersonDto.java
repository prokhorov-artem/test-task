package ru.noveogroup.demo.model.dto;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.noveogroup.demo.service.impl.DateServiceImpl;
import ru.noveogroup.demo.validation.CheckDateFormat;

@Data
@Builder
@AllArgsConstructor
public class PersonDto {

    private Long id;
    private String name;
    @CheckDateFormat(pattern = DateServiceImpl.DATE_PATTERN)
    private String birthDate;
    private String birthPlace;
    @Valid
    private RelatedPersonDto spouse;
    @Size(max = 2)
    private List<@Valid RelatedPersonDto> parents;

}
