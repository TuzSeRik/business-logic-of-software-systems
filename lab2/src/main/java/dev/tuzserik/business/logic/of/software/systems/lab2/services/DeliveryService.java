package dev.tuzserik.business.logic.of.software.systems.lab2.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;
import dev.tuzserik.business.logic.of.software.systems.lab2.repositories.DeliveryRepository;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Delivery;

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
