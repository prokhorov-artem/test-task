package ru.noveogroup.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.noveogroup.demo.model.RelationType;
import ru.noveogroup.demo.model.entity.Relationship;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

    List<Relationship> findAllByRelationFromId(Long relationFrom);

    boolean existsByRelationFromIdAndRelationTypeEquals(Long relationFrom, RelationType type);

}
