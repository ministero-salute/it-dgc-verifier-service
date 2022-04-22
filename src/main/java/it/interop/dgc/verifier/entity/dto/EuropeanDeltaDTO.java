package it.interop.dgc.verifier.entity.dto;

import org.springframework.data.mongodb.core.mapping.Field;

import it.interop.dgc.verifier.entity.EuropeanDeltaETY;
import lombok.Data;

@Data
public class EuropeanDeltaDTO {

    /**
     * Delta ETY.
     */
    EuropeanDeltaETY deltaETY;
    
    /**
     * Delta.
     */
    @Field(name = "delta")
    private DIDTO delta;
     
    
}
