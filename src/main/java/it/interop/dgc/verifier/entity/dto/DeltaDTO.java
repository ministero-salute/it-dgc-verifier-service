package it.interop.dgc.verifier.entity.dto;

import org.springframework.data.mongodb.core.mapping.Field;

import it.interop.dgc.verifier.entity.DeltaETY;
import lombok.Data;

@Data
public class DeltaDTO {

    DeltaETY deltaETY;
    
    /**
     * delta.
     */
    @Field(name = "delta")
    private DIDTO delta;
     
    
}
