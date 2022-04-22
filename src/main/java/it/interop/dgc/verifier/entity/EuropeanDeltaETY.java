package it.interop.dgc.verifier.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import it.interop.dgc.verifier.utils.DRLEUConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = DRLEUConstants.DeltaField.COLLECTION_NAME)
public class EuropeanDeltaETY {

    /**
     * Pk document.
     */
    @Id
    private String id;

    /**
     * Starting version.
     */
    @Field(name = DRLEUConstants.DeltaField.FROM_VERSION)
    private Long fromVersion;

    /**
     * Final version.
     */
    @Field(name = DRLEUConstants.DeltaField.TO_VERSION)
    private Long toVersion;

    /**
     * Num totale revoke.
     */
    @Field(name = DRLEUConstants.DeltaField.TOTAL)
    private Integer numberRevocation;

    /**
     * Num total pass to add.
     */
    @Field(name = DRLEUConstants.DeltaField.NUM_ADD)
    private Integer numTotaleAdd;

    /**
     * Num total pass to remove.
     */
    @Field(name = DRLEUConstants.DeltaField.NUM_DELETE)
    private Integer numTotaleRemove;
    
}
