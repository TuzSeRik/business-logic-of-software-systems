package dev.tuzserik.business.logic.of.software.systems.lab3.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID>{
}
