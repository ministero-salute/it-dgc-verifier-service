/*-
 * ---license-start
 * eu-digital-green-certificates / dgca-verifier-service
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

import it.interop.dgc.verifier.testdata.SettingsTestHelper;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
class SettingsIntegrationTest {

    private static final String URI_SETTINGS_API = "/v1/dgc/settings";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void clearRepositoryData() {
        mongoTemplate.remove(new Query(), SettingsTestHelper.TEST_COLLECTION);
    }

    @Test
    void requestSettingsFromEmptySettingsList() throws Exception {
        mockMvc
            .perform(get(URI_SETTINGS_API))
            .andExpect(status().isOk())
            .andExpect(
                content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
            )
            .andExpect(content().json("[]"));
    }

    @Test
    void requestSettingsFromNotEmptySettingsList() throws Exception {
        mongoTemplate.save(
            SettingsTestHelper.getSetting(1),
            SettingsTestHelper.TEST_COLLECTION
        );

        mockMvc
            .perform(get(URI_SETTINGS_API))
            .andExpect(status().isOk())
            .andExpect(
                content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
            )
            .andExpect(
                c ->
                    assertSettingsStrEqual(
                        c,
                        SettingsTestHelper.TEST_RESPONSE_SETTING_1
                    )
            );

        mongoTemplate.save(
            SettingsTestHelper.getSetting(2),
            SettingsTestHelper.TEST_COLLECTION
        );

        mockMvc
            .perform(get(URI_SETTINGS_API))
            .andExpect(status().isOk())
            .andExpect(
                content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
            )
            .andExpect(
                c ->
                    assertSettingsStrEqual(
                        c,
                        SettingsTestHelper.TEST_RESPONSE_ALL_SETTINGS
                    )
            );
    }

    private void assertSettingsStrEqual(MvcResult result, String responseStr)
        throws UnsupportedEncodingException {
        String resultCert = result.getResponse().getContentAsString();

        Assertions.assertEquals(responseStr, resultCert);
    }
}
