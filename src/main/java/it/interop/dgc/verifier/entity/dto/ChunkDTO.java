package it.interop.dgc.verifier.entity.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
 


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
    *  chunk total size.
    */
   private Integer sizeTotaliDeiChunk;
   
   /**
    *  total size in byte.
    */
   private Long sizeTotalInByte;
   
   /**
    *  chunk total size in byte.
    */
   private Long sizeSingleChunkInByte;
   
   


}
