package dev.tuzserik.business.logic.of.software.systems.lab3.activemq;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import dev.tuzserik.business.logic.of.software.systems.lab3.services.*;

@AllArgsConstructor @Component
public class BulkItemCreationConsumer {
    private final ItemService itemService;
    private final TypeService typeService;
    private final AttributeService attributeService;
    private final ParameterService parameterService;

    @Transactional @JmsListener(destination = "bulk-item-creation-new")
    public void messageListener(BulkItemCreationNewMessage bulkItemCreationNewMessage) {

    }

    @Transactional @JmsListener(destination = "bulk-item-creation-existing")
    public void messageListener(BulkItemCreationExistingMessage bulkItemCreationExistingMessage) {

    }
}
