package dev.tuzserik.business.logic.of.software.systems.lab2.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.UUID;
import java.time.ZonedDateTime;
import dev.tuzserik.business.logic.of.software.systems.lab2.responses.*;
import dev.tuzserik.business.logic.of.software.systems.lab2.services.OrderService;
import dev.tuzserik.business.logic.of.software.systems.lab2.services.DeliveryService;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Order;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Delivery;

@AllArgsConstructor @RestController @RequestMapping("/api/administrator")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AdministratorController {
    private final OrderService orderService;
    private final DeliveryService deliveryService;

    @PostMapping("/delivery/submit")
    ResponseEntity<DeliveryStatusResponse> submitDelivery(@RequestParam UUID deliveryId){
        Delivery delivery = deliveryService.getDeliveryById(deliveryId);

        if (delivery != null) {
            if ((ZonedDateTime.now().isAfter(delivery.getDate()))
                    && delivery.getStatus().equals(Delivery.Status.AWAITING)) {
                delivery = deliveryService.saveDelivery(delivery.setStatus(Delivery.Status.SUBMITTED));

                if (orderService.submitOrderPaymentWithDelivery(delivery))
                    return new ResponseEntity<>(
                                new DeliveryStatusResponse(
                                        delivery.getId(), delivery.getType(),
                                        delivery.getStatus(), delivery.getDate()
                                ),
                                HttpStatus.OK
                    );
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/delivery/cancel")
    ResponseEntity<DeliveryStatusResponse> cancelDelivery(@RequestParam UUID deliveryId) {
        Delivery delivery = deliveryService.getDeliveryById(deliveryId);

        if (delivery != null) {
            if (delivery.getStatus() != Delivery.Status.AWAITING) {
                return new ResponseEntity<>(
                        new DeliveryStatusResponse(
                                delivery.getId(), delivery.getType(),
                                delivery.getStatus(), delivery.getDate()
                        ),
                        HttpStatus.OK
                );
            }
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/order/submit")
    ResponseEntity<OrderStatusResponse> submitOrder(@RequestParam UUID orderId){
        Order order = orderService.getOrderById(orderId);

        if (order != null) {
            if (order.getStatus() == Order.Status.PAYED) {
                if (order.getPaymentType() != Order.PaymentType.ONLINE &&
                        order.getDelivery().getType() == Delivery.Type.PICKUP)
                    deliveryService.saveDelivery(order.getDelivery().setStatus(Delivery.Status.SUBMITTED));

                order = orderService.saveOrder(order.setStatus(Order.Status.SUBMITTED));

                return new ResponseEntity<>(
                        new OrderStatusResponse(
                                order.getId(), order.getItemsIds(),
                                order.getPaymentType(), order.getDelivery(),
                                order.getStatus(), order.getTimestamp()),
                        HttpStatus.OK
                );
            }
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/order/cancel")
    ResponseEntity<OrderStatusResponse> cancelOrder(@RequestParam UUID orderId){
        Order order = orderService.getOrderById(orderId);

        if (order != null) {
            if (order.getStatus() != Order.Status.CANCELED &&
                    order.getStatus() != Order.Status.SUBMITTED) {
                order = orderService.saveOrder(order.setStatus(Order.Status.CANCELED));

                return new ResponseEntity<>(
                        new OrderStatusResponse(
                                order.getId(), order.getItemsIds(),
                                order.getPaymentType(), order.getDelivery(),
                                order.getStatus(), order.getTimestamp()),
                        HttpStatus.OK
                );
            }
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
