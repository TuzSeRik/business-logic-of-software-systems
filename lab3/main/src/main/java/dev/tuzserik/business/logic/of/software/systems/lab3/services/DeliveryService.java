package dev.tuzserik.business.logic.of.software.systems.lab3.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;
import dev.tuzserik.business.logic.of.software.systems.lab3.repositories.DeliveryRepository;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Delivery;

@AllArgsConstructor @Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    public Delivery getDeliveryById(UUID id){
        return deliveryRepository.getOne(id);
    }

    public Delivery saveDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }
}
