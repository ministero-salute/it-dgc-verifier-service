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
@Document(collection = DRLEUConstants.SnapshotEntryField.COLLECTION_NAME)
public class EuropeanSnapshotEntryETY {

    /**
     * Pk document.
     */
    @Id
    private String id;

    /**
     * Version.
     */
    @Field(name = DRLEUConstants.SnapshotEntryField.VERSION)
    private Long version;

    /**
     * Revoked pass identifier.
     */
    @Field(name = DRLEUConstants.SnapshotEntryField.IDENTIFIER)
    private String hash;

}
