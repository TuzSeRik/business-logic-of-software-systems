package dev.tuzserik.business.logic.of.software.systems.lab2.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;
import java.util.UUID;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Attribute;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Type;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, UUID> {
    Collection<Attribute> findAllByTypesContains(Type type);
}
