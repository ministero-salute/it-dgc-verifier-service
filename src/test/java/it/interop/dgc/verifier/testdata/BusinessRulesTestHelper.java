package it.interop.dgc.verifier.testdata;

import org.springframework.stereotype.Service;

import it.interop.dgc.verifier.entity.BusinessRuleEntity;
import it.interop.dgc.verifier.entity.ValueSetEntity;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusinessRulesTestHelper {
	
	public static final String BR_TEST_COLLECTION = "business_rules";
	public static final String VS_TEST_COLLECTION = "valuesets";
	public static final String CL_TEST_COLLECTION = "country_list";
	
    public static final String BR_IDENTIFIER_1 = "VR-DE-1";
    public static final String BR_HASH_1 = "ce50e623fd57e482ad9edf63eae7c898d639056e716aeb7f9975a3471bf3e59c";
    public static final String BR_COUNTRY_1 = "DE";
    public static final String BR_VERSION_1 = "1.0.0";
    public static final String BR_DATA_1 = "{\n"
        + "  \"Identifier\": \"VR-DE-1\",\n"
        + "  \"Version\": \"1.0.0\",\n"
        + "  \"SchemaVersion\":\"1.0.0\",\n"
        + "  \"Engine\":\"CERTLOGIC\",\n"
        + "  \"EngineVersion\":\"1.0.0\",\n"
        + "  \"Type\":\"Acceptance\",\n"
        + "  \"Country\":\"DE\",\n"
        + "  \"CertificateType\":\"Vaccination\",\n"
        + "  \"Description\":[{\"lang\":\"en\",\"desc\":\"Vaccination must be from June and doses must be 2\"}],\n"
        + "  \"ValidFrom\":\"2021-06-27T07:46:40Z\",\n"
        + "  \"ValidTo\":\"2021-08-01T07:46:40Z\",\n"
        + "  \"AffectedFields\":[\"dt\",\"dn\"],\n"
        + "  \"Logic\":{\n"
        + "    \"and\": [\n"
        + "      {\">=\":[ {\"var\":\"dt\"}, \"2021-06-01T00:00:00Z\" ]},\n"
        + "      {\">=\":[ {\"var\":\"dn\"}, 2 ]}\n"
        + "    ]\n"
        + "  }\n"
        + "}";

    public static final String BR_IDENTIFIER_2 = "VR-DE-2";
    public static final String BR_HASH_2 = "edd69d42d52a7b52059cfbea379e647039fc16117b75bf3dfec68c965552a2fd";
    public static final String BR_COUNTRY_2 = "DE";
    public static final String BR_VERSION_2 = "1.0.0";
    public static final String BR_DATA_2 = "{\n"
        + "   \"Identifier\":\"VR-DE-2\",\n"
        + "   \"Type\":\"Acceptance\",\n"
        + "   \"Country\":\"DE\",\n"
        + "   \"Version\":\"1.0.0\",\n"
        + "   \"SchemaVersion\":\"1.0.0\",\n"
        + "   \"Engine\":\"CERTLOGIC\",\n"
        + "   \"EngineVersion\":\"1.0.0\",\n"
        + "   \"CertificateType\":\"Vaccination\",\n"
        + "   \"Description\":[\n"
        + "      {\n"
        + "         \"lang\":\"en\",\n"
        + "         \"desc\":\"Just the following vaccines are valid: Moderna,AstraZeneca,Biontech, J&J\"\n"
        + "      }\n"
        + "   ],\n"
        + "   \"ValidFrom\":\"2021-05-27T07:46:40Z\",\n"
        + "   \"ValidTo\":\"2030-06-01T07:46:40Z\",\n"
        + "   \"AffectedFields\":[\n"
        + "      \"v.0.mp\"\n"
        + "   ],\n"
        + "   \"Logic\":{\n"
        + "      \"in\":[\n"
        + "         {\n"
        + "            \"var\":\"payload.v.0.mp\"\n"
        + "         },\n"
        + "         [\n"
        + "            \"EU/1/20/1528\",\n"
        + "            \"EU/1/20/1507\",\n"
        + "            \"EU/1/21/1529\",\n"
        + "            \"EU/1/20/1525\"\n"
        + "         ]\n"
        + "      ]\n"
        + "   }\n"
        + "}";

    public static final String BR_IDENTIFIER_3 = "VR-EU-1";
    public static final String BR_HASH_3 = "7bbffe1ac60dc201cf4a1303de4b8ba25ffa5ab714d882a7e4e80dfbb2c08fe7";
    public static final String BR_COUNTRY_3 = "EU";
    public static final String BR_VERSION_3 = "1.0.0";
    public static final String BR_DATA_3 = "{\n"
        + "  \"Identifier\": \"VR-EU-1\",\n"
        + "  \"Version\": \"1.0.0\",\n"
        + "  \"SchemaVersion\":\"1.0.0\",\n"
        + "  \"Engine\":\"CERTLOGIC\",\n"
        + "  \"EngineVersion\":\"1.0.0\",\n"
        + "  \"Type\":\"Acceptance\",\n"
        + "  \"Country\":\"DE\",\n"
        + "  \"CertificateType\":\"Vaccination\",\n"
        + "  \"Description\":[{\"lang\":\"en\",\"desc\":\"Vaccination must be from June and doses must be 2\"}],\n"
        + "  \"ValidFrom\":\"2021-06-27T07:46:40Z\",\n"
        + "  \"ValidTo\":\"2021-08-01T07:46:40Z\",\n"
        + "  \"AffectedFields\":[\"dt\",\"dn\"],\n"
        + "  \"Logic\":{\n"
        + "    \"and\": [\n"
        + "      {\">=\":[ {\"var\":\"dt\"}, \"2021-06-01T00:00:00Z\" ]},\n"
        + "      {\">=\":[ {\"var\":\"dn\"}, 2 ]}\n"
        + "    ]\n"
        + "  }\n"
        + "}";

    public static final String BR_IDENTIFIER_4 = "VR-DE-1";
    public static final String BR_HASH_4 = "1706b888b9abc095e78ab1ebf32f2445a36c6a263b72634ae56476ecac5c89de";
    public static final String BR_COUNTRY_4 = "DE";
    public static final String BR_VERSION_4 = "2.0.0";
    public static final String BR_DATA_4 = "{\n"
        + "  \"Identifier\": \"VR-DE-1\",\n"
        + "  \"Version\": \"2.0.0\",\n"
        + "  \"SchemaVersion\":\"1.0.0\",\n"
        + "  \"Engine\":\"CERTLOGIC\",\n"
        + "  \"EngineVersion\":\"1.0.0\",\n"
        + "  \"Type\":\"Acceptance\",\n"
        + "  \"Country\":\"DE\",\n"
        + "  \"CertificateType\":\"Vaccination\",\n"
        + "  \"Description\":[{\"lang\":\"en\",\"desc\":\"Vaccination must be from June and doses must be 2\"}],\n"
        + "  \"ValidFrom\":\"2021-06-27T07:46:40Z\",\n"
        + "  \"ValidTo\":\"2021-09-01T07:46:40Z\",\n"
        + "  \"AffectedFields\":[\"dt\",\"dn\"],\n"
        + "  \"Logic\":{\n"
        + "    \"and\": [\n"
        + "      {\">=\":[ {\"var\":\"dt\"}, \"2021-06-01T00:00:00Z\" ]},\n"
        + "      {\">=\":[ {\"var\":\"dn\"}, 2 ]}\n"
        + "    ]\n"
        + "  }\n"
        + "}";


    public static final String VALUESET_DATA_1 = "{\n"
        + "  \"valueSetId\": \"sct-vaccines-covid-19\",\n"
        + "  \"valueSetDate\": \"2021-04-27\",\n"
        + "  \"valueSetValues\": {\n"
        + "    \"1119349007\": {\n"
        + "      \"display\": \"SARS-CoV-2 mRNA vaccine\",\n"
        + "      \"lang\": \"en\",\n"
        + "      \"active\": true,\n"
        + "      \"version\": \"http://snomed.info/sct/900000000000207008/version/20210131\",\n"
        + "      \"system\": \"http://snomed.info/sct\"\n"
        + "    }}}";

    public static final String VALUESET_HASH_1 = "7d8a9a79caa9ccc5373209d85eb91c3f6beec6762fa06ddacf0172ec819cd058";

    public static final String VALUESET_IDENTIFIER_1 = "sct-vaccines-covid-19";

    public static final String VALUESET_DATA_2 = "{\n"
        + "  \"valueSetId\": \"vaccines-covid-19-names\",\n"
        + "  \"valueSetDate\": \"2021-04-27\",\n"
        + "  \"valueSetValues\": {\n"
        + "    \"EU/1/20/1528\": {\n"
        + "      \"display\": \"Comirnaty\",\n"
        + "      \"lang\": \"en\",\n"
        + "      \"active\": true,\n"
        + "      \"system\": \"https://ec.europa.eu/health/documents/community-register/html/\",\n"
        + "      \"version\": \"\"\n"
        + "    }}}";

    public static final String VALUESET_HASH_2 = "d2c03840b0e771b02967170bfc7b633702e0932b09f643a6edcd079df1ea096d";

    public static final String VALUESET_IDENTIFIER_2 = "vaccines-covid-19-names";

    public static BusinessRuleEntity getBusinessRule(String hash, String identifier, String country,String version, 
    		String data) {
    	return new BusinessRuleEntity(hash, identifier, version, country, data);
    }

	public static ValueSetEntity getValueSet(String hash, String identifier, String data) {
		return new ValueSetEntity(hash, identifier, data);
	}
    
}
