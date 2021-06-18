package dev.tuzserik.business.logic.of.software.systems.lab3.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Parameter;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, UUID> {
}
