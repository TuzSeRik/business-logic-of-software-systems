package dev.tuzserik.business.logic.of.software.systems.lab1.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;
import java.util.UUID;
import dev.tuzserik.business.logic.of.software.systems.lab1.model.Type;
import dev.tuzserik.business.logic.of.software.systems.lab1.model.Parameter;

@Repository
public interface TypeRepository extends JpaRepository<Type, UUID> {
    Collection<Type> findAllByAttributesIn(Collection<Parameter> parameters);
}
