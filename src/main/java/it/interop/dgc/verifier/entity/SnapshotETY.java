package it.interop.dgc.verifier.entity;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document(collection = "snapshot")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SnapshotETY {

    /**
     * Pk document.
     */
    @Id
    private String id;

    /**
     * version.
     */
    @Field(name = "version")
    private Long version;

    /**
     * revoked_ucvi.
     */
    @Field(name = "revoked_ucvi")
    private List<String> revokedUcvi;

    /**
     * creation_date.
     */
    @Field(name = "creation_date")
    private Date creationDate;
     
    /**
     * Flag revoke.
     */
    @Field(name = "flag_archived")
    private Boolean flag_archived;
    
    /**
     * Num totale ucvi.
     */
    @Field(name = "num_totale_ucvi")
    private Integer numTotaleUCVI;
}
