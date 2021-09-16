package it.interop.dgc.verifier.entity.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
 


/*
* @author vincenzoingenito 
* Classe per mappare le informazioni del chunk.
*/
@Getter 
@AllArgsConstructor
public class ChunkDTO implements Serializable {

   /**
    *  serial version uid
    */
   private static final long serialVersionUID = 7823535468752584993L;
   
   /**
    *  List chunk.
    */
   private List<String> listChunk;
   
   /**
    *  Size totale dei chunk.
    */
   private Integer sizeTotaliDeiChunk;
   
   /**
    *  Size totale in byte.
    */
   private Long sizeTotalInByte;
   
   /**
    *  Size totale in byte.
    */
   private Long sizeSingleChunkInByte;
   
   


}
