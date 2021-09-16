package it.interop.dgc.verifier.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import it.interop.dgc.verifier.entity.dto.DIDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model per il salvataggio di un pass.
 */
@Getter
@Setter
@Document(collection = "delta")
@Data 
@Builder 
@NoArgsConstructor
@AllArgsConstructor
public class DeltaETY {


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
    @Field(name = "delta")
    private DIDTO delta;


}
