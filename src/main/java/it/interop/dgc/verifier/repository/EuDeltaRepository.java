package it.interop.dgc.verifier.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import it.interop.dgc.verifier.config.EuropeanDrlCFG;
import it.interop.dgc.verifier.entity.EuropeanDeltaETY;
import it.interop.dgc.verifier.entity.EuropeanDeltaEntryETY;
import it.interop.dgc.verifier.entity.dto.DIDTO;
import it.interop.dgc.verifier.entity.dto.EuropeanDeltaDTO;
import it.interop.dgc.verifier.exceptions.BusinessException;
import it.interop.dgc.verifier.utils.DRLEUConstants;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class EuDeltaRepository {

    /**
     * Mongo template.
     */
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private EuropeanDrlCFG drlCFG;

    public EuropeanDeltaDTO getByVersions(final Long versionFrom,final Long versionTo, Long chunk) {
        EuropeanDeltaDTO output = null;
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where(DRLEUConstants.DeltaField.FROM_VERSION).is(versionFrom).and(DRLEUConstants.DeltaField.TO_VERSION).is(versionTo));
            List<EuropeanDeltaETY> listOut = mongoTemplate.find(query, EuropeanDeltaETY.class);
            if (!listOut.isEmpty()) {
                output = new EuropeanDeltaDTO();
                output.setDeltaETY(listOut.get(0));
                
                query = new Query();
                query.addCriteria(Criteria.where(DRLEUConstants.DeltaEntryField.FROM_VERSION).is(versionFrom).and(DRLEUConstants.DeltaEntryField.TO_VERSION).is(versionTo)); 
                query.skip((chunk-1)*drlCFG.getNumMaxItemInChunk());
                query.limit(drlCFG.getNumMaxItemInChunk());
                List<EuropeanDeltaEntryETY> listEntry = mongoTemplate.find(query, EuropeanDeltaEntryETY.class);
                List<String> idsToAdd = new ArrayList<>();
                List<String> idsToRemove = new ArrayList<>();
                for(EuropeanDeltaEntryETY entry : listEntry) {
                    if(entry.getIdentifierToAdd()!=null) {
                        idsToAdd.add(entry.getIdentifierToAdd());
                    }  else if(entry.getIdentifierToRemove()!=null) {
                        idsToRemove.add(entry.getIdentifierToRemove());
                    }
                }
                DIDTO did = new DIDTO();
                did.setInsertions(idsToAdd);
                did.setDeletions(idsToRemove);
                output.setDelta(did); 
            }
        } catch (final Exception ex) {
            log.error("Error getting delta for version pair.", ex);
            throw new BusinessException("Error getting delta for version pair.", ex);
        }
        return output;
    }
    
    public EuropeanDeltaDTO getByVersionsWithoutContent(final Long versionFrom,final Long versionTo, Long chunk) {
        EuropeanDeltaDTO output = null;
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where(DRLEUConstants.DeltaField.FROM_VERSION).is(versionFrom).and(DRLEUConstants.DeltaField.TO_VERSION).is(versionTo));
            List<EuropeanDeltaETY> listOut = mongoTemplate.find(query, EuropeanDeltaETY.class);
            if (!listOut.isEmpty()) {
                output = new EuropeanDeltaDTO();
                output.setDeltaETY(listOut.get(0)); 
            }
        } catch (final Exception ex) {
            log.error("Error getting delta for version pair.", ex);
            throw new BusinessException("Error getting delta for version pair.", ex);
        }
        return output;
    }
 
}
