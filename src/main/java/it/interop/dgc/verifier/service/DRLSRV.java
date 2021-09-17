package it.interop.dgc.verifier.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.interop.dgc.verifier.entity.DeltaETY;
import it.interop.dgc.verifier.entity.SnapshotETY;
import it.interop.dgc.verifier.entity.dto.DRLDTO;
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