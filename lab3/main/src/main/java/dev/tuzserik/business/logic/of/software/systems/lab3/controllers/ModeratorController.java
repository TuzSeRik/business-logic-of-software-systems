package dev.tuzserik.business.logic.of.software.systems.lab3.controllers;

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

@AllArgsConstructor @RestController @RequestMapping("/api/moderator")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ModeratorController {
    private final ItemService itemService;
    private final TypeService typeService;
    private final AttributeService attributeService;
    private final ParameterService parameterService;
    private final JmsTemplate jmsTemplate;

    @PostMapping("/type/check")
    ResponseEntity<TypeInformationResponse> checkTypeExistence(@RequestBody TypeExistenceRequest request) {
        if (!request.getTypeName().isEmpty() && !request.getAttributesNames().isEmpty()) {
            Type type = typeService.findTypeByNameAndAttributesNames(request.getTypeName(), request.getAttributesNames());

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
        attributes = attributeService.saveAttributes(attributes);
        final Type type = typeService.saveType(new Type().setName(request.getTypeName()).setAttributes(attributes));
        final Item item = itemService.saveItem(new Item().setName(request.getItemName()).setType(type));

        if (attributes != null && type != null && item != null) {
            Set<Parameter> parameters = parameterService.saveParameters(
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
        if (typeService.verifyTypePresence(request.getTypeId(), request.getParameters().keySet())) {
            Item item = itemService.saveItem(
                    new Item()
                            .setName(request.getName())
                            .setType(typeService.getTypeById(request.getTypeId()))
            );

            Set<Parameter> parameters = parameterService.saveParameters(
                    request.getParameters().keySet().stream()
                            .map(k -> new Parameter()
                                    .setItem(item)
                                    .setAttribute(attributeService.getAttributeById(k))
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
        jmsTemplate.convertAndSend("bulk-item-creation-new", request);

        return HttpStatus.OK;
    }

    @PostMapping("/type/existing/item/add/bulk") @Transactional
    HttpStatus addItemWithExistingType(@RequestBody BulkExistingTypeItemCreationRequest request) {
        jmsTemplate.convertAndSend("bulk-item-creation-existing", request);

        return HttpStatus.OK;
    }
}
