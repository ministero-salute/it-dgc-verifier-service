package it.interop.dgc.verifier.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import it.interop.dgc.verifier.entity.DeltaETY;
import it.interop.dgc.verifier.entity.DeltaEntryETY;
import it.interop.dgc.verifier.entity.dto.DIDTO;
import it.interop.dgc.verifier.entity.dto.DeltaDTO;
import it.interop.dgc.verifier.exceptions.BusinessException;
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

    public DeltaDTO getByVersions(
        final Long versionFrom,
        final Long versionTo
    ) {
        DeltaDTO output = null;
        try {
            Query query = new Query();
            query.addCriteria(
                Criteria
                    .where("from_version")
                    .is(versionFrom)
                    .and("to_version")
                    .is(versionTo)
            );
            List<DeltaETY> listOut = mongoTemplate.find(query, DeltaETY.class);
            if (!listOut.isEmpty()) {
                output = new DeltaDTO();
                output.setDeltaETY(listOut.get(0));
                
                query = new Query();
                query.addCriteria(
                    Criteria
                        .where("from_version")
                        .is(versionFrom)
                        .and("to_version")
                        .is(versionTo)
                );
                List<DeltaEntryETY> listEntry = mongoTemplate.find(query, DeltaEntryETY.class);
                List<String> ucviToAdd = new ArrayList<>();
                List<String> ucviToRemove = new ArrayList<>();
                for(DeltaEntryETY entry : listEntry) {
                    if(entry.getUcvi_hashed_to_add()!=null) {
                        ucviToAdd.add(entry.getUcvi_hashed_to_add());
                    }  else if(entry.getUcvi_hashed_to_remove()!=null) {
                        ucviToRemove.add(entry.getUcvi_hashed_to_remove());
                    }
                }
                DIDTO did = new DIDTO();
                did.setInsertions(ucviToAdd);
                did.setDeletions(ucviToRemove);
                output.setDelta(did); 
            }
        } catch (final Exception ex) {
            log.error(
                "Error getting delta for version pair.",
                ex
            );
            throw new BusinessException(
                "Error getting delta for version pair.",
                ex
            );
        }
        return output;
    }
 
}
