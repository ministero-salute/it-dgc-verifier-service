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

package it.interop.dgc.verifier.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import it.interop.dgc.verifier.repository.ValueSetRepository;
import it.interop.dgc.verifier.testdata.BusinessRulesTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ValueSetControllerIntegrationTest {

    private static final String API_VERSION_HEADER = "X-VERSION";

    @Autowired
    ValueSetRepository valueSetRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void clearRepositoryData() {
        mongoTemplate.remove(
            new Query(),
            BusinessRulesTestHelper.VS_TEST_COLLECTION
        );
    }

    @Test
    void getEmptyValueSetList() throws Exception {
        mockMvc
            .perform(get("/v1/dgc/valuesets").header(API_VERSION_HEADER, "1.0"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("[]"));
    }

    @Test
    void getValueSetList() throws Exception {
        String expectedJson =
            "[{\"id\":\"" +
            BusinessRulesTestHelper.VALUESET_IDENTIFIER_1 +
            "\"," +
            "\"hash\":\"" +
            BusinessRulesTestHelper.VALUESET_HASH_1 +
            "\"}," +
            "{\"id\":\"" +
            BusinessRulesTestHelper.VALUESET_IDENTIFIER_2 +
            "\"," +
            "\"hash\":\"" +
            BusinessRulesTestHelper.VALUESET_HASH_2 +
            "\"}]";

        mongoTemplate.save(
            BusinessRulesTestHelper.getValueSet(
                BusinessRulesTestHelper.VALUESET_HASH_1,
                BusinessRulesTestHelper.VALUESET_IDENTIFIER_1,
                BusinessRulesTestHelper.VALUESET_DATA_1
            ),
            BusinessRulesTestHelper.VS_TEST_COLLECTION
        );

        mongoTemplate.save(
            BusinessRulesTestHelper.getValueSet(
                BusinessRulesTestHelper.VALUESET_HASH_2,
                BusinessRulesTestHelper.VALUESET_IDENTIFIER_2,
                BusinessRulesTestHelper.VALUESET_DATA_2
            ),
            BusinessRulesTestHelper.VS_TEST_COLLECTION
        );

        mockMvc
            .perform(get("/v1/dgc/valuesets").header(API_VERSION_HEADER, "1.0"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedJson));
    }

    @Test
    void getValueSet() throws Exception {
        mongoTemplate.save(
            BusinessRulesTestHelper.getValueSet(
                BusinessRulesTestHelper.VALUESET_HASH_1,
                BusinessRulesTestHelper.VALUESET_IDENTIFIER_1,
                BusinessRulesTestHelper.VALUESET_DATA_1
            ),
            BusinessRulesTestHelper.VS_TEST_COLLECTION
        );

        mongoTemplate.save(
            BusinessRulesTestHelper.getValueSet(
                BusinessRulesTestHelper.VALUESET_HASH_2,
                BusinessRulesTestHelper.VALUESET_IDENTIFIER_2,
                BusinessRulesTestHelper.VALUESET_DATA_2
            ),
            BusinessRulesTestHelper.VS_TEST_COLLECTION
        );

        mockMvc
            .perform(
                get(
                    "/v1/dgc/valuesets/" +
                    BusinessRulesTestHelper.VALUESET_HASH_1
                )
                    .header(API_VERSION_HEADER, "1.0")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(BusinessRulesTestHelper.VALUESET_DATA_1));

        mockMvc
            .perform(
                get(
                    "/v1/dgc/valuesets/" +
                    BusinessRulesTestHelper.VALUESET_HASH_2
                )
                    .header(API_VERSION_HEADER, "1.0")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(BusinessRulesTestHelper.VALUESET_DATA_2));
    }

    @Test
    void getValueSetNotExist() throws Exception {
        String expectedJson =
            "{\"code\":\"0x001\",\"problem\":\"Possible reasons: The provided hash value is " +
            "not correct\",\"sendValue\":\"" +
            BusinessRulesTestHelper.VALUESET_HASH_1 +
            "\",\"details\":\"\"}";

        mockMvc
            .perform(
                get(
                    "/v1/dgc/valuesets/" +
                    BusinessRulesTestHelper.VALUESET_HASH_1
                )
                    .header(API_VERSION_HEADER, "1.0")
            )
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedJson));

        mongoTemplate.save(
            BusinessRulesTestHelper.getValueSet(
                BusinessRulesTestHelper.VALUESET_HASH_2,
                BusinessRulesTestHelper.VALUESET_IDENTIFIER_2,
                BusinessRulesTestHelper.VALUESET_DATA_2
            ),
            BusinessRulesTestHelper.VS_TEST_COLLECTION
        );

        mockMvc
            .perform(
                get(
                    "/v1/dgc/valuesets/" +
                    BusinessRulesTestHelper.VALUESET_HASH_2
                )
                    .header(API_VERSION_HEADER, "1.0")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(BusinessRulesTestHelper.VALUESET_DATA_2));

        mockMvc
            .perform(
                get(
                    "/v1/dgc/valuesets/" +
                    BusinessRulesTestHelper.VALUESET_HASH_1
                )
                    .header(API_VERSION_HEADER, "1.0")
            )
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedJson));
    }
}
