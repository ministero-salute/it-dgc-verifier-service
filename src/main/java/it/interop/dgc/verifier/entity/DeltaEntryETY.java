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
 * Model per il salvataggio di uno delta.
 */
@Getter
@Setter
@Document(collection = "deltaEntry")
@Data 
@Builder 
@NoArgsConstructor
@AllArgsConstructor
public class DeltaEntryETY {


    /**
     * Pk documento.
     */
    @Id
    private String id;

    /**
     * Versione di partenza.
     */
    @Field(name = "from_version")
    private Long fromVersion;

    /**
     * Versione di arrivo.
     */
    @Field(name = "to_version")
    private Long toVersion;

    /**
     * Operazioni da eseguire per colmare il delta.
     */
    @Field(name = "ucvi_hashed_to_add")
    private String ucvi_hashed_to_add;
    
    @Field(name = "ucvi_hashed_to_remove")
    private String ucvi_hashed_to_remove;


}