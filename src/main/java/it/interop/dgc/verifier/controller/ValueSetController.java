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
import it.interop.dgc.verifier.entity.ValueSetEntity;
import it.interop.dgc.verifier.entity.dto.ProblemReportDto;
import it.interop.dgc.verifier.entity.dto.ValueSetListItemDto;
import it.interop.dgc.verifier.exceptions.DgcaBusinessRulesResponseException;
import it.interop.dgc.verifier.service.ValueSetService;
import java.util.List;
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
@RequestMapping("/v1/dgc/valuesets")
@RequiredArgsConstructor
public class ValueSetController {

    private static final String API_VERSION_HEADER = "X-VERSION";

    private final ValueSetService valueSetService;

    /**
     * Http Method for getting the value set list.
     */
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Gets the a list of all value set ids and value set hash values.",
        description = "This method returns a list containing the ids and hash values of all value sets. The" +
        " hash value can be used to check, if a value set has changed and needs to be updated. The hash value can" +
        " also be used to download a specific value set afterwards.",
        tags = { "Value Sets" },
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
                description = "Returns a list of all value set ids and there hash values.",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(
                        schema = @Schema(
                            implementation = ValueSetListItemDto.class
                        )
                    ),
                    examples = {
                        @ExampleObject(
                            value = "[\n" +
                            "    {\n" +
                            "        \"id\": \"country-2-codes\",\n" +
                            "        \"hash\": \"923e4e556fe7936e4a3e92e76cfb3aa87be1bf30000b4df3b755247042eea0e7\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "        \"id\": \"covid-19-lab-result\",\n" +
                            "        \"hash\": \"934e145e9bb1f560d1d3b1ec767ce3a4e9f86ae101260ed04a5cef8c1f5636c4\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "        \"id\": \"covid-19-lab-test-manufacturer-and-name\",\n" +
                            "        \"hash\": \"9da3ed15d036c20339647f8db1cb67bfcfbd04575e10b0c0df8e55a76a173a97\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "        \"id\": \"covid-19-lab-test-type\",\n" +
                            "        \"hash\": \"50ba87d7c774cd9d77e4d82f6ab34871119bc4ad51b5b6fa1100efa687be0094\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "        \"id\": \"disease-agent-targeted\",\n" +
                            "        \"hash\": \"d4bfba1fd9f2eb29dfb2938220468ccb0b481d348f192e6015d36da4b911a83a\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "        \"id\": \"sct-vaccines-covid-19\",\n" +
                            "        \"hash\": \"70505eab33ac1da351f782ee2e78e89451226c47360e7b89b8a6295bbb70eed6\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "        \"id\": \"vaccines-covid-19-auth-holders\",\n" +
                            "        \"hash\": \"55af9c705a95ced1a7d9130043f71a7a01f72e168dbd451d23d1575962518ab6\"\n" +
                            "    },\n" +
                            "    {\n" +
                            "        \"id\": \"vaccines-covid-19-names\",\n" +
                            "        \"hash\": \"8651c3db9ed5332c8fa42943d4656d442a5264debc8482b6d11d4c9176149146\"\n" +
                            "    }\n" +
                            "]"
                        ),
                    }
                )
            ),
        }
    )
    public ResponseEntity<List<ValueSetListItemDto>> getValueSetList(
        @RequestHeader(
            value = API_VERSION_HEADER,
            required = false
        ) String apiVersion
    ) {
        return ResponseEntity.ok(valueSetService.getValueSetsList());
    }

    /**
     * Http Method for getting  specific value set .
     */
    @GetMapping(path = "/{hash}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Gets a specific value set by its hash value.",
        description = "This method can be used to download a specific value set. Therefore the hash value of the value " +
        "set must be provided as path parameter.",
        tags = { "Value Sets" },
        parameters = {
            @Parameter(
                in = ParameterIn.PATH,
                name = "hash",
                description = "Hash of the value set to download",
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
                description = "Returns the specified value set.",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = String.class),
                    examples = {
                        @ExampleObject(
                            value = "{\n" +
                            "    \"valueSetId\": \"disease-agent-targeted\",\n" +
                            "    \"valueSetDate\": \"2021-04-27\",\n" +
                            "    \"valueSetValues\": {\n" +
                            "        \"840539006\": {\n" +
                            "            \"display\": \"COVID-19\",\n" +
                            "            \"lang\": \"en\",\n" +
                            "            \"active\": true,\n" +
                            "            \"version\": \"http://snomed.info/sct/900000000000207008/version/20210131\",\n" +
                            "            \"system\": \"http://snomed.info/sct\"\n" +
                            "        }\n" +
                            "    }\n" +
                            "}"
                        ),
                    }
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Value set could not be found for the given hash value.",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ProblemReportDto.class)
                )
            ),
        }
    )
    public ResponseEntity<String> getValueSet(
        @RequestHeader(
            value = API_VERSION_HEADER,
            required = false
        ) String apiVersion,
        @Valid @PathVariable("hash") String hash
    ) {
        ValueSetEntity vse = valueSetService.getValueSetByHash(hash);

        if (vse == null) {
            throw new DgcaBusinessRulesResponseException(
                HttpStatus.NOT_FOUND,
                "0x001",
                "Possible reasons: " + "The provided hash value is not correct",
                hash,
                ""
            );
        }

        return ResponseEntity.ok(vse.getRawData());
    }
}
