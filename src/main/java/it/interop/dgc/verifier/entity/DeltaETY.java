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
     * delta.
     */
    @Field(name = "delta")
    private DIDTO delta;


}
