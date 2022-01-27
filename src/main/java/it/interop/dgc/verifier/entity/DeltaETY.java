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

@Getter
@Setter
@Document(collection = "delta")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeltaETY {

    /**
     * Pk document.
     */
    @Id
    private String id;

    /**
     * from_version.
     */
    @Field(name = "from_version")
    private Long fromVersion;

    /**
     * to_version.
     */
    @Field(name = "to_version")
    private Long toVersion;


    
    /**
     * Num totale ucvi.
     */
    @Field(name = "num_totale_ucvi")
    private Integer numTotaleUCVI;
    
    /**
     * Num totale ucvi add.
     */
    @Field(name = "num_totale_add")
    private Integer numTotaleAdd;
    
    /**
     * Num totale ucvi remove.
     */
    @Field(name = "num_totale_remove")
    private Integer numTotaleRemove;
}
