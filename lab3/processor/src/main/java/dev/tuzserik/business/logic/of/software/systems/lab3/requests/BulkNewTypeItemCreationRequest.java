package dev.tuzserik.business.logic.of.software.systems.lab3.requests;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor @NoArgsConstructor @Data
public class BulkNewTypeItemCreationRequest implements Serializable {
    private Set<NewTypeItemCreationRequest> requests;
}
