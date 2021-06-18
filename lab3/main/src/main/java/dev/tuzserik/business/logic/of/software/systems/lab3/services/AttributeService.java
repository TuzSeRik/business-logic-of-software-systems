package dev.tuzserik.business.logic.of.software.systems.lab3.services;

import dev.tuzserik.business.logic.of.software.systems.lab3.model.Attribute;
import dev.tuzserik.business.logic.of.software.systems.lab3.repositories.AttributeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Set;
import java.util.HashSet;
import java.util.UUID;

@AllArgsConstructor @Service
public class AttributeService {
    private final AttributeRepository attributeRepository;

    public Attribute getAttributeById(UUID attributeId) {
        return attributeRepository.getOne(attributeId);
    }

    public Set<Attribute> saveAttributes(Set<Attribute> attributes) {
        return new HashSet<>(attributeRepository.saveAll(attributes));
    }
}
