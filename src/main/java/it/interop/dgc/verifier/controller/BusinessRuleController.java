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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.interop.dgc.verifier.entity.BusinessRuleEntity;
import it.interop.dgc.verifier.entity.dto.BusinessRuleListItemDto;
import it.interop.dgc.verifier.entity.dto.ProblemReportDto;
import it.interop.dgc.verifier.exceptions.DgcaBusinessRulesResponseException;
import it.interop.dgc.verifier.service.BusinessRuleService;
import java.util.List;
import java.util.Locale;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/dgc/rules")
@RequiredArgsConstructor
public class BusinessRuleController {

    private static final String API_VERSION_HEADER = "X-VERSION";

    private final BusinessRuleService businessRuleService;

    /**
     * Http Method for getting the business rules list.
     */
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Gets the a list of all business rule ids country codes and hash values.",
        description = "This method returns a list containing the ids, country codes and hash values of all business " +
        "rules. The hash value can be used to check, if a business rule has changed and needs to be updated. " +
        "The hash value and country code can also be used to download a specific business rule afterwards.",
        tags = { "Business Rules" },
        parameters = {
            @Parameter(
                in = ParameterIn.HEADER,
                name = "X-VERSION",
                description = "Version of the API. In preparation of changes in the future. Set it to \"1.0\"",
                required = true,
                schema = @Schema(implementation = String.class)
            ),
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Returns a list of all business rule ids country codes and hash values.",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(
                        schema = @Schema(
                            implementation = BusinessRuleListItemDto.class
                        )
                    )
                )
            ),
        }
    )
    public ResponseEntity<List<BusinessRuleListItemDto>> getRules(
        @RequestHeader(
            value = API_VERSION_HEADER,
            required = false
        ) String apiVersion
    ) {
        return ResponseEntity.ok(businessRuleService.getBusinessRulesList());
    }

    /**
     * Http Method for getting the business rules list for a country.
     */
    @GetMapping(
        path = "/{country}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
        summary = "Gets the a list of all business rule ids country codes and hash values for a country.",
        description = "This method returns a list containing the ids, country codes and hash values of all business " +
        "rules for a country. The hash value can be used to check, if a business rule has changed and needs to " +
        "be updated. The hash value and country code can also be used to download a specific business " +
        "rule afterwards.",
        tags = { "Business Rules" },
        parameters = {
            @Parameter(
                in = ParameterIn.HEADER,
                name = "X-VERSION",
                description = "Version of the API. In preparation of changes in the future. Set it to \"1.0\"",
                required = true,
                schema = @Schema(implementation = String.class)
            ),
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Returns a list of all business rule ids country codes and hash values for a country.",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(
                        schema = @Schema(
                            implementation = BusinessRuleListItemDto.class
                        )
                    )
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "The Country Code has a wrong format.",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProblemReportDto.class)
                )
            ),
        }
    )
    public ResponseEntity<List<BusinessRuleListItemDto>> getRulesForCountry(
        @RequestHeader(
            value = API_VERSION_HEADER,
            required = false
        ) String apiVersion,
        @Valid @PathVariable("country") String country
    ) {
        validateCountryParameter(country);

        return ResponseEntity.ok(
            businessRuleService.getBusinessRulesListForCountry(
                country.toUpperCase(Locale.ROOT)
            )
        );
    }

    /**
     * Http Method for getting  specific business rule set .
     */
    @GetMapping(
        path = "/{country}/{hash}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
        summary = "Gets a specific business rule by its country code and hash value.",
        description = "This method can be used to download a specific business rule. Therefore the hash value and the " +
        "country code of the rule must be provided as path parameter.",
        tags = { "Business Rules" },
        parameters = {
            @Parameter(
                in = ParameterIn.PATH,
                name = "country",
                description = "Country code of the business rule to download.",
                required = true,
                schema = @Schema(implementation = String.class)
            ),
            @Parameter(
                in = ParameterIn.PATH,
                name = "hash",
                description = "Hash of the business rule to download.",
                required = true,
                schema = @Schema(implementation = String.class)
            ),
            @Parameter(
                in = ParameterIn.HEADER,
                name = "X-VERSION",
                description = "Version of the API. In preparation of changes in the future.",
                required = true,
                schema = @Schema(implementation = String.class)
            ),
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Returns the specified business rule.",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = String.class),
                    examples = {
                        @ExampleObject(
                            value = "{\n" +
                            "  \"Identifier\": \"VR-DE-1\",\n" +
                            "  \"Version\": \"1.0.0\",\n" +
                            "  \"SchemaVersion\":\"1.0.0\",\n" +
                            "  \"Engine\":\"CERTLOGIC\",\n" +
                            "  \"EngineVersion\":\"1.0.0\",\n" +
                            "  \"Type\":\"Acceptance\",\n" +
                            "  \"Country\":\"DE\",\n" +
                            "  \"CertificateType\":\"Vaccination\",\n" +
                            "  \"Description\":[{\"lang\":\"en\",\"desc\":\"Vaccination must be from June and " +
                            "doses must be 2\"}],\n" +
                            "  \"ValidFrom\":\"2021-06-27T07:46:40Z\",\n" +
                            "  \"ValidTo\":\"2021-08-01T07:46:40Z\",\n" +
                            "  \"AffectedFields\":[\"dt\",\"dn\"],\n" +
                            "  \"Logic\":{\n" +
                            "    \"and\": [\n" +
                            "      {\">=\":[ {\"var\":\"dt\"}, \"2021-06-01T00:00:00Z\" ]},\n" +
                            "      {\">=\":[ {\"var\":\"dn\"}, 2 ]}\n" +
                            "    ]\n" +
                            "  }\n" +
                            "}"
                        ),
                    }
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "The Country Code has a wrong format.",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProblemReportDto.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Business rule could not be found for the given hash and country code value.",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProblemReportDto.class)
                )
            ),
        }
    )
    public ResponseEntity<String> getRuleByCountryAndHash(
        @RequestHeader(
            value = API_VERSION_HEADER,
            required = false
        ) String apiVersion,
        @Valid @PathVariable("country") String country,
        @Valid @PathVariable("hash") String hash
    ) {
        validateCountryParameter(country);
        if (hash == null || hash.isBlank()) {
            throw new DgcaBusinessRulesResponseException(
                HttpStatus.BAD_REQUEST,
                "0x005",
                "Possible reasons: " + "The provided hash value is not correct",
                hash,
                ""
            );
        }
        BusinessRuleEntity rule = businessRuleService.getBusinessRuleByCountryAndHash(
            country.toUpperCase(Locale.ROOT),
            hash
        );

        if (rule == null) {
            throw new DgcaBusinessRulesResponseException(
                HttpStatus.NOT_FOUND,
                "0x006",
                "Possible reasons: " +
                "The provided hash or country may not be correct.",
                "country: " + country + ", hash: " + hash,
                ""
            );
        }
        return ResponseEntity.ok(rule.getRawData());
    }

    private void validateCountryParameter(String country)
        throws DgcaBusinessRulesResponseException {
        if (!country.matches("^[a-zA-Z]{2}$")) {
            throw new DgcaBusinessRulesResponseException(
                HttpStatus.BAD_REQUEST,
                "0x004",
                "Possible reasons: " +
                "The Country Code has a wrong format. Should be 2 char format.",
                country,
                ""
            );
        }
    }
}
