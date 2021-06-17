package dev.tuzserik.business.logic.of.software.systems.lab3.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.HashSet;
import dev.tuzserik.business.logic.of.software.systems.lab3.repositories.ParameterRepository;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Parameter;

@AllArgsConstructor @Service
public class ParameterService {
    private final ParameterRepository parameterRepository;

    public Set<Parameter> saveParameters(Set<Parameter> parameters) {
        return new HashSet<>(parameterRepository.saveAll(parameters));
    }
}
