package it.interop.dgc.verifier.entity.dto;

import java.io.Serializable;
import java.util.Date;
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
public class DrlResponseDTO implements Serializable {

    /**
     * Serializzation.
     */
    private static final long serialVersionUID = -2569851440652674636L;

    /**
     * Id.
     */
    private String id;  
    
    /**
     * Version.
     */
    private Long fromVersion;
    
    /**
     * Version.
     */
    private Long version;  
    
    /**
     * Chunk.
     */
    private Integer chunk;  
    
    /**
     * Last Chunk.
     */
    private Integer lastChunk;  
    
    /**
     * Revoked ucvi.
     */ 
    private List<String> revokedUcvi;  
    
    /**
     * Delta.
     */
    private DeltaResponseDTO delta;
    
    /**
     * Creation date.
     */
    private Date creationDate;  
    
    /**
     * First elem in chunk.
     */ 
    private String firstElementInChunk;
    
    /**
     * Last elem in chunk.
     */ 
    private String lastElementInChunk;
     
    /**
     * Numero di add.
     */ 
    private Integer numDiAdd;
    
    /**
     * Numero di delete.
     */ 
    private Integer numDiDelete;
    
    /**
     * Total size in byte.
     */ 
    private Long totalSizeInByte; 
    
    /**
     * Single size in byte.
     */ 
    private Long sizeSingleChunkInByte;

}