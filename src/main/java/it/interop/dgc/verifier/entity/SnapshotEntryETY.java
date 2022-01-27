package it.interop.dgc.verifier.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author vincenzoingenito
 * Model per il salvataggio di uno snapshot.
 */
@Getter
@Setter
@Document(collection = "snapshotEntry")
@Data 
@Builder 
@NoArgsConstructor
@AllArgsConstructor
public class SnapshotEntryETY {


    /**
     * Pk documento.
     */
    @Id
    private String id;

    /**
     * Versione.
     */
    @Field(name = "version")
    private Long version;

    /**
     * Lista dei pass revocati.
     */
    @Field(name = "revoked_ucvi")
    private String revokedUcvi;


}
