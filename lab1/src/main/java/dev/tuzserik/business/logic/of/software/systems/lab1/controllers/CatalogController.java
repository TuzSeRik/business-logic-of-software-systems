package dev.tuzserik.business.logic.of.software.systems.lab1.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.UUID;
import dev.tuzserik.business.logic.of.software.systems.lab1.services.CatalogService;
import dev.tuzserik.business.logic.of.software.systems.lab1.model.Item;
import dev.tuzserik.business.logic.of.software.systems.lab1.requests.CartUpdateRequest;
import dev.tuzserik.business.logic.of.software.systems.lab1.responses.CartStatusResponse;
import dev.tuzserik.business.logic.of.software.systems.lab1.responses.CatalogListResponse;

@AllArgsConstructor @RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CatalogController {
    private final CatalogService catalogService;

    @GetMapping("/api/catalog")
    ResponseEntity<CatalogListResponse> getAppropriateItemList(@RequestParam Map<String, String> allRequestParams) {
        if (catalogService.checkIfBelongsToOneType(allRequestParams.remove("type"),
                allRequestParams.keySet().stream().map(UUID::fromString).collect(Collectors.toSet()))) {
            return new ResponseEntity<>(
                    new CatalogListResponse(catalogService.findAllAppropriateItems(allRequestParams)
                            .stream().map(Item::getId).collect(Collectors.toList())),
                    HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/api/cart")
    ResponseEntity<CartStatusResponse> getCart(@RequestParam UUID id) {
        return new ResponseEntity<>(
            new CartStatusResponse(
                    catalogService.checkCartId(id), catalogService.getCart(id)),
            HttpStatus.OK);
    }

    @PostMapping("/api/cart")
    ResponseEntity<CartStatusResponse> updateCart(@RequestBody CartUpdateRequest request) {
        return new ResponseEntity<>(
            new CartStatusResponse(
                catalogService.checkCartId(request.getCartId()),
                catalogService.updateCart(request.getCartId(), request.getItems())),
            HttpStatus.OK);
    }
}
