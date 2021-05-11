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

package it.interop.dgc.verifier.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import it.interop.dgc.verifier.enums.CertificateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "signer_information")
@AllArgsConstructor
@NoArgsConstructor
public class SignerInformationEntity {

	@Field(name = "id")
    private Long id;

    /**
     * Unique Identifier of the cert.
     */
    @Field(name = "kid")
    private String kid;

    /**
     * Base64 encoded certificate raw data.
     */
    @Field(name = "raw_data")
    String rawData;
    
    /**
     * CertificateType enums.
     */
    @Field(name = "certificate_type")
    private CertificateType certificateType;
    
    /**
     * Revoked flag
     */
    @Field("revoked")
	private boolean revoked;

}