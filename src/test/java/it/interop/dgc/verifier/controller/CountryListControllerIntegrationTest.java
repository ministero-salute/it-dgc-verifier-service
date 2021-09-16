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

import it.interop.dgc.verifier.entity.CountryListEntity;
import it.interop.dgc.verifier.repository.CountryListRepository;
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
class CountryListControllerIntegrationTest {

    private static final Long COUNTRY_LIST_ID = 1L;

    private static final String TEST_LIST_DATA =
        "[\"BE\", \"EL\", \"LT\", \"PT\", \"BG\", \"ES\", \"LU\", \"RO\", " +
        "\"CZ\", \"FR\", \"HU\", \"SI\", \"DK\", \"HR\", \"MT\", \"SK\", \"DE\", \"IT\", \"NL\", \"FI\", \"EE\", " +
        "\"CY\", \"AT\", \"SE\", \"IE\", \"LV\", \"PL\"]";

    @Autowired
    CountryListRepository countryListRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void clearRepositoryData() {
        mongoTemplate.remove(
            new Query(),
            BusinessRulesTestHelper.CL_TEST_COLLECTION
        );
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getEmptyCountryList() throws Exception {
        mockMvc
            .perform(get("/v1/dgc/countrylist"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("[]"));
    }

    @Test
    void getCountryList() throws Exception {
        CountryListEntity cle = new CountryListEntity(
            COUNTRY_LIST_ID,
            TEST_LIST_DATA
        );
        mongoTemplate.save(cle, BusinessRulesTestHelper.CL_TEST_COLLECTION);

        mockMvc
            .perform(get("/v1/dgc/countrylist"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(TEST_LIST_DATA));
    }
}
