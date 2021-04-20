package dev.tuzserik.business.logic.of.software.systems.lab1.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.*;
import dev.tuzserik.business.logic.of.software.systems.lab1.model.Item;
import dev.tuzserik.business.logic.of.software.systems.lab1.repositories.AttributeRepository;
import dev.tuzserik.business.logic.of.software.systems.lab1.repositories.CartRepository;
import dev.tuzserik.business.logic.of.software.systems.lab1.model.Cart;
import dev.tuzserik.business.logic.of.software.systems.lab1.repositories.ParameterRepository;
import dev.tuzserik.business.logic.of.software.systems.lab1.model.Parameter;
import dev.tuzserik.business.logic.of.software.systems.lab1.repositories.TypeRepository;

@AllArgsConstructor @Service
public class CatalogService {
    private final TypeRepository typeRepository;
    private final AttributeRepository attributeRepository;
    private final ParameterRepository parameterRepository;
    private final CartRepository cartRepository;

    public boolean checkIfBelongsToOneType(String type, Set<UUID> attrValue) {
        return attrValue.stream()
                   .filter(a -> attributeRepository.getOne(a).getTypes()
                       .contains(typeRepository.getOne(UUID.fromString(type)))).count() == attrValue.size();
    }

    public Collection<Item> findAllAppropriateItems(Map<String, String> attrValue) {
        return parameterRepository.findAllByAttributeIn(
                   attributeRepository.findAllById(attrValue.keySet().stream().map(UUID::fromString)
                           .collect(Collectors.toSet())))
                 .stream().filter(o -> attrValue.get(o.getAttribute().getId().toString()).equals(o.getValue()))
                     .map(Parameter::getItem).collect(Collectors.toSet());
    }

    public UUID checkCartId(UUID cartId) {
        if (cartId.equals(new UUID(0, 0)))
            return UUID.randomUUID();
        return cartId;
    }

    public Collection<UUID> getCart(UUID id) {
        if (!cartRepository.existsById(id))
            return cartRepository.getOne(new UUID(0, 0)).getItemIds();
        return cartRepository.getOne(id).getItemIds();
    }

    public Collection<UUID> updateCart(UUID id, Collection<UUID> items) {
        if (!cartRepository.existsById(id))
            cartRepository.save(new Cart(id, new ArrayList<>()));
        return cartRepository.save(
                    cartRepository.getOne(id).setItemIds(items))
                .getItemIds();
    }
}
