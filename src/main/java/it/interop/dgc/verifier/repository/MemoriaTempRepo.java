package it.interop.dgc.verifier.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.DeleteResult;

import it.interop.dgc.verifier.entity.MemoriaTempETY;
import it.interop.dgc.verifier.exceptions.BusinessException;

/**
 * 
 *  @author vincenzoingenito
 *
 */
@Repository
public class MemoriaTempRepo {

    /**
     * Logger.
     */
//    private static final ALogger LOGGER = ALogger.getLogger(MemoriaTempRepo.class);

    /**
     * Mongo template.
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    public MemoriaTempETY findByRevokedUCVI(String revokedUCVI) {
        MemoriaTempETY output = null;
        try {
            final Query query = new Query();
            query.addCriteria(Criteria.where("revokedUcvi").is(revokedUCVI).and("daCancellare").is(false));
            List<MemoriaTempETY> listOut = mongoTemplate.find(query, MemoriaTempETY.class);
            output = listOut!=null && !listOut.isEmpty() ? listOut.get(0) : null;   
        } catch (final Exception ex) {
//            LOGGER.error("Errore durante la findByRevokedUCVI()", ex);
            throw new BusinessException("Errore durante la findByRevokedUCVI()", ex);
        }
        return output;  
    }
 
    public MemoriaTempETY saveRevokedUcvi(final MemoriaTempETY revokedUcvi) {
        try {
           return mongoTemplate.save(revokedUcvi);
        } catch(Exception ex) {
//            LOGGER.error("Errore durante il salvataggio dell'ucvi revocato", ex);
            throw new BusinessException("Errore durante il salvataggio dell'ucvi revocato", ex);
        }
        
    }
      
    public void delete(final String id) { 
        try { 
            final Query query = new Query(); 
            query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
            DeleteResult recordRimosso = mongoTemplate.remove(query, MemoriaTempETY.class,"memoria_temporanea");
            recordRimosso.getDeletedCount();
        } catch (final Exception ex) {
//            LOGGER.error("Errore durante la find by fiscal code and authCode or doc id", ex);
            throw new BusinessException("Errore durante la find by fiscal code and authCode or doc id", ex);
        }
         
    }
    
    
    
}