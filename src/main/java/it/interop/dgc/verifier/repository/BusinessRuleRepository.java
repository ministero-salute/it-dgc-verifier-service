/*-
 * ---license-start
 * eu-digital-green-certificates / dgca-businessrule-service
 * ---
 * Copyright (C) 2021 T-Systems International GmbH and all other contributors
 * ---
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ---license-end
 */

package it.interop.dgc.verifier.repository;

import it.interop.dgc.verifier.entity.BusinessRuleEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class BusinessRuleRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<BusinessRuleEntity> findAllByOrderByIdentifierAsc() {
        Query query = new Query();
        query.addCriteria(Criteria.where("revoked").is(false));
        query.with(Sort.by(Sort.Direction.ASC, "identifier_name"));
        return mongoTemplate.find(
            query,
            BusinessRuleEntity.class
        );
    }

    public List<BusinessRuleEntity> findAllByCountryOrderByIdentifierAsc(
        String country
    ) {
        Query query = new Query();
        query.addCriteria(Criteria.where("revoked").is(false));
        query.addCriteria(Criteria.where("country_code").is(country));
        query.with(Sort.by(Sort.Direction.ASC, "identifier_name"));
        return mongoTemplate.find(
            query,
            BusinessRuleEntity.class
        );
    }

    public BusinessRuleEntity findOneByCountryAndHash(
        String country,
        String hash
    ) {
        Query query = new Query();
        query.addCriteria(Criteria.where("revoked").is(false));
        query.addCriteria(Criteria.where("country_code").is(country));
        query.addCriteria(Criteria.where("hash").is(hash));
        query.with(Sort.by(Sort.Direction.ASC, "identifier_name"));
        return mongoTemplate.findOne(
            query,
            BusinessRuleEntity.class
        );
    }
}
