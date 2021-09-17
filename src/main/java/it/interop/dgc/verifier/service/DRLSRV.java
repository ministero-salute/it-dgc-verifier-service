package it.interop.dgc.verifier.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.interop.dgc.verifier.entity.DeltaETY;
import it.interop.dgc.verifier.entity.SnapshotETY;
import it.interop.dgc.verifier.entity.dto.DRLDTO;
import it.interop.dgc.verifier.exceptions.DgcaBusinessRulesResponseException;
import it.interop.dgc.verifier.repository.DeltaRepository;
import it.interop.dgc.verifier.repository.SnapshotRepository;

/**
 * 
 * @author CPIERASC
 *
 *	Servizio gestione CRL - Certificate Revocation List.
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
        SnapshotETY crl = null;
        Long lastSnap = snapRepo.getLastVersion();
        DeltaETY delta = deltaRepo.getByVersions(version, lastSnap);
        
    
        if(lastSnap==null) {
            throw new DgcaBusinessRulesResponseException(HttpStatus.BAD_REQUEST,"0x004","Nessuno snapshot trovato sul db",null,null);  
        }
        
        if(version!=null && version>lastSnap) {
            throw new DgcaBusinessRulesResponseException(HttpStatus.BAD_REQUEST,"0x004","Versione maggiore rispetto all'ultima snapshot disponibile",null,null);  
        }
         
        if(lastSnap.equals(version)) {
            crl = snapRepo.getSnapWithoutUCVI(version);
        } else if (delta == null) {
            crl = snapRepo.getLastVersionWithContent();
        }
        
        if(crl==null && delta==null) {
            throw new DgcaBusinessRulesResponseException(HttpStatus.BAD_REQUEST,"0x004","Nessun record snap o delta trovato, controllare i dati passati",null,null);  
        }
        
        return new DRLDTO(crl, delta);
    }
    
	
 
 
}