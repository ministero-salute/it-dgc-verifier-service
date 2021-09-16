package it.interop.dgc.verifier.service;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.interop.dgc.verifier.entity.DeltaETY;
import it.interop.dgc.verifier.entity.MemoriaTempETY;
import it.interop.dgc.verifier.entity.SnapshotETY;
import it.interop.dgc.verifier.entity.dto.DRLDTO;
import it.interop.dgc.verifier.enums.NotifyTypeEnum;
import it.interop.dgc.verifier.enums.OperationTypeEnum;
import it.interop.dgc.verifier.exceptions.BusinessException;
import it.interop.dgc.verifier.repository.DeltaRepository;
import it.interop.dgc.verifier.repository.MemoriaTempRepo;
import it.interop.dgc.verifier.repository.SnapshotRepository;
import it.interop.dgc.verifier.utils.StringUtility;

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
     * Logger.
     */
//    private static final ALogger LOGGER = ALogger.getLogger(DRLSRV.class);
    
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
	
	@Autowired
	private MemoriaTempRepo memoriaTempRepo;
 
	
	public void saveSingleRevokeUCVI(final String revokedUCVI,final NotifyTypeEnum tipoNotifica) {
	    try {
//	        LOGGER.info("START SALVATAGGIO IN MEMORIA_TEMPORANEA COLLECTION");
	        MemoriaTempETY temp = memoriaTempRepo.findByRevokedUCVI(revokedUCVI);
	        if(temp==null) {
	            if(NotifyTypeEnum.REVOCA.equals(tipoNotifica)) {
	                temp = new MemoriaTempETY(null,revokedUCVI,StringUtility.encodeSHA256(revokedUCVI) ,new Date(),OperationTypeEnum.ADD.getType(),false);
	            } else if(NotifyTypeEnum.UNDO_REVOCA.equals(tipoNotifica)) {
	                temp = new MemoriaTempETY(null,revokedUCVI,StringUtility.encodeSHA256(revokedUCVI),new Date(),OperationTypeEnum.REMOVE.getType(),false);
	            }
	            
	            memoriaTempRepo.saveRevokedUcvi(temp);
	             
	        } else {
	            if(NotifyTypeEnum.REVOCA.equals(tipoNotifica)) {
	                if(OperationTypeEnum.REMOVE.getType().equals(temp.getOperazione())) {
	                    memoriaTempRepo.delete(temp.getId());
	                } 
	            } else if(NotifyTypeEnum.UNDO_REVOCA.equals(tipoNotifica)) {
	                if(OperationTypeEnum.ADD.getType().equals(temp.getOperazione())) {
	                    memoriaTempRepo.delete(temp.getId());
	                } 
                } 
	        }
//	        LOGGER.info("END SALVATAGGIO IN MEMORIA_TEMPORANEA COLLECTION");	        
	    } catch(Exception ex) {
//	        LOGGER.error("Errore durante il salvataggio del singolo ucvi revocato : ", ex);
	        throw new BusinessException("Errore durante il salvataggio del singolo ucvi revocato : "+ ex);
	        
	    }
	    
	}
	
	
	public DRLDTO getDRL(Long version, boolean isIspettiva) {
		SnapshotETY crl = null;
		Long lastSnap = snapRepo.getLastVersion();
		DeltaETY delta = deltaRepo.getByVersions(version, lastSnap);
		
		if(!isIspettiva && lastSnap.equals(version)) {
		    crl = snapRepo.getSnapWithoutUCVI(version);
		} else if (version==null || (isIspettiva && delta == null)) {
			crl = snapRepo.getLastVersionWithContent();
		}
		
		return new DRLDTO(crl, delta);
	}
	
 
 
}