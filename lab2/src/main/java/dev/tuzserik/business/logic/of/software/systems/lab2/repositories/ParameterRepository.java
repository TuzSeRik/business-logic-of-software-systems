package dev.tuzserik.business.logic.of.software.systems.lab2.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;
import java.util.UUID;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Parameter;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Attribute;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, UUID> {
    Collection<Parameter> findAllByAttributeIn(Collection<Attribute> attributes);
}
