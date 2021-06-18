package dev.tuzserik.business.logic.of.software.systems.lab3.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.*;
import dev.tuzserik.business.logic.of.software.systems.lab3.repositories.*;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Item;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Parameter;

@AllArgsConstructor @Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final TypeRepository typeRepository;
    private final AttributeRepository attributeRepository;
    private final ParameterRepository parameterRepository;

    public Item getItemById(UUID itemId) {
        return itemRepository.getOne(itemId);
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public boolean verifyAttributes(String type, Set<UUID> attrIds) {
        return attrIds.stream()
                   .filter(a -> attributeRepository.getOne(a).getTypes()
                       .contains(typeRepository.getOne(UUID.fromString(type)))).count() == attrIds.size();
    }

    public Set<Item> findAllAppropriateItems(Map<String, String> parameters) {
        return parameterRepository.findAllByAttributeIn(
                    attributeRepository.findAllById(
                        parameters.keySet().stream().map(UUID::fromString)
                            .collect(Collectors.toSet())
                )
        ).stream().filter(o -> parameters.get(o.getAttribute().getId().toString()).equals(o.getValue()))
                    .map(Parameter::getItem)
                    .collect(Collectors.toSet());
    }
}
