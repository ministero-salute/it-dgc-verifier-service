package it.interop.dgc.verifier.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import it.interop.dgc.verifier.entity.SnapshotETY;
import it.interop.dgc.verifier.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 *	@author CPIERASC
 *
 */
@Repository
@Slf4j
public class SnapshotRepository {
  
	/**
	 * Mongo template.
	 */
	@Autowired
	private MongoTemplate mongoTemplate;

	public SnapshotETY getLastVersionWithContent() {
		final Query query = new Query();
		query.addCriteria(Criteria.where("flag_archived").is(false));
		query.with(Sort.by(Sort.Direction.DESC, "version"));
		query.limit(1);    
		return mongoTemplate.findOne(query, SnapshotETY.class);
	}
	
    public Long getLastVersion() {
	    Long version = null;
        final Query query = new Query();
        query.addCriteria(Criteria.where("flag_archived").is(false));
        query.with(Sort.by(Sort.Direction.DESC, "version"));
        query.limit(1); 
        query.fields().include("version");
        
        SnapshotETY snap = mongoTemplate.findOne(query, SnapshotETY.class);
        if(snap!=null) {
            version = snap.getVersion();
        }
        return version;
    }
	 
	public SnapshotETY getVersion(final Long version) {
		SnapshotETY output = null;
		try {
			final Query query = new Query();
			query.addCriteria(Criteria.where("version").is(version).and("flag_archived").is(false));
			List<SnapshotETY> listOut = mongoTemplate.find(query, SnapshotETY.class);
			if (!listOut.isEmpty()) {
				output = listOut.get(0);
			}
		} catch (final Exception ex) {
			log.error("Errore durante il recupero dello snapshot per versione", ex);
			throw new BusinessException("Errore durante il recupero dello snapshot per versione", ex);
		}
		return output;				 
	}

	public List<SnapshotETY> getPrevious(final Long version) {
		List<SnapshotETY> output = null;
		try {
			final Query query = new Query();
			query.addCriteria(Criteria.where("version").lt(version).and("flag_archived").is(false));
			output = mongoTemplate.find(query, SnapshotETY.class);
		} catch (final Exception ex) {
		    log.error("Errore durante il recupero degli snapshot precedenti", ex);
			throw new BusinessException("Errore durante il recupero degli snapshot precedenti", ex);
		}
		return output;				 
	}

	public SnapshotETY save(final List<String> revokedUcvi) {
		SnapshotETY lastSnap = getLastVersionWithContent();
		Long version = 0L;
		if (lastSnap != null) {
			version = lastSnap.getVersion() + 1;
		}
		SnapshotETY output = new SnapshotETY(null, version, revokedUcvi, new Date());
		try {
			output = mongoTemplate.save(output);	
		} catch (final Exception ex) {
		    log.error("Errore durante il salvataggio di uno snapshot", ex);
			throw new BusinessException("Errore durante il salvataggio di uno snapshot", ex);
		}
		return output;
		
	}
 
	
    public SnapshotETY getSnapWithoutUCVI(final Long version) {
        SnapshotETY output = null;
        try {
            final Query query = new Query();
            query.addCriteria(Criteria.where("version").is(version).and("flag_archived").is(false));
            
            query.fields().include("_id");
            query.fields().include("version");
            query.fields().include("creation_date");
            query.fields().include("flag_archived");
            
            List<SnapshotETY> listOut = mongoTemplate.find(query, SnapshotETY.class);
            if (!listOut.isEmpty()) {
                output = listOut.get(0);
            }
        } catch (final Exception ex) {
            log.error("Errore durante il recupero dello snapshot per versione", ex);
            throw new BusinessException("Errore durante il recupero dello snapshot per versione", ex);
        }
        return output;               
    }
   
 
 
}