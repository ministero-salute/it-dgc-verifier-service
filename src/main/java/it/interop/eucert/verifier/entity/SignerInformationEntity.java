/*-
 * ---license-start
 * EU Digital Green Certificate Gateway Service / dgc-gateway
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

package it.interop.eucert.verifier.entity;

import java.time.ZonedDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "signer_information")
public class SignerInformationEntity {

	@Field(name = "id")
    private Long id;

    /**
     * Unique Identifier of the cert.
     */
    @Field(name = "kid")
    private String kid;

    /**
     * Timestamp of the Record creation.
     */
    @Field(name = "created_at")
    private ZonedDateTime createdAt = ZonedDateTime.now();

    /**
     * Base64 encoded certificate raw data.
     */
    @Field(name = "raw_data")
    String rawData;
    
    /**
     * Revoked flag
     */
    @Field("revoked")
	private boolean revoked;

}