package dev.tuzserik.business.logic.of.software.systems.lab3.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.Set;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Type;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Attribute;

@Repository
public interface TypeRepository extends JpaRepository<Type, UUID> {
    Type findTypeByNameAndAttributesIn(String name, Set<Set<Attribute>> attributes);
}
