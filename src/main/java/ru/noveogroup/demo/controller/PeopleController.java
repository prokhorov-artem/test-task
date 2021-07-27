package ru.noveogroup.demo.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.noveogroup.demo.model.dto.MarriageDto;
import ru.noveogroup.demo.model.dto.PersonDto;
import ru.noveogroup.demo.service.PersonService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/people")
public class PeopleController {

    private final PersonService personService;

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable Long id) {
        PersonDto personDto = personService.findById(id);
        return personDto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(personDto);
    }

    @PostMapping
    public ResponseEntity<Long> addPerson(@RequestBody @Valid PersonDto personDto) {
        Long personId = personService.addPerson(personDto);
        return personId != null ? ResponseEntity.ok(personId) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/relationships")
    public ResponseEntity<Object> addMarriage(@RequestBody @Valid MarriageDto marriageDto) {
        if (personService.addMarriage(marriageDto)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
