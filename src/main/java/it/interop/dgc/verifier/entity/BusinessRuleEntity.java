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
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@Document(collection = "business_rules")
public class BusinessRuleEntity implements Serializable {

    private static final long serialVersionUID = 7147938564859031373L;

    /**
     * SHA-256 Thumbprint of the rule (hex encoded).
     */
    @Field(name = "hash")
    private String hash;

    @Field(name = "identifier_name")
    private String identifier;

    @Field(name = "version")
    private String version;

    @Field(name = "country_code")
    private String country;

    @Field(name = "raw_data")
    private String rawData;

    @CreatedDate
    @Field(name = "created_at")
    private Date createdAt;

    @Field("revoked")
    private boolean revoked;

    @Field("revoked_date")
    private Date revokedDate;

    @Field(name = "batch_tag")
    private String downloadBatchTag;

    @Field(name = "batch_tag_revoke")
    private String revokedBatchTag;

    public BusinessRuleEntity(
        String hash,
        String identifier,
        String version,
        String country,
        String data
    ) {
        this.hash = hash;
        this.identifier = identifier;
        this.version = version;
        this.country = country;
        this.rawData = data;
    }
}
