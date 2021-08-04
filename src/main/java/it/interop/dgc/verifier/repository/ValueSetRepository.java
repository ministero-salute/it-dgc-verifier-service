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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import it.interop.dgc.verifier.entity.ValueSetEntity;

@Repository
public class ValueSetRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	public List<ValueSetEntity> findAllByOrderByIdAsc() {
		Query query = new Query();
		query.with(Sort.by(Sort.Direction.ASC, "identifier_name"));
		List<ValueSetEntity> valueSets = mongoTemplate.find(query, ValueSetEntity.class);
		return valueSets;
	}

	public ValueSetEntity findOneByHash(String hash) {
		Query query = new Query();	
		query.addCriteria(Criteria.where("hash").is(hash));
		ValueSetEntity valueSet = mongoTemplate.findOne(query, ValueSetEntity.class);
		return valueSet;
	}
	
}