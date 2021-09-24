package it.interop.dgc.verifier.repository;

import it.interop.dgc.verifier.entity.DeltaETY;
import it.interop.dgc.verifier.exceptions.BusinessException;
import it.interop.dgc.verifier.utils.Validation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

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

    public DeltaETY getByVersions(
        final Long versionFrom,
        final Long versionTo
    ) {
        DeltaETY output = null;
        try {
            final Query query = new Query();
            query.addCriteria(
                Criteria
                    .where("from_version")
                    .is(versionFrom)
                    .and("to_version")
                    .is(versionTo)
            );
            List<DeltaETY> listOut = mongoTemplate.find(query, DeltaETY.class);
            if (!listOut.isEmpty()) {
                output = listOut.get(0);
            }
        } catch (final Exception ex) {
            log.error(
                "Errore durante il recupero del delta per coppia di versioni",
                ex
            );
            throw new BusinessException(
                "Errore durante il recupero del delta per coppia di versioni",
                ex
            );
        }
        return output;
    }
 
}
