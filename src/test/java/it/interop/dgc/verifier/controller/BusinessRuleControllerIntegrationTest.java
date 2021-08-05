/*
 *  Copyright (C) 2021 Ministero della Salute and all other contributors.
 *  Please refer to the AUTHORS file for more information. 
 *  This program is free software: you can redistribute it and/or modify 
 *  it under the terms of the GNU Affero General Public License as 
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  This program is distributed in the hope that it will be useful, 
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of 
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 *  GNU Affero General Public License for more details.
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program. If not, see <https://www.gnu.org/licenses/>.   
*/

package it.interop.dgc.verifier.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import it.interop.dgc.verifier.repository.BusinessRuleRepository;
import it.interop.dgc.verifier.testdata.BusinessRulesTestHelper;

@SpringBootTest
@AutoConfigureMockMvc
class BusinessRuleControllerIntegrationTest {

    private static final String API_VERSION_HEADER = "X-VERSION";

    @Autowired
    BusinessRuleRepository businessRuleRepository;

    @Autowired
    private MockMvc mockMvc;

	@Autowired
	private MongoTemplate mongoTemplate;

    @BeforeEach
    void clearRepositoryData() {
    	mongoTemplate.remove(new Query(), BusinessRulesTestHelper.BR_TEST_COLLECTION);
    }

    @Test
    void getEmptyRulesList() throws Exception {
        mockMvc.perform(get("/v1/dgc/rules").header(API_VERSION_HEADER, "1.0"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("[]"));
    }

    @Test
    void getRulesList() throws Exception {
        String expectedJson = "[{\"identifier\":\"VR-DE-1\",\"version\":\"1.0.0\",\"country\":\"DE\",\"hash\":"
            + "\"ce50e623fd57e482ad9edf63eae7c898d639056e716aeb7f9975a3471bf3e59c\"},{\"identifier\":\"VR-DE-2\","
            + "\"version\":\"1.0.0\",\"country\":\"DE\",\"hash\":"
            + "\"edd69d42d52a7b52059cfbea379e647039fc16117b75bf3dfec68c965552a2fd\"},{\"identifier\":\"VR-EU-1\","
            + "\"version\":\"1.0.0\",\"country\":\"EU\",\"hash\":"
            + "\"7bbffe1ac60dc201cf4a1303de4b8ba25ffa5ab714d882a7e4e80dfbb2c08fe7\"}]";

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_1,
            BusinessRulesTestHelper.BR_IDENTIFIER_1, BusinessRulesTestHelper.BR_COUNTRY_1,
            BusinessRulesTestHelper.BR_VERSION_1, BusinessRulesTestHelper.BR_DATA_1), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_2,
            BusinessRulesTestHelper.BR_IDENTIFIER_2, BusinessRulesTestHelper.BR_COUNTRY_2,
            BusinessRulesTestHelper.BR_VERSION_2, BusinessRulesTestHelper.BR_DATA_2), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_3,
            BusinessRulesTestHelper.BR_IDENTIFIER_3, BusinessRulesTestHelper.BR_COUNTRY_3,
            BusinessRulesTestHelper.BR_VERSION_3, BusinessRulesTestHelper.BR_DATA_3), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mockMvc.perform(get("/v1/dgc//rules").header(API_VERSION_HEADER, "1.0"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedJson));
    }

    @Test
    void getRulesListSameRuleWithDiffrentVersions() throws Exception {
        String expectedJson = "[{\"identifier\":\"VR-DE-1\",\"version\":\"1.0.0\",\"country\":\"DE\","
            + "\"hash\":\"ce50e623fd57e482ad9edf63eae7c898d639056e716aeb7f9975a3471bf3e59c\"},"
            + "{\"identifier\":\"VR-DE-1\",\"version\":\"2.0.0\",\"country\":\"DE\","
            + "\"hash\":\"1706b888b9abc095e78ab1ebf32f2445a36c6a263b72634ae56476ecac5c89de\"},"
            + "{\"identifier\":\"VR-DE-2\",\"version\":\"1.0.0\",\"country\":\"DE\","
            + "\"hash\":\"edd69d42d52a7b52059cfbea379e647039fc16117b75bf3dfec68c965552a2fd\"},"
            + "{\"identifier\":\"VR-EU-1\",\"version\":\"1.0.0\",\"country\":\"EU\","
            + "\"hash\":\"7bbffe1ac60dc201cf4a1303de4b8ba25ffa5ab714d882a7e4e80dfbb2c08fe7\"}]";

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_1,
            BusinessRulesTestHelper.BR_IDENTIFIER_1, BusinessRulesTestHelper.BR_COUNTRY_1,
            BusinessRulesTestHelper.BR_VERSION_1, BusinessRulesTestHelper.BR_DATA_1), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_2,
            BusinessRulesTestHelper.BR_IDENTIFIER_2, BusinessRulesTestHelper.BR_COUNTRY_2,
            BusinessRulesTestHelper.BR_VERSION_2, BusinessRulesTestHelper.BR_DATA_2), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_3,
            BusinessRulesTestHelper.BR_IDENTIFIER_3, BusinessRulesTestHelper.BR_COUNTRY_3,
            BusinessRulesTestHelper.BR_VERSION_3, BusinessRulesTestHelper.BR_DATA_3), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_4,
            BusinessRulesTestHelper.BR_IDENTIFIER_4, BusinessRulesTestHelper.BR_COUNTRY_4,
            BusinessRulesTestHelper.BR_VERSION_4, BusinessRulesTestHelper.BR_DATA_4), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);


        mockMvc.perform(get("/v1/dgc/rules").header(API_VERSION_HEADER, "1.0"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedJson));
    }

    @Test
    void getEmptyRulesListForCountry() throws Exception {
        mockMvc.perform(get("/v1/dgc/rules/" + BusinessRulesTestHelper.BR_COUNTRY_1)
            .header(API_VERSION_HEADER, "1.0"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("[]"));
    }

    @Test
    void getRulesListForCountry() throws Exception {
        String expectedJson = "[{\"identifier\":\"VR-DE-1\",\"version\":\"1.0.0\",\"country\":\"DE\","
            + "\"hash\":\"ce50e623fd57e482ad9edf63eae7c898d639056e716aeb7f9975a3471bf3e59c\"},"
            + "{\"identifier\":\"VR-DE-2\",\"version\":\"1.0.0\",\"country\":\"DE\","
            + "\"hash\":\"edd69d42d52a7b52059cfbea379e647039fc16117b75bf3dfec68c965552a2fd\"}]";

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_1,
            BusinessRulesTestHelper.BR_IDENTIFIER_1, BusinessRulesTestHelper.BR_COUNTRY_1,
            BusinessRulesTestHelper.BR_VERSION_1, BusinessRulesTestHelper.BR_DATA_1), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_2,
            BusinessRulesTestHelper.BR_IDENTIFIER_2, BusinessRulesTestHelper.BR_COUNTRY_2,
            BusinessRulesTestHelper.BR_VERSION_2, BusinessRulesTestHelper.BR_DATA_2), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_3,
            BusinessRulesTestHelper.BR_IDENTIFIER_3, BusinessRulesTestHelper.BR_COUNTRY_3,
            BusinessRulesTestHelper.BR_VERSION_3, BusinessRulesTestHelper.BR_DATA_3), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mockMvc.perform(get("/v1/dgc/rules/" + BusinessRulesTestHelper.BR_COUNTRY_1)
            .header(API_VERSION_HEADER, "1.0"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedJson));
    }

    @Test
    void getRulesListForCountryRuleWithDiffrentVersions() throws Exception {
        String expectedJson = "[{\"identifier\":\"VR-DE-1\",\"version\":\"1.0.0\",\"country\":\"DE\","
            + "\"hash\":\"ce50e623fd57e482ad9edf63eae7c898d639056e716aeb7f9975a3471bf3e59c\"},"
            + "{\"identifier\":\"VR-DE-1\",\"version\":\"2.0.0\",\"country\":\"DE\","
            + "\"hash\":\"1706b888b9abc095e78ab1ebf32f2445a36c6a263b72634ae56476ecac5c89de\"},"
            + "{\"identifier\":\"VR-DE-2\",\"version\":\"1.0.0\",\"country\":\"DE\","
            + "\"hash\":\"edd69d42d52a7b52059cfbea379e647039fc16117b75bf3dfec68c965552a2fd\"}]";

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_1,
            BusinessRulesTestHelper.BR_IDENTIFIER_1, BusinessRulesTestHelper.BR_COUNTRY_1,
            BusinessRulesTestHelper.BR_VERSION_1, BusinessRulesTestHelper.BR_DATA_1), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);

		mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_2,
            BusinessRulesTestHelper.BR_IDENTIFIER_2, BusinessRulesTestHelper.BR_COUNTRY_2,
            BusinessRulesTestHelper.BR_VERSION_2, BusinessRulesTestHelper.BR_DATA_2), 
				BusinessRulesTestHelper.BR_TEST_COLLECTION);

		mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_3,
            BusinessRulesTestHelper.BR_IDENTIFIER_3, BusinessRulesTestHelper.BR_COUNTRY_3,
            BusinessRulesTestHelper.BR_VERSION_3, BusinessRulesTestHelper.BR_DATA_3), 
				BusinessRulesTestHelper.BR_TEST_COLLECTION);

		mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_4,
            BusinessRulesTestHelper.BR_IDENTIFIER_4, BusinessRulesTestHelper.BR_COUNTRY_4,
            BusinessRulesTestHelper.BR_VERSION_4, BusinessRulesTestHelper.BR_DATA_4), 
				BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mockMvc.perform(get("/v1/dgc/rules/" + BusinessRulesTestHelper.BR_COUNTRY_1)
            .header(API_VERSION_HEADER, "1.0"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedJson));
    }

    @Test
    void getRulesListForCountryWrongCountryFormat() throws Exception {
        String expectedJson = "{\"code\":\"0x004\",\"problem\":\"Possible reasons: The Country Code has a wrong format."
            + " Should be 2 char format.\",\"sendValue\":\"EUR\",\"details\":\"\"}";

        mockMvc.perform(get("/v1/dgc/rules/EUR")
            .header(API_VERSION_HEADER, "1.0"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedJson));

        expectedJson = "{\"code\":\"0x004\",\"problem\":\"Possible reasons: The Country Code has a wrong format."
            + " Should be 2 char format.\",\"sendValue\":\"E\",\"details\":\"\"}";

        mockMvc.perform(get("/v1/dgc/rules/E")
            .header(API_VERSION_HEADER, "1.0"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedJson));

        expectedJson = "{\"code\":\"0x004\",\"problem\":\"Possible reasons: The Country Code has a wrong format."
            + " Should be 2 char format.\",\"sendValue\":\"22\",\"details\":\"\"}";

        mockMvc.perform(get("/v1/dgc/rules/22")
            .header(API_VERSION_HEADER, "1.0"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedJson));
    }

    @Test
    void getRuleByCountryAndHash() throws Exception {
    	mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_1,
            BusinessRulesTestHelper.BR_IDENTIFIER_1, BusinessRulesTestHelper.BR_COUNTRY_1,
            BusinessRulesTestHelper.BR_VERSION_1, BusinessRulesTestHelper.BR_DATA_1), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_2,
            BusinessRulesTestHelper.BR_IDENTIFIER_2, BusinessRulesTestHelper.BR_COUNTRY_2,
            BusinessRulesTestHelper.BR_VERSION_2, BusinessRulesTestHelper.BR_DATA_2), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_3,
            BusinessRulesTestHelper.BR_IDENTIFIER_3, BusinessRulesTestHelper.BR_COUNTRY_3,
            BusinessRulesTestHelper.BR_VERSION_3, BusinessRulesTestHelper.BR_DATA_3), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);
				
        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_4,
            BusinessRulesTestHelper.BR_IDENTIFIER_4, BusinessRulesTestHelper.BR_COUNTRY_4,
            BusinessRulesTestHelper.BR_VERSION_4, BusinessRulesTestHelper.BR_DATA_4), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION); 

        mockMvc.perform(get("/v1/dgc/rules/" + BusinessRulesTestHelper.BR_COUNTRY_1 + "/"
            + BusinessRulesTestHelper.BR_HASH_1)
            .header(API_VERSION_HEADER, "1.0"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(BusinessRulesTestHelper.BR_DATA_1));
    }

    @Test
    void getRuleByCountryAndHashWrongCountryFormat() throws Exception {
        String expectedJson = "{\"code\":\"0x004\",\"problem\":\"Possible reasons: The Country Code has a wrong format."
            + " Should be 2 char format.\",\"sendValue\":\"EUR\",\"details\":\"\"}";

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_1,
            BusinessRulesTestHelper.BR_IDENTIFIER_1, BusinessRulesTestHelper.BR_COUNTRY_1,
            BusinessRulesTestHelper.BR_VERSION_1, BusinessRulesTestHelper.BR_DATA_1), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_2,
            BusinessRulesTestHelper.BR_IDENTIFIER_2, BusinessRulesTestHelper.BR_COUNTRY_2,
            BusinessRulesTestHelper.BR_VERSION_2, BusinessRulesTestHelper.BR_DATA_2), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_3,
            BusinessRulesTestHelper.BR_IDENTIFIER_3, BusinessRulesTestHelper.BR_COUNTRY_3,
            BusinessRulesTestHelper.BR_VERSION_3, BusinessRulesTestHelper.BR_DATA_3), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_4,
            BusinessRulesTestHelper.BR_IDENTIFIER_4, BusinessRulesTestHelper.BR_COUNTRY_4,
            BusinessRulesTestHelper.BR_VERSION_4, BusinessRulesTestHelper.BR_DATA_4), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mockMvc.perform(get("/v1/dgc/rules/EUR/" + BusinessRulesTestHelper.BR_HASH_1)
            .header(API_VERSION_HEADER, "1.0"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedJson));

        expectedJson = "{\"code\":\"0x004\",\"problem\":\"Possible reasons: The Country Code has a wrong format."
            + " Should be 2 char format.\",\"sendValue\":\"E\",\"details\":\"\"}";

        mockMvc.perform(get("/v1/dgc/rules/E/" + BusinessRulesTestHelper.BR_HASH_1)
            .header(API_VERSION_HEADER, "1.0"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedJson));

        expectedJson = "{\"code\":\"0x004\",\"problem\":\"Possible reasons: The Country Code has a wrong format."
            + " Should be 2 char format.\",\"sendValue\":\"23\",\"details\":\"\"}";

        mockMvc.perform(get("/v1/dgc/rules/23/" + BusinessRulesTestHelper.BR_HASH_1)
            .header(API_VERSION_HEADER, "1.0"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedJson));
    }

    @Test
    void getRuleByCountryAndHashWrongCountryHashCombination() throws Exception {
        String expectedJson = "{\"code\":\"0x006\",\"problem\":\"Possible reasons: The provided hash or country may "
            + "not be correct.\",\"sendValue\":\"country: EU, "
            + "hash: ce50e623fd57e482ad9edf63eae7c898d639056e716aeb7f9975a3471bf3e59c\",\"details\":\"\"}";

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_1,
            BusinessRulesTestHelper.BR_IDENTIFIER_1, BusinessRulesTestHelper.BR_COUNTRY_1,
            BusinessRulesTestHelper.BR_VERSION_1, BusinessRulesTestHelper.BR_DATA_1), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_2,
            BusinessRulesTestHelper.BR_IDENTIFIER_2, BusinessRulesTestHelper.BR_COUNTRY_2,
            BusinessRulesTestHelper.BR_VERSION_2, BusinessRulesTestHelper.BR_DATA_2), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_3,
            BusinessRulesTestHelper.BR_IDENTIFIER_3, BusinessRulesTestHelper.BR_COUNTRY_3,
            BusinessRulesTestHelper.BR_VERSION_3, BusinessRulesTestHelper.BR_DATA_3), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_4,
            BusinessRulesTestHelper.BR_IDENTIFIER_4, BusinessRulesTestHelper.BR_COUNTRY_4,
            BusinessRulesTestHelper.BR_VERSION_4, BusinessRulesTestHelper.BR_DATA_4), 
        		BusinessRulesTestHelper.BR_TEST_COLLECTION);
        
        mockMvc.perform(get("/v1/dgc/rules/" + BusinessRulesTestHelper.BR_COUNTRY_3
            + "/" + BusinessRulesTestHelper.BR_HASH_1)
            .header(API_VERSION_HEADER, "1.0"))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedJson));
    }

    @Test
    void getRuleByCountryAndHashNotExist() throws Exception {
        String expectedJson = "{\"code\":\"0x006\",\"problem\":\"Possible reasons: The provided hash or country may "
            + "not be correct.\",\"sendValue\":\"country: DE, "
            + "hash: ce50e623fd57e482ad9edf63eae7c898d639056e716aeb7f9975a3471bf3e59c\",\"details\":\"\"}";


        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_2,
            BusinessRulesTestHelper.BR_IDENTIFIER_2, BusinessRulesTestHelper.BR_COUNTRY_2,
            BusinessRulesTestHelper.BR_VERSION_2, BusinessRulesTestHelper.BR_DATA_2), 
				BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_3,
            BusinessRulesTestHelper.BR_IDENTIFIER_3, BusinessRulesTestHelper.BR_COUNTRY_3,
            BusinessRulesTestHelper.BR_VERSION_3, BusinessRulesTestHelper.BR_DATA_3), 
				BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mongoTemplate.save(BusinessRulesTestHelper.getBusinessRule(BusinessRulesTestHelper.BR_HASH_4,
            BusinessRulesTestHelper.BR_IDENTIFIER_4, BusinessRulesTestHelper.BR_COUNTRY_4,
            BusinessRulesTestHelper.BR_VERSION_4, BusinessRulesTestHelper.BR_DATA_4), 
				BusinessRulesTestHelper.BR_TEST_COLLECTION);

        mockMvc.perform(get("/v1/dgc/rules/" + BusinessRulesTestHelper.BR_COUNTRY_1
            + "/" + BusinessRulesTestHelper.BR_HASH_1)
            .header(API_VERSION_HEADER, "1.0"))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(expectedJson));
    }
}