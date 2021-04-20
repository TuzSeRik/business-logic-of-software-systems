package dev.tuzserik.business.logic.of.software.systems.lab1.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import dev.tuzserik.business.logic.of.software.systems.lab1.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID>{
}
