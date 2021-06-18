package dev.tuzserik.business.logic.of.software.systems.lab3.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import dev.tuzserik.business.logic.of.software.systems.lab3.repositories.ItemRepository;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Item;

@AllArgsConstructor @Service
public class ItemService {
    private final ItemRepository itemRepository;

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }
}
