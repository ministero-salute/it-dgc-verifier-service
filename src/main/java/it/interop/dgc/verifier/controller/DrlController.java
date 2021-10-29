package it.interop.dgc.verifier.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import it.interop.dgc.verifier.exceptions.DgcaBusinessRulesResponseException;
import it.interop.dgc.verifier.service.DRLSRV;
import it.interop.dgc.verifier.utils.ChunkUtility;
import it.interop.dgc.verifier.utils.StringUtility;
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
    private DrlCFG crlCFG;

    /**
     *
     * @param version        version
     * @param chunk          chunk
     * @return               DrlResponseDTO
     */
    @GetMapping("")
    @Operation(
        summary = "Get drl list",
        description = "Service to retrive a certificate revocation list."
    )
    @ApiResponse(
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = DrlResponseDTO.class)
        )
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "List found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(
                responseCode = "404",
                description = "Record not found"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal Server Error"
            ),
        }
    )
    public DrlResponseDTO getDRL(
        final @RequestParam(required = false, name = "version") Long version,
        @RequestParam(required = false, name = "chunk") Integer chunk,
        HttpServletRequest request
    ) {
        Validation.isValidVersion(version);
        DRLDTO drlDTO = drlSRV.getDRL(version, false);
        return buildOutputDrl(drlDTO, chunk, false, version);
    }

    /**
     *
     * @param version        version
     * @param chunk          chunk
     * @return               DrlResponseDTO
     */
    @GetMapping("/check")
    @Operation(
        summary = "Inspection call for drl",
        description = "Service to let Inspection call for drl."
    )
    @ApiResponse(
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = DrlResponseDTO.class)
        )
    )
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "List found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(
                responseCode = "404",
                description = "Record not found"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal Server Error"
            ),
        }
    )
    public DrlResponseDTO checkDRL(
        final @RequestParam(required = false, name = "version") Long version,
        @RequestParam(required = false, name = "chunk") Integer chunk,
        HttpServletRequest request
    ) {
        Validation.isValidVersion(version);
        DRLDTO drlDTO = drlSRV.getDRL(version, true);
        return buildOutputDrl(drlDTO, chunk, true, version);
    }

    private DrlResponseDTO buildOutputDrl(
        DRLDTO drlDTO,
        Integer chunk,
        boolean isIspettiva,
        Long version
    ) {
        DrlResponseDTO output = null;

        if (chunk == null) {
            chunk = 1;
        }

        if (drlDTO.getDrl() != null) {
            output = buildOutputSnap(drlDTO, chunk, isIspettiva, version);
        } else if (drlDTO.getDeltaVers() != null) {
            output = buildOutputDelta(drlDTO, chunk, isIspettiva);
        }

        return output;
    }

    private DrlResponseDTO buildOutputDelta(
        DRLDTO drlDTO,
        Integer chunk,
        boolean isIspettiva
    ) {
        DrlResponseDTO output = new DrlResponseDTO();
        output.setId(drlDTO.getDeltaVers().getDeltaETY().getId());
        output.setTotalNumberUCVI(drlDTO.getDeltaVers().getDeltaETY().getNumTotaleUCVI());
        output.setFromVersion(drlDTO.getDeltaVers().getDeltaETY().getFromVersion());
        output.setVersion(drlDTO.getDeltaVers().getDeltaETY().getToVersion());

        List<String> totalChunk = new ArrayList<>();
        totalChunk.addAll(drlDTO.getDeltaVers().getDelta().getInsertions());
        totalChunk.addAll(drlDTO.getDeltaVers().getDelta().getDeletions());

        ChunkDTO chunkDTO = getListChunk(totalChunk, chunk);

        output.setChunk(chunkDTO.getSizeTotaliDeiChunk());
        List<String> insert = new ArrayList<>();
        List<String> delete = new ArrayList<>();
        for (String singleChunk : chunkDTO.getListChunk()) {
            if (
                drlDTO
                    .getDeltaVers()
                    .getDelta()
                    .getInsertions()
                    .contains(singleChunk)
            ) {
                insert.add(singleChunk);
            } else if (
                drlDTO
                    .getDeltaVers()
                    .getDelta()
                    .getDeletions()
                    .contains(singleChunk)
            ) {
                delete.add(singleChunk);
            }
        }
        DeltaResponseDTO respDelt = new DeltaResponseDTO();
        respDelt.setInsertions(insert);
        respDelt.setDeletions(delete);

        if (isIspettiva) {
            output.setNumDiAdd(respDelt.getInsertions().size());
            output.setNumDiDelete(respDelt.getDeletions().size());

            output.setTotalSizeInByte(chunkDTO.getSizeTotalInByte());
            output.setTotalChunk(chunkDTO.getSizeTotaliDeiChunk());
        } else {
            output.setChunk(chunk);
            output.setLastChunk(chunkDTO.getSizeTotaliDeiChunk());
            output.setDelta(respDelt);
        }
        output.setSizeSingleChunkInByte(chunkDTO.getSizeSingleChunkInByte());
        return output;
    }

    /**
     *
     * @param drlDTO                  drlDTO
     * @param chunk                   chunk
     * @param isIspettiva             isIspettiva
     * @return                        DrlResponseDTO
     */
    private DrlResponseDTO buildOutputSnap(
        DRLDTO drlDTO,
        Integer chunk,
        boolean isIspettiva,
        Long version
    ) {
        DrlResponseDTO output = new DrlResponseDTO();
        output.setId(drlDTO.getDrl().getSnapshot().getId());
        output.setTotalNumberUCVI(drlDTO.getDrl().getSnapshot().getNumTotaleUCVI());
        if (isIspettiva) {
            output.setNumDiAdd(0);
            output.setTotalSizeInByte(0L);
            output.setSizeSingleChunkInByte(0L);
            output.setVersion(drlDTO.getDrl().getSnapshot().getVersion());
            output.setTotalChunk(0);
            output.setChunk(0);
        } else {
            output.setCreationDate(drlDTO.getDrl().getSnapshot().getCreationDate());
            output.setRevokedUcvi(new ArrayList<>());
            output.setVersion(drlDTO.getDrl().getSnapshot().getVersion());
        }

        if (version != null) {
            output.setFromVersion(version);
        }

        ChunkDTO chunkDTO = null;
        if (
            drlDTO.getDrl().getRevokedUcvi() != null &&
            !drlDTO.getDrl().getRevokedUcvi().isEmpty()
        ) {
            chunkDTO = getListChunk(drlDTO.getDrl().getRevokedUcvi(), chunk);
            output.setChunk(chunk);

            if (isIspettiva) {
                output.setNumDiAdd(drlDTO.getDrl().getRevokedUcvi().size());
                output.setTotalSizeInByte(chunkDTO.getSizeTotalInByte()); 
                output.setTotalChunk(chunkDTO.getSizeTotaliDeiChunk());
            } else {
                output.setLastChunk(chunkDTO.getSizeTotaliDeiChunk());
                output.setRevokedUcvi(chunkDTO.getListChunk());
                output.setFirstElementInChunk(chunkDTO.getListChunk().get(0));
                output.setLastElementInChunk(
                    chunkDTO
                        .getListChunk()
                        .get(chunkDTO.getListChunk().size() - 1)
                );
            }
            output.setSizeSingleChunkInByte(
                chunkDTO.getSizeSingleChunkInByte()
            );
        }
        return output;
    }

    private ChunkDTO getListChunk(
        List<String> listCalcolaChunk,
        Integer chunk
    ) {
        List<String> chunkList = new ArrayList<>();
        Long sizeTotalInByte = 0L;
        Long sizeSingleChunkByte = 0L;
        Integer numTotaleChunk = 0;

        Stream<List<String>> out = ChunkUtility.calcolaChunk(
            listCalcolaChunk,
            crlCFG.getNumMaxItemInChunk()
        );

        try {
            Object[] vettChunk = out.toArray();
            if (chunk > vettChunk.length || chunk <= 0) {
                throw new DgcaBusinessRulesResponseException(
                    HttpStatus.BAD_REQUEST,
                    "0x004",
                    "Chunk not existing",
                    null,
                    null
                );
            }

            List<Object> listAllChunk = Arrays.asList(vettChunk);

            numTotaleChunk = listAllChunk.size();
            if (chunk != null) {
                for (int i = chunk; i < listAllChunk.size(); i++) {
                    List<String> listChunk = new ArrayList<>();
                    listChunk.addAll((List<String>) listAllChunk.get(i));
                    sizeTotalInByte += ChunkUtility.getBytesFromObj(listChunk);
                }
            }

            Object vettChunkSingle = vettChunk[chunk - 1];

            chunkList.addAll((List<String>) vettChunkSingle);

            Long sizeSingleUcviHashed = 51L;
            sizeSingleChunkByte = crlCFG.getNumMaxItemInChunk()*sizeSingleUcviHashed;
//            sizeSingleChunkByte =
//                ChunkUtility.getBytesFromObj(sizeSingle);
        } catch (DgcaBusinessRulesResponseException ex) {
            log.error("Chunk not existing", ex);
            throw new DgcaBusinessRulesResponseException(
                HttpStatus.BAD_REQUEST,
                "0x004",
                "Chunk not existing",
                null,
                null
            );
        } catch (Exception ex) {
            log.error("Error in the calculation of chunks : ", ex);
            throw new BusinessException("Error in the calculation of chunks : " + ex);
        }

        return new ChunkDTO(
            chunkList,
            numTotaleChunk,
            sizeTotalInByte,
            sizeSingleChunkByte
        );
    }
    
     
}
