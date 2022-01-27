package it.interop.dgc.verifier.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.interop.dgc.verifier.config.DrlCFG;
import it.interop.dgc.verifier.entity.dto.DRLDTO;
import it.interop.dgc.verifier.entity.dto.DeltaDTO;
import it.interop.dgc.verifier.entity.dto.SnapshotDTO;
import it.interop.dgc.verifier.exceptions.DgcaBusinessRulesResponseException;
import it.interop.dgc.verifier.repository.DeltaRepository;
import it.interop.dgc.verifier.repository.SnapshotRepository;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author CPIERASC
 *
 */
@Service
@Slf4j
public class DRLSRV implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6489834121456735245L;

    /**
     * Pass repo.
     */
    @Autowired
    private SnapshotRepository snapRepo;

    /**
     * Delta repo.
     */
    @Autowired
    private DeltaRepository deltaRepo;
 
    /**
     * Drl CFG.
     */
    @Autowired
    private DrlCFG drlCFG;
    
    public DRLDTO getDRL(Long version, Long chunk, boolean isIspettiva) {
        SnapshotDTO drl = null;
        Long lastSnap = snapRepo.getLastVersion();

        if(lastSnap==null) {
            throw new DgcaBusinessRulesResponseException(HttpStatus.BAD_REQUEST,"0x004","No snapshot found on the db",null,null);  
        }

        if(version!=null && version>lastSnap) {
            throw new DgcaBusinessRulesResponseException(HttpStatus.BAD_REQUEST,"0x004","Version greater than the latest available snapshot",null,null);  
        }

        if(lastSnap.equals(version)) {
            log.info("GET SNAP WITHOUT UCVI");
            drl = snapRepo.getSnapWithoutUCVI(version);
            checkChunk(chunk, drl);
        }

        DeltaDTO delta = null;
        if(drl==null) {
            if(!isIspettiva) {
                delta = deltaRepo.getByVersions(version, lastSnap,chunk); 
            } else {
                delta = deltaRepo.getByVersionsWithoutContent(version, lastSnap,chunk); 
            }
            if(delta!=null) {
                checkChunk(chunk,delta);
            }
             

            log.info("Last snap : " + lastSnap );
            log.info("Delta : " + delta);

            if (delta == null) {
                if(!isIspettiva) {
                    log.info("GET LAST VERSION WITH CONTENT");
                    drl = snapRepo.getLastVersionWithContent(chunk);
                } else {
                    log.info("GET LAST VERSION WITHOUTH CONTENT");
                    drl = snapRepo.getSnapWithoutUCVI(lastSnap);
                }

                checkChunk(chunk, drl);
            }
        }
        if(drl==null && delta==null) {
            throw new DgcaBusinessRulesResponseException(HttpStatus.BAD_REQUEST,"0x004","No snap or delta records found, check past data",null,null);  
        }

        return new DRLDTO(drl, delta);
    }

    private void checkChunk(Long chunk, SnapshotDTO drl) {
        
        Double lastChunk = Math.ceil(drl.getSnapshot().getNumTotaleUCVI().floatValue()/drlCFG.getNumMaxItemInChunk().floatValue());
        
        if(lastChunk.equals(0D) && chunk!=null && chunk.intValue()>1) {
            throw new DgcaBusinessRulesResponseException(HttpStatus.BAD_REQUEST,"0x004","Chunk non valido",null,null);
        }  
        
        if(!lastChunk.equals(0D) && chunk.intValue() > lastChunk.intValue()) {
            throw new DgcaBusinessRulesResponseException(HttpStatus.BAD_REQUEST,"0x004","Chunk non valido",null,null);  

        }
    }
    
    private void checkChunk(Long chunk, DeltaDTO drl) {
        Integer numTotaleUCVI = drl.getDeltaETY().getNumTotaleAdd()+drl.getDeltaETY().getNumTotaleRemove();
        Double lastChunk = Math.ceil(numTotaleUCVI.floatValue()/drlCFG.getNumMaxItemInChunk().floatValue());
        
        if(lastChunk.equals(0D) && chunk!=null && chunk.intValue()>1) {
            throw new DgcaBusinessRulesResponseException(HttpStatus.BAD_REQUEST,"0x004","Chunk non valido",null,null);
        }  
        
        if(!lastChunk.equals(0D) && chunk.intValue() > lastChunk.intValue()) {
            throw new DgcaBusinessRulesResponseException(HttpStatus.BAD_REQUEST,"0x004","Chunk non valido",null,null);  

        }
    }
    
    
}
