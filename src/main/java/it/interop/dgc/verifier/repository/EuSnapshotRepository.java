package it.interop.dgc.verifier.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import it.interop.dgc.verifier.config.EuropeanDrlCFG;
import it.interop.dgc.verifier.entity.EuropeanSnapshotETY;
import it.interop.dgc.verifier.entity.EuropeanSnapshotEntryETY;
import it.interop.dgc.verifier.entity.dto.EuropeanSnapshotDTO;
import it.interop.dgc.verifier.exceptions.BusinessException;
import it.interop.dgc.verifier.utils.DRLEUConstants;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class EuSnapshotRepository {

    /**
     * Mongo template.
     */
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private EuropeanDrlCFG drlCFG;
    
    public EuropeanSnapshotDTO getLastVersionWithContent(Long chunk) {
        EuropeanSnapshotDTO output = new EuropeanSnapshotDTO();
        Query query = new Query();
        query.addCriteria(Criteria.where(DRLEUConstants.SnapshotField.FLAG_ARCHIVED).is(false));
        query.with(Sort.by(Sort.Direction.DESC, DRLEUConstants.SnapshotField.VERSION));
        query.limit(1);
        EuropeanSnapshotETY snap = mongoTemplate.findOne(query, EuropeanSnapshotETY.class);
        output.setSnapshot(snap);
        query = new Query();
        query.addCriteria(Criteria.where(DRLEUConstants.SnapshotEntryField.VERSION).is(snap.getVersion()));
        query.skip((chunk-1)*drlCFG.getNumMaxItemInChunk());
        query.limit(drlCFG.getNumMaxItemInChunk());
        List<EuropeanSnapshotEntryETY> completeSnap = mongoTemplate.find(query, EuropeanSnapshotEntryETY.class);
        List<String> revokedIds = new ArrayList<>();
        for(EuropeanSnapshotEntryETY snapTemp : completeSnap) {
            revokedIds.add(snapTemp.getHash());
            
        }
        output.setRevokedIdentifiers(revokedIds);
        return output;
    }
     

    public Long getLastVersion() {
        Long version = null;
        final Query query = new Query();
        query.addCriteria(Criteria.where(DRLEUConstants.SnapshotField.FLAG_ARCHIVED).is(false));
        query.with(Sort.by(Sort.Direction.DESC, DRLEUConstants.SnapshotField.VERSION));
        query.limit(1);
        query.fields().include(DRLEUConstants.SnapshotField.VERSION);

        EuropeanSnapshotETY snap = mongoTemplate.findOne(query, EuropeanSnapshotETY.class);
        if (snap != null) {
            version = snap.getVersion();
        }
        return version;
    } 
    
    public EuropeanSnapshotDTO getSnapWithoutContent(final Long version) {
        EuropeanSnapshotDTO output = null;
        try {
            final Query query = new Query();
            query.addCriteria(
                Criteria
                    .where(DRLEUConstants.SnapshotField.VERSION)
                    .is(version)
                    .and(DRLEUConstants.SnapshotField.FLAG_ARCHIVED)
                    .is(false)
            );

            query.fields().include("_id");
            query.fields().include(DRLEUConstants.SnapshotField.VERSION);
            query.fields().include(DRLEUConstants.SnapshotField.CREATION_DATE);
            query.fields().include(DRLEUConstants.SnapshotField.FLAG_ARCHIVED);
            query.fields().include(DRLEUConstants.SnapshotField.TOTAL);

            List<EuropeanSnapshotETY> listOut = mongoTemplate.find(
                query,
                EuropeanSnapshotETY.class
            );
            if (!listOut.isEmpty()) {
                output = new EuropeanSnapshotDTO();
                output.setSnapshot(listOut.get(0));
            }
        } catch (final Exception ex) {
            log.error(String.format("Error while retrieving snapshot of version %s", version), ex);
            throw new BusinessException(String.format("Error while retrieving snapshot of version %s", version), ex);
        }
        return output;
    }
}
