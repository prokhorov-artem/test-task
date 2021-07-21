package ru.noveogroup.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.noveogroup.demo.model.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsById(Long id);

}
