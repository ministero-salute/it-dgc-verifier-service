package it.interop.dgc.verifier.entity.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DIDTO implements Serializable {

    /**
     * Serialization.
     */
    private static final long serialVersionUID = -4947707904604590613L;

    /**
     * insert list.
     */
    @Field(name = "insertions")
    private List<String> insertions;

    /**
     * delete list.
     */
    @Field(name = "deletions")
    private List<String> deletions;
}
