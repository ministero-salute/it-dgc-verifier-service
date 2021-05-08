/*-
 *   Copyright (C) 2021 Presidenza del Consiglio dei Ministri.
 *   Please refer to the AUTHORS file for more information. 
 *   This program is free software: you can redistribute it and/or modify 
 *   it under the terms of the GNU Affero General Public License as 
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *   This program is distributed in the hope that it will be useful, 
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of 
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 *   GNU Affero General Public License for more details.
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program. If not, see <https://www.gnu.org/licenses/>.   
 */
package it.interop.dgc.verifier.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import it.interop.dgc.verifier.entity.SignerInformationEntity;

@Repository
public class SignerInformationRepository {

	@Autowired
	private MongoTemplate mongoTemplate;
	
    public SignerInformationEntity findFirstValidOrderByIdAsc() {
		Query query = new Query();
		query.addCriteria(Criteria.where("revoked").is(false));
		query.with(Sort.by(Sort.Direction.ASC, "sortField"));
		SignerInformationEntity signerInformation = mongoTemplate.findOne(query, SignerInformationEntity.class);
		return signerInformation;
	}

	public SignerInformationEntity findFirstValidByIdGreaterThanOrderByIdAsc(Long resumeId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("revoked").is(false));
		query.addCriteria(Criteria.where("id").gt(resumeId));
		query.with(Sort.by(Sort.Direction.ASC, "sortField"));
		SignerInformationEntity signerInformation = mongoTemplate.findOne(query, SignerInformationEntity.class);
		return signerInformation;
	}
	
	public List<SignerInformationEntity> findAllValidByOrderByIdAsc() {
		Query query = new Query();
		query.addCriteria(Criteria.where("revoked").is(false));
		query.with(Sort.by(Sort.Direction.ASC, "sortField"));
		List<SignerInformationEntity> signerInformationList = mongoTemplate.find(query, SignerInformationEntity.class);
		return signerInformationList;
	}
	
}
