package it.interop.dgc.verifier.controller;

import java.util.ArrayList;
import java.util.List;

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
import it.interop.dgc.verifier.entity.dto.DRLDTO;
import it.interop.dgc.verifier.entity.dto.DeltaResponseDTO;
import it.interop.dgc.verifier.entity.dto.DrlResponseDTO;
import it.interop.dgc.verifier.service.DRLSRV;
import it.interop.dgc.verifier.utils.Validation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/v1/dgc/drl")
@Tag(name = "Certificate service")
@Slf4j
public class DrlController {

    @Autowired
    private DRLSRV drlSRV;

    @Autowired
    private DrlCFG drlCFG;

    /**
     *
     * @param version        version
     * @param chunk          chunk
     * @return               DrlResponseDTO
     */
    @GetMapping("")
    @Operation(summary = "Get drl list",description = "Service to retrive a certificate revocation list.")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = DrlResponseDTO.class)))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List found"),@ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404",description = "Record not found"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error"),})
    public DrlResponseDTO getDRL(final @RequestParam(required = false, name = "version") Long version,@RequestParam(required = false, name = "chunk") Integer chunk,HttpServletRequest request) {
        log.info("Chiamata download con version :" + version + " e chunk : " + chunk);
        
        Validation.isValidVersion(version);
        Validation.isValidChunk(chunk);
        if (chunk == null || chunk==0) {
            chunk = 1;
        }
        
        DRLDTO drlDTO = drlSRV.getDRL(version,chunk.longValue(), false);
        return buildOutputDownloadDrl(drlDTO, chunk, version);
    }
     
    /**
     *
     * @param version        version
     * @param chunk          chunk
     * @return               DrlResponseDTO
     */
    @GetMapping("/check")
    @Operation(summary = "Inspection call for drl",description = "Service to let Inspection call for drl.")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = DrlResponseDTO.class)))
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List found"),@ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404",description = "Record not found"),
            @ApiResponse(responseCode = "500",description = "Internal Server Error"),})
    public DrlResponseDTO checkDRL(final @RequestParam(required = false, name = "version") Long version,@RequestParam(required = false, name = "chunk") Integer chunk,HttpServletRequest request) {
        log.info("Chiamata check con version :" + version + " e chunk : " + chunk);
        Validation.isValidVersion(version); 
        Validation.isValidChunk(chunk);
        if (chunk == null || chunk==0) {
            chunk = 1;
        }
        
        DRLDTO drlDTO = drlSRV.getDRL(version,chunk.longValue(), true);
        return buildOutputCheckDrl(drlDTO, chunk, version);
    }

    private DrlResponseDTO buildOutputDownloadDrl(DRLDTO drlDTO,Integer chunk,Long version) {
        log.info("START - Build output DRL");
        DrlResponseDTO output = null;
 
        Long sizeSingleUcviHashed = 51L;
        Long sizeSingleChunkByte = drlCFG.getNumMaxItemInChunk()*sizeSingleUcviHashed; 
        
        if (drlDTO.getDrl() != null) {
            output = buildOutputDownloadSnap(drlDTO, chunk, version,sizeSingleChunkByte);
        } else if (drlDTO.getDeltaVers() != null) {
            output = buildOutputDownloadDelta(drlDTO, chunk,sizeSingleChunkByte);
        }
        log.info("END - Build output DRL");
        return output;
    }
   
    private DrlResponseDTO buildOutputCheckDrl(DRLDTO drlDTO,Integer chunk,Long version) {
        log.info("START - Build output DRL");
        DrlResponseDTO output = null;

        Long sizeSingleUcviHashed = 51L;
        Long sizeSingleChunkByte = drlCFG.getNumMaxItemInChunk()*sizeSingleUcviHashed; 
        
        if (drlDTO.getDrl() != null) {
            output = buildOutputCheckSnap(drlDTO, chunk, version,sizeSingleChunkByte);
        } else if (drlDTO.getDeltaVers() != null) {
            output = buildOutputCheckDelta(drlDTO, chunk,sizeSingleChunkByte);
        }
        log.info("END - Build output DRL");
        return output;
    }

    private DrlResponseDTO buildOutputCheckDelta(DRLDTO drlDTO,Integer chunk,Long sizeSingleChunkByte) {
        log.info("START - Build output DELTA");
        DrlResponseDTO output = new DrlResponseDTO();
        output.setId(drlDTO.getDeltaVers().getDeltaETY().getId());
        output.setTotalNumberUCVI(drlDTO.getDeltaVers().getDeltaETY().getNumTotaleUCVI());
        output.setFromVersion(drlDTO.getDeltaVers().getDeltaETY().getFromVersion());
        output.setVersion(drlDTO.getDeltaVers().getDeltaETY().getToVersion());
        output.setSizeSingleChunkInByte(sizeSingleChunkByte);
          
        output.setChunk(chunk); 
           
        Integer numTotaleUCVI = drlDTO.getDeltaVers().getDeltaETY().getNumTotaleAdd()+drlDTO.getDeltaVers().getDeltaETY().getNumTotaleRemove();
        
        output.setNumDiAdd(drlDTO.getDeltaVers().getDeltaETY().getNumTotaleAdd());
        output.setNumDiDelete(drlDTO.getDeltaVers().getDeltaETY().getNumTotaleRemove());

        Double lastChunk = Math.ceil(numTotaleUCVI.floatValue()/drlCFG.getNumMaxItemInChunk().floatValue());
        output.setTotalChunk(lastChunk.intValue());
        
        Integer chunkRestanti = lastChunk.intValue() - chunk;
        Long totalSizeInByte = (chunkRestanti+1)*sizeSingleChunkByte;
        output.setTotalSizeInByte(totalSizeInByte);  
                
        log.info("END - Build output DELTA");
        return output;
    }
    
    /**
     *
     * @param drlDTO                  drlDTO
     * @param chunk                   chunk
     * @param isIspettiva             isIspettiva
     * @return                        DrlResponseDTO
     */
    private DrlResponseDTO buildOutputCheckSnap(DRLDTO drlDTO,Integer chunk,Long version, Long sizeSingleChunkByte) {
        log.info("START - Build output SNAP"); 
        DrlResponseDTO output = DrlResponseDTO.builder().
                id(drlDTO.getDrl().getSnapshot().getId()).
                totalNumberUCVI(drlDTO.getDrl().getSnapshot().getNumTotaleUCVI()).
                sizeSingleChunkInByte(sizeSingleChunkByte).
                numDiAdd(0).
                totalSizeInByte(0L).
                version(drlDTO.getDrl().getSnapshot().getVersion()).
                totalChunk(0).
                chunk(0).
                build();

        if (version != null) {
            output.setFromVersion(version);
        }

        output.setChunk(chunk); 
        
        if(drlDTO.getDrl().getSnapshot().getVersion()!=version) {
        	output.setNumDiAdd(drlDTO.getDrl().getSnapshot().getNumTotaleUCVI());
        }
        
        Double lastChunk = Math.ceil(drlDTO.getDrl().getSnapshot().getNumTotaleUCVI().floatValue()/drlCFG.getNumMaxItemInChunk().floatValue());
        output.setTotalChunk(lastChunk.intValue());  


        Integer chunkRestanti = lastChunk.intValue() - chunk;
        Long totalSizeInByte = (chunkRestanti+1)*sizeSingleChunkByte;
        output.setTotalSizeInByte(totalSizeInByte);  

        log.info("END - Build output SNAP");
        return output;
    }
    
    /**
    * @param drlDTO                  drlDTO
    * @param chunk                   chunk
    * @param isIspettiva             isIspettiva
    * @return                        DrlResponseDTO
    */
    private DrlResponseDTO buildOutputDownloadSnap(DRLDTO drlDTO, Integer chunk, Long version, Long sizeSingleChunkByte) {
        log.info("START - Build output SNAP");
        DrlResponseDTO output = new DrlResponseDTO();
        output.setId(drlDTO.getDrl().getSnapshot().getId());
        output.setTotalNumberUCVI(drlDTO.getDrl().getSnapshot().getNumTotaleUCVI());
        output.setVersion(drlDTO.getDrl().getSnapshot().getVersion());
        if (version != null) {
            output.setFromVersion(version);
        }
         
        output.setSizeSingleChunkInByte(sizeSingleChunkByte);
 
        output.setCreationDate(drlDTO.getDrl().getSnapshot().getCreationDate());
        output.setRevokedUcvi(new ArrayList<>());
        

        if (drlDTO.getDrl().getRevokedUcvi() != null && !drlDTO.getDrl().getRevokedUcvi().isEmpty()) { 
            output.setChunk(chunk); 
            Double lastChunk = Math.ceil(drlDTO.getDrl().getSnapshot().getNumTotaleUCVI().floatValue()/drlCFG.getNumMaxItemInChunk().floatValue());
            output.setLastChunk(lastChunk.intValue());
            output.setRevokedUcvi(drlDTO.getDrl().getRevokedUcvi());
            output.setFirstElementInChunk(drlDTO.getDrl().getRevokedUcvi().get(0));
            output.setLastElementInChunk(drlDTO.getDrl().getRevokedUcvi().get(drlDTO.getDrl().getRevokedUcvi().size() - 1));
        }
        log.info("END - Build output SNAP");
        return output;
    }
    
    private DrlResponseDTO buildOutputDownloadDelta(DRLDTO drlDTO,Integer chunk,Long sizeSingleChunkByte) {
        log.info("START - Build output DELTA");
        
        List<String> insert = new ArrayList<>();
        List<String> delete = new ArrayList<>();
        insert.addAll(drlDTO.getDeltaVers().getDelta().getInsertions());
        delete.addAll(drlDTO.getDeltaVers().getDelta().getDeletions());
      
        DeltaResponseDTO respDelt = new DeltaResponseDTO();
        respDelt.setInsertions(insert);
        respDelt.setDeletions(delete);
        
        Integer numTotaleUCVI = drlDTO.getDeltaVers().getDeltaETY().getNumTotaleAdd()+drlDTO.getDeltaVers().getDeltaETY().getNumTotaleRemove();
        Double lastChunk = Math.ceil(numTotaleUCVI.floatValue()/drlCFG.getNumMaxItemInChunk().floatValue());
        
        DrlResponseDTO output =  DrlResponseDTO.builder().
                id(drlDTO.getDeltaVers().getDeltaETY().getId()).
                totalNumberUCVI(drlDTO.getDeltaVers().getDeltaETY().getNumTotaleUCVI()).
                fromVersion(drlDTO.getDeltaVers().getDeltaETY().getFromVersion()).
                version(drlDTO.getDeltaVers().getDeltaETY().getToVersion()).
                chunk(chunk).
                lastChunk(lastChunk.intValue()).
                delta(respDelt).
                sizeSingleChunkInByte(sizeSingleChunkByte).
                build(); 
        
        log.info("END - Build output DELTA");
        return output;
    }
     
}
