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

package it.interop.dgc.verifier.entity;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "country_list")
public class CountryListEntity implements Serializable {
	private static final long serialVersionUID = -3383002566473192925L;

	@Field(name = "id")
    private Long countryListId;

    @Field(name = "raw_data")
    private String rawData;

    @Field(name = "hash")
    private String hash;

    @Field(name = "signature")
    private String signature;
    
    public CountryListEntity(Long countryListId, String rawData) {
    	this.countryListId = countryListId;
    	this.rawData = rawData;
    }
    
}