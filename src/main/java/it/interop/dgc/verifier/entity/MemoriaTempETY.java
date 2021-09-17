package it.interop.dgc.verifier.entity;

import java.util.Date;

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
 */
@Getter
@Setter
@Document(collection = "memoria_temporanea")
@Data 
@Builder 
@NoArgsConstructor
@AllArgsConstructor
public class MemoriaTempETY {
 
    
    /**
     * Pk document.
     */
    @Id
    private String id;

    /**
     * Revoked ucvi.
     */
    @Field(name = "revokedUcvi")
    private String revokedUcvi;
    
    /**
     * Revoked ucvi.
     */
    @Field(name = "revokedUcviSha")
    private String revokedUcviSha;
    
    /**
     * insert date.
     */
    @Field(name = "dataInserimento")
    private Date dataInserimento;

    /**
     * operation.
     */
    @Field(name = "operazione")
    private String operazione;
    
    /**
     * flag to delete.
     */
    @Field(name = "daCancellare")
    private boolean daCancellare;
}
