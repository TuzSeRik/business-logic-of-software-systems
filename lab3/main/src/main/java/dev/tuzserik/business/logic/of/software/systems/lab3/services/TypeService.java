package dev.tuzserik.business.logic.of.software.systems.lab3.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import dev.tuzserik.business.logic.of.software.systems.lab3.repositories.TypeRepository;
import dev.tuzserik.business.logic.of.software.systems.lab3.repositories.AttributeRepository;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Type;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Attribute;

@AllArgsConstructor @Service
public class TypeService {
    private final TypeRepository typeRepository;
    private final AttributeRepository attributeRepository;

    public Type getTypeById(UUID typeId) {
        return typeRepository.getOne(typeId);
    }

    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    public Type findTypeByNameAndAttributesNames(String typeName, Set<String> attributeNames) {
        Set<Attribute> attributes =  attributeRepository.findAllByNameIn(attributeNames);

        return typeRepository.findTypeByNameAndAttributesIn(
                typeName,
                Collections.singleton(Optional.ofNullable(attributes).orElse(Collections.emptySet()))
        );
    }

    public boolean verifyTypePresence(UUID typeId, Set<UUID> attributesIds) {
        Type type = typeRepository.getOne(typeId);
        //noinspection ConstantConditions
        return type != null && type.getAttributes().equals(attributeRepository.findAllByIdIn(attributesIds));
    }
}
