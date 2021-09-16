package it.interop.dgc.verifier.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.interop.dgc.verifier.config.DrlCFG;
import it.interop.dgc.verifier.entity.dto.ChunkDTO;
import it.interop.dgc.verifier.entity.dto.DRLDTO;
import it.interop.dgc.verifier.entity.dto.DeltaResponseDTO;
import it.interop.dgc.verifier.entity.dto.DrlResponseDTO;
import it.interop.dgc.verifier.exceptions.BusinessException;
import it.interop.dgc.verifier.exceptions.ValidationException;
import it.interop.dgc.verifier.service.DRLSRV;
import it.interop.dgc.verifier.utils.ChunkUtility;
import it.interop.dgc.verifier.utils.Validation;


@RestController
@RequestMapping(path = "/v1/dgc/drl")
@Tag(name = "Servizio dei Certificati")
public class DrlController {

	@Autowired
	private DRLSRV drlSRV;

	@Autowired
	private DrlCFG crlCFG;

	/**
     * Metodo per ottenere la digital green certificate revocation list.
     * 
     * @param version        version
     * @param chunk          chunk
     * @return               DrlResponseDTO
     */
	@GetMapping("")
    @Operation(summary = "Ottieni la crl", description = "Servizio che consente di recuperare la certificate revocation list.")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DrlResponseDTO.class)))
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista trovata"),
                            @ApiResponse(responseCode = "400", description = "Bad Request"),
                            @ApiResponse(responseCode = "404", description = "Record not found"),
                            @ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public DrlResponseDTO getDRL(final @RequestParam(required = false, name = "version") Long version, @RequestParam(required = false, name = "chunk") Integer chunk, HttpServletRequest request) {
	    
	    Validation.isValidVersion(version);
	    DRLDTO drlDTO = drlSRV.getDRL(version,false); 
	    return buildOutputDrl(drlDTO, chunk,false);
	}

	 
	/**
     * Metodo per la chiamata ispettiva per il download della digital green certificate revocation list.
     * 
     * @param version        version
     * @param chunk          chunk
     * @return               DrlResponseDTO
     */
	@GetMapping("/check")
    @Operation(summary = "Chiamata ispettiva per la la crl", description = "Servizio che consente di effettuare una chiamata ispettiva per la crl.")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DrlResponseDTO.class)))
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista trovata"),
                            @ApiResponse(responseCode = "400", description = "Bad Request"),
                            @ApiResponse(responseCode = "404", description = "Record not found"),
                            @ApiResponse(responseCode = "500", description = "Internal Server Error") })
	public DrlResponseDTO checkDRL(final @RequestParam(required = true, name = "version") Long version, @RequestParam(required = false, name = "chunk") Integer chunk, HttpServletRequest request) { 

	    Validation.isValidVersion(version);
	    DRLDTO drlDTO = drlSRV.getDRL(version,true); 
	    return buildOutputDrl(drlDTO, chunk,true); 
	}

	private DrlResponseDTO buildOutputDrl(DRLDTO drlDTO,Integer chunk, boolean isIspettiva) {
	    DrlResponseDTO output = null;

	    //Se chunk non mi viene dato lo setto al primo elemento
	    if(chunk==null) {
            chunk = 1;
        }
         
	    if(drlDTO.getCrl()!=null) { 
	        output = buildOutputSnap(drlDTO, chunk, isIspettiva); 
	    } else if(drlDTO.getDeltaVers()!=null) {
	        output = buildOutputDelta(drlDTO, chunk, isIspettiva);
	    }

	    return output; 
	}

    private DrlResponseDTO buildOutputDelta(DRLDTO drlDTO, Integer chunk, boolean isIspettiva) {
        DrlResponseDTO output = new DrlResponseDTO();
        output.setId(drlDTO.getDeltaVers().getId());
        output.setFromVersion(drlDTO.getDeltaVers().getFromVersion());
        output.setVersion(drlDTO.getDeltaVers().getToVersion());
         
        List<String> totalChunk = new ArrayList<>();
        totalChunk.addAll(drlDTO.getDeltaVers().getDelta().getInsertions());
        totalChunk.addAll(drlDTO.getDeltaVers().getDelta().getDeletions());

        ChunkDTO chunkDTO = getListChunk(totalChunk,chunk);

        output.setChunk(chunkDTO.getSizeTotaliDeiChunk());
        List<String> insert = new ArrayList<>();
        List<String> delete = new ArrayList<>();
        for(String singleChunk : chunkDTO.getListChunk()) {
            if(drlDTO.getDeltaVers().getDelta().getInsertions().contains(singleChunk)){
                insert.add(singleChunk);                
            } else if(drlDTO.getDeltaVers().getDelta().getDeletions().contains(singleChunk)){
                delete.add(singleChunk);
            }
        }
        DeltaResponseDTO respDelt = new DeltaResponseDTO();
        respDelt.setInsertions(insert);
        respDelt.setDeletions(delete);

        
        if(isIspettiva) {
            output.setNumDiAdd(respDelt.getInsertions().size());
            output.setNumDiDelete(respDelt.getDeletions().size());

            output.setTotalSizeInByte(chunkDTO.getSizeTotalInByte());
            output.setSizeSingleChunkInByte(chunkDTO.getSizeSingleChunkInByte());
        } else {
            output.setChunk(chunk);
            output.setLastChunk(chunkDTO.getSizeTotaliDeiChunk());
            output.setDelta(respDelt);
        }
        return output;
    }

	/**
     * Costruzione response dato uno snap.
     * 
     * @param drlDTO                  drlDTO
     * @param chunk                   chunk
     * @param isIspettiva             isIspettiva
     * @return                        DrlResponseDTO
     */
    private DrlResponseDTO buildOutputSnap(DRLDTO drlDTO, Integer chunk, boolean isIspettiva) {
        DrlResponseDTO output = new DrlResponseDTO();
        output.setId(drlDTO.getCrl().getId());
        output.setVersion(drlDTO.getCrl().getVersion());
        

        if(isIspettiva) {
            output.setNumDiAdd(0);
        } else {
            output.setCreationDate(drlDTO.getCrl().getCreationDate());
            output.setRevokedUcvi(new ArrayList<>());
        }
 
        ChunkDTO chunkDTO = null;
        if(drlDTO.getCrl().getRevokedUcvi()!=null && !drlDTO.getCrl().getRevokedUcvi().isEmpty()) {
            chunkDTO = getListChunk(drlDTO.getCrl().getRevokedUcvi(),chunk); 
            output.setChunk(chunk);
            if(isIspettiva) {
                output.setNumDiAdd(drlDTO.getCrl().getRevokedUcvi().size());
                output.setTotalSizeInByte(chunkDTO.getSizeTotalInByte());          
                output.setSizeSingleChunkInByte(chunkDTO.getSizeSingleChunkInByte());
            } else {
                output.setLastChunk(chunkDTO.getSizeTotaliDeiChunk());
                output.setRevokedUcvi(chunkDTO.getListChunk());
                output.setFirstElementInChunk(chunkDTO.getListChunk().get(0));
                output.setLastElementInChunk(chunkDTO.getListChunk().get(chunkDTO.getListChunk().size()-1));
            }
            
        }
        return output;
    }

	private ChunkDTO getListChunk(List<String> listCalcolaChunk,Integer chunk){
	    List<String> chunkList = new ArrayList<>(); 
        Long sizeTotalInByte = 0L;
        Long sizeSingleChunkByte = 0L;
        Integer numTotaleChunk = 0;
        
	    Stream<List<String>> out = ChunkUtility.calcolaChunk(listCalcolaChunk, crlCFG.getNumMaxItemInChunk());
	     
	    try {
	        Object[] vettChunk = out.toArray(); 
            if(chunk > vettChunk.length || chunk<=0) {
                throw new ValidationException("Chunk non esistente");
            }
            
	        List<Object> listAllChunk = Arrays.asList(vettChunk);
	        
	        numTotaleChunk = listAllChunk.size(); 
	        if(chunk!=null) {
	            for(int i=chunk; i<listAllChunk.size(); i++) {
	                List<String> listChunk = new ArrayList<>(); 
	                listChunk.addAll((List<String>) listAllChunk.get(i));
	                sizeTotalInByte += ChunkUtility.getBytesFromObj(listChunk); 
	            }
	        } 
   
	        Object vettChunkSingle = vettChunk[chunk-1];
	       
	        chunkList.addAll((List<String>) vettChunkSingle); 

	        sizeSingleChunkByte = ChunkUtility.getBytesFromObj(crlCFG.getNumMaxItemInChunk());
	    } catch(Exception ex) {
//	        LOGGER.error("Errore nel calcolo dei chunk : ",ex);	        
	        throw new BusinessException("Errore nel calcolo dei chunk : "+ex);
	    }
	     
	    return new ChunkDTO(chunkList,numTotaleChunk,sizeTotalInByte,sizeSingleChunkByte);
	}
	
}
