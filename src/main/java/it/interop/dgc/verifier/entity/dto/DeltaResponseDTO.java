package it.interop.dgc.verifier.entity.dto;

import java.io.Serializable;
import java.util.List;

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
public class DeltaResponseDTO implements Serializable {

    /**
     * Serializzation.
     */
    private static final long serialVersionUID = -2569851440652674636L;

    /**
     * Insertions.
     */
    private List<String> insertions;
    
    /**
     * Insertions.
     */
    private List<String> deletions;    
    

}