package it.interop.dgc.verifier.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.interop.dgc.verifier.config.EuropeanDrlCFG;
import it.interop.dgc.verifier.entity.dto.EuropeanDRLDTO;
import it.interop.dgc.verifier.entity.dto.EuropeanDeltaDTO;
import it.interop.dgc.verifier.entity.dto.EuropeanSnapshotDTO;
import it.interop.dgc.verifier.exceptions.DgcaBusinessRulesResponseException;
import it.interop.dgc.verifier.repository.EuDeltaRepository;
import it.interop.dgc.verifier.repository.EuSnapshotRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EuDRLSRV implements Serializable {

    /**
     * Serial verison UID.
     */
    private static final long serialVersionUID = -6489834121456735242L;

    /**
     * Pass repo.
     */
    @Autowired
    private EuSnapshotRepository euSnapRepo;

    /**
     * Delta repo.
     */
    @Autowired
    private EuDeltaRepository euDeltaRepo;
 
    /**
     * Drl CFG.
     */
    @Autowired
    private EuropeanDrlCFG drlCFG;
    
    public EuropeanDRLDTO getDRL(Long version, Long chunk, boolean isIspettiva) {
        EuropeanSnapshotDTO drl = null;
        Long lastSnap = euSnapRepo.getLastVersion();

        if(lastSnap==null) {
            throw new DgcaBusinessRulesResponseException(HttpStatus.BAD_REQUEST,"0x004","No snapshot found on the db",null,null);  
        }

        if(version!=null && version>lastSnap) {
            throw new DgcaBusinessRulesResponseException(HttpStatus.BAD_REQUEST,"0x004","Version greater than the latest available snapshot",null,null);  
        }

        if(lastSnap.equals(version)) {
            log.info("GET SNAP WITHOUT IDS EU");
            drl = euSnapRepo.getSnapWithoutContent(version);
            checkChunk(chunk, drl);
        }

        EuropeanDeltaDTO delta = null;
        if(drl==null) {
            if(!isIspettiva) {
                delta = euDeltaRepo.getByVersions(version, lastSnap,chunk); 
            } else {
                delta = euDeltaRepo.getByVersionsWithoutContent(version, lastSnap,chunk); 
            }
            if(delta!=null) {
                checkChunk(chunk,delta);
            }
             

            log.info("Last snap: {}", lastSnap );
            log.info("Delta: {}", delta);

            if (delta == null) {
                if(!isIspettiva) {
                    log.info("GET LAST VERSION WITH CONTENT");
                    drl = euSnapRepo.getLastVersionWithContent(chunk);
                } else {
                    log.info("GET LAST VERSION WITHOUTH CONTENT");
                    drl = euSnapRepo.getSnapWithoutContent(lastSnap);
                }

                checkChunk(chunk, drl);
            }
        }
        if(drl==null && delta==null) {
            throw new DgcaBusinessRulesResponseException(HttpStatus.BAD_REQUEST,"0x004","No snap or delta records found, check past data",null,null);  
        }

        return new EuropeanDRLDTO(drl, delta);
    }

    private void checkChunk(Long chunk, EuropeanSnapshotDTO drl) {
        
        Double lastChunk = Math.ceil(drl.getSnapshot().getNumberRevocation().floatValue() / drlCFG.getNumMaxItemInChunk().floatValue());
        
        if(lastChunk.equals(0D) && chunk!=null && chunk.intValue()>1) {
            throw new DgcaBusinessRulesResponseException(HttpStatus.BAD_REQUEST,"0x004","Chunk non valido",null,null);
        }  
        
        if(!lastChunk.equals(0D) && chunk.intValue() > lastChunk.intValue()) {
            throw new DgcaBusinessRulesResponseException(HttpStatus.BAD_REQUEST,"0x004","Chunk non valido",null,null);  

        }
    }
    
    private void checkChunk(Long chunk, EuropeanDeltaDTO drl) {
        Integer numTotalIds = drl.getDeltaETY().getNumTotaleAdd()+drl.getDeltaETY().getNumTotaleRemove();
        Double lastChunk = Math.ceil(numTotalIds.floatValue()/drlCFG.getNumMaxItemInChunk().floatValue());
        
        if(lastChunk.equals(0D) && chunk!=null && chunk.intValue()>1) {
            throw new DgcaBusinessRulesResponseException(HttpStatus.BAD_REQUEST,"0x004","Chunk non valido",null,null);
        }  
        
        if(!lastChunk.equals(0D) && chunk.intValue() > lastChunk.intValue()) {
            throw new DgcaBusinessRulesResponseException(HttpStatus.BAD_REQUEST,"0x004","Chunk non valido",null,null);  

        }
    }
    
}
