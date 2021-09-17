package it.interop.dgc.verifier.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import it.interop.dgc.verifier.entity.DeltaETY;
import it.interop.dgc.verifier.exceptions.BusinessException;
import it.interop.dgc.verifier.utils.Validation;
import lombok.extern.slf4j.Slf4j;



/**
 * 
 *	@author vincenzoingenito
 *
 */
@Repository
@Slf4j
public class DeltaRepository {


	/**
	 * Mongo template.
	 */
	@Autowired
	private MongoTemplate mongoTemplate;

	public DeltaETY getByVersions(final Long versionFrom, final Long versionTo) {
		DeltaETY output = null;
		try {
			final Query query = new Query();
			query.addCriteria(Criteria.where("from_version").is(versionFrom).and("to_version").is(versionTo));
			List<DeltaETY> listOut = mongoTemplate.find(query, DeltaETY.class);
			if (!listOut.isEmpty()) {
				output = listOut.get(0);
			}
		} catch (final Exception ex) {
			log.error("Errore durante il recupero del delta per coppia di versioni", ex);
			throw new BusinessException("Errore durante il recupero del delta per coppia di versioni", ex);
		}
		return output;				 
	}

	public DeltaETY save(final DeltaETY delta) {
		DeltaETY output = null;
		try {
			Validation.mustBeTrue(delta.getId() == null, "Il documento non deve avere l'id valorizzato.");
			
			//Verifica se presente il delta, se lo è recupero id e lo uso per l'upsert con i nuovi dati.
			DeltaETY oldDelta = getByVersions(delta.getFromVersion(), delta.getToVersion());
			if (oldDelta != null) {
				delta.setId(oldDelta.getId());
			}
			output = mongoTemplate.save(delta);	
		} catch (final Exception ex) {
		    log.error("Errore durante il salvataggio di un delta", ex);
			throw new BusinessException("Errore durante il salvataggio di un delta", ex);
		}
		return output;
	}
 
}