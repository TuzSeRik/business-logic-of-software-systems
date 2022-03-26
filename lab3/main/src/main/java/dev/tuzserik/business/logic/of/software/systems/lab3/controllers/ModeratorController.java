package dev.tuzserik.business.logic.of.software.systems.lab3.controllers;

import dev.tuzserik.business.logic.of.software.systems.lab3.repositories.AttributeRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.jms.core.JmsTemplate;
import java.util.stream.Collectors;
import java.util.*;
import dev.tuzserik.business.logic.of.software.systems.lab3.services.*;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.*;
import dev.tuzserik.business.logic.of.software.systems.lab3.requests.*;
import dev.tuzserik.business.logic.of.software.systems.lab3.responses.*;

import static dev.tuzserik.business.logic.of.software.systems.lab3.utils.Jwt.CLIENT_HOST;
import static dev.tuzserik.business.logic.of.software.systems.lab3.utils.Jwt.MODERATOR_ENDPOINT;

@AllArgsConstructor @RestController @RequestMapping(MODERATOR_ENDPOINT)
@CrossOrigin(origins = CLIENT_HOST, allowCredentials = "true")
public class ModeratorController {
    private final ItemService itemFinder;
    private final TypeService typeFinder;
    private final AttributeRepository attributePersistence;
    private final ParameterService parametersPersistence;
    private final JmsTemplate messageTemplate;

    @PostMapping("/type/check")
    ResponseEntity<TypeInformationResponse> checkTypeExistence(@RequestBody TypeExistenceRequest request) {
        if (!request.getTypeName().isEmpty() && !request.getAttributesNames().isEmpty()) {
            Type type = typeFinder.findTypeByNameAndAttributesNames(request.getTypeName(), request.getAttributesNames());

            if (type != null) {
                return new ResponseEntity<>(
                        new TypeInformationResponse(
                                type.getId(),
                                type.getName(),
                                type.getAttributes()
                        ),
                        HttpStatus.OK
                );
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/type/new/item/add") @Transactional
    ResponseEntity<ItemInformationResponse> addItemWithNewType(@RequestBody NewTypeItemCreationRequest request) {
        Set<Attribute> attributes = request.getParameters().keySet().stream()
                                            .map(n -> new Attribute().setName(n))
                                            .collect(Collectors.toSet());
        attributes = new HashSet<>(attributePersistence.saveAll(attributes));
        final Type type = typeFinder.saveType(new Type().setName(request.getTypeName()).setAttributes(attributes));
        final Item item = itemFinder.saveItem(new Item().setName(request.getItemName()).setType(type));

        if (attributes != null && type != null && item != null) {
            Set<Parameter> parameters = parametersPersistence.saveParameters(
                    attributes.stream()
                            .map(a -> new Parameter()
                                    .setItem(item)
                                    .setAttribute(a)
                                    .setValue(request.getParameters().get(a.getName()))
                            ).collect(Collectors.toSet())
            );

            return new ResponseEntity<>(
                    new ItemInformationResponse(
                            item.getId(),
                            item.getName(),
                            item.getType().getId(),
                            parameters
                    ),
                    HttpStatus.OK
            );
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/type/existing/item/add") @Transactional
    ResponseEntity<ItemInformationResponse> addItemWithExistingType(@RequestBody ExistingTypeItemCreationRequest request) {
        if (typeFinder.verifyTypePresence(request.getTypeId(), request.getParameters().keySet())) {
            Item item = itemFinder.saveItem(
                    new Item()
                            .setName(request.getName())
                            .setType(typeFinder.getTypeById(request.getTypeId()))
            );

            Set<Parameter> parameters = parametersPersistence.saveParameters(
                    request.getParameters().keySet().stream()
                            .map(k -> new Parameter()
                                    .setItem(item)
                                    .setAttribute(attributePersistence.getOne(k))
                                    .setValue(request.getParameters().get(k))
                            ).collect(Collectors.toSet())
            );

            return new ResponseEntity<>(
                    new ItemInformationResponse(
                            item.getId(),
                            item.getName(),
                            item.getType().getId(),
                            parameters
                    ),
                    HttpStatus.OK
            );
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/type/new/item/add/bulk") @Transactional
    HttpStatus addBulkItemWithNewType(@RequestBody BulkNewTypeItemCreationRequest request) {
        messageTemplate.convertAndSend("bulk-item-creation-new", request);

        return HttpStatus.OK;
    }

    @PostMapping("/type/existing/item/add/bulk") @Transactional
    HttpStatus addItemWithExistingType(@RequestBody BulkExistingTypeItemCreationRequest request) {
        messageTemplate.convertAndSend("bulk-item-creation-existing", request);

        return HttpStatus.OK;
    }
}
