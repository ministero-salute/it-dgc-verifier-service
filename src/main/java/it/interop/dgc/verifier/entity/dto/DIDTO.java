package it.interop.dgc.verifier.entity.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
     * Lista delle insert.
     */
    @Field(name = "insertions")    
    private List<String> insertions;

    /**
     * Lista delle delete.
     */
    @Field(name = "deletions")    
    private List<String> deletions;

}