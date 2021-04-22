package dev.tuzserik.business.logic.of.software.systems.lab2.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
}
