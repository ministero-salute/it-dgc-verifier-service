package it.interop.dgc.verifier.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.interop.dgc.verifier.entity.DeltaETY;
import it.interop.dgc.verifier.entity.SnapshotETY;
import it.interop.dgc.verifier.entity.dto.DRLDTO;
import it.interop.dgc.verifier.entity.dto.SnapshotDTO;
import it.interop.dgc.verifier.exceptions.DgcaBusinessRulesResponseException;
import it.interop.dgc.verifier.repository.DeltaRepository;
import it.interop.dgc.verifier.repository.SnapshotRepository;

/**
 *
 * @author CPIERASC
 *
 */
@Service
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

    public DRLDTO getDRL(Long version, boolean isIspettiva) {
        SnapshotDTO drl = null;
        Long lastSnap = snapRepo.getLastVersion();
        DeltaETY delta = deltaRepo.getByVersions(version, lastSnap);
        
    
        if(lastSnap==null) {
            throw new DgcaBusinessRulesResponseException(HttpStatus.BAD_REQUEST,"0x004","No snapshot found on the db",null,null);  
        }
        
        if(version!=null && version>lastSnap) {
            throw new DgcaBusinessRulesResponseException(HttpStatus.BAD_REQUEST,"0x004","Version greater than the latest available snapshot",null,null);  
        }
         
        if(lastSnap.equals(version)) {
            drl = snapRepo.getSnapWithoutUCVI(version);
        } else if (delta == null) {
            drl = snapRepo.getLastVersionWithContent();
        }
        
        if(drl==null && delta==null) {
            throw new DgcaBusinessRulesResponseException(HttpStatus.BAD_REQUEST,"0x004","No snap or delta records found, check past data",null,null);  
        }
        
        return new DRLDTO(drl, delta);
    }
}
