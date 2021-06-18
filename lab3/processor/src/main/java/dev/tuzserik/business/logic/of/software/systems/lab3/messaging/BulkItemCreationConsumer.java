package dev.tuzserik.business.logic.of.software.systems.lab3.messaging;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;
import org.springframework.jms.annotation.JmsListener;
import java.util.Set;
import java.util.stream.Collectors;
import dev.tuzserik.business.logic.of.software.systems.lab3.services.*;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.*;
import dev.tuzserik.business.logic.of.software.systems.lab3.requests.*;

@AllArgsConstructor @Component
public class BulkItemCreationConsumer {
    private final ItemService itemService;
    private final TypeService typeService;
    private final AttributeService attributeService;
    private final ParameterService parameterService;

    @Transactional @JmsListener(destination = "bulk-item-creation-new")
    public void messageListener(BulkNewTypeItemCreationRequest requests) {
        for (NewTypeItemCreationRequest request : requests.getRequests())
            addItemWithNewType(request);
    }

    @Transactional @JmsListener(destination = "bulk-item-creation-existing")
    public void messageListener(BulkExistingTypeItemCreationRequest requests) {
        for (ExistingTypeItemCreationRequest request : requests.getRequests())
            addItemWithExistingType(request);
    }

    @Transactional
    void addItemWithNewType(NewTypeItemCreationRequest request) {
        Set<Attribute> attributes = request.getParameters().keySet().stream()
                .map(n -> new Attribute().setName(n))
                .collect(Collectors.toSet());
        attributes = attributeService.saveAttributes(attributes);
        final Type type = typeService.saveType(new Type().setName(request.getTypeName()).setAttributes(attributes));
        final Item item = itemService.saveItem(new Item().setName(request.getItemName()).setType(type));

        if (attributes != null && type != null && item != null) {
            parameterService.saveParameters(
                    attributes.stream()
                            .map(a -> new Parameter()
                                    .setItem(item)
                                    .setAttribute(a)
                                    .setValue(request.getParameters().get(a.getName()))
                            ).collect(Collectors.toSet())
            );
        }
    }

    @Transactional
    void addItemWithExistingType(ExistingTypeItemCreationRequest request) {
        if (typeService.verifyTypePresence(request.getTypeId(), request.getParameters().keySet())) {
            Item item = itemService.saveItem(
                    new Item()
                            .setName(request.getName())
                            .setType(typeService.getTypeById(request.getTypeId()))
            );

            parameterService.saveParameters(
                    request.getParameters().keySet().stream()
                            .map(k -> new Parameter()
                                    .setItem(item)
                                    .setAttribute(attributeService.getAttributeById(k))
                                    .setValue(request.getParameters().get(k))
                            ).collect(Collectors.toSet())
            );
        }
    }
}
