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
@Document(collection = DRLEUConstants.DeltaEntryField.COLLECTION_NAME)
public class EuropeanDeltaEntryETY {

    /**
     * Pk document.
     */
    @Id
    private String id;

    /**
     * Startin version.
     */
    @Field(name = DRLEUConstants.DeltaEntryField.FROM_VERSION)
    private Long fromVersion;

    /**
     * Final version.
     */
    @Field(name = DRLEUConstants.DeltaEntryField.TO_VERSION)
    private Long toVersion;

    /**
     * Identifier to add if non-null.
     */
    @Field(name = DRLEUConstants.DeltaEntryField.ADD_IDENTIFIER)
    private String identifierToAdd;

    /**
     * Identifier to remove if non-null.
     */
    @Field(name = DRLEUConstants.DeltaEntryField.DEL_IDENTIFIER)
    private String identifierToRemove;

}
