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

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.interop.dgc.verifier.entity.CountryListEntity;
import it.interop.dgc.verifier.service.CountryListService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/v1/dgc/countrylist")
@RequiredArgsConstructor
public class CountryListController {

    private static final String API_VERSION_HEADER = "X-VERSION";

    private final CountryListService countryListService;

    /**
     * Http Method for getting the country list.
     */
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Gets the country list.",
        description = "This method returns a list containing the country codes of all onboarded member states.",
        tags = {"Country List Information"},
        parameters = {
            @Parameter(
                in = ParameterIn.HEADER,
                name = "X-VERSION",
                description = "Version of the API. In preparation of changes in the future. Set it to \"1.0\"",
                required = true,
                schema = @Schema(implementation = String.class))
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Returns a JSON list, with all onboarded member states as country code.",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = String.class)),
                    examples = {
                        @ExampleObject(value =
                            "[\"BE\", \"EL\", \"LT\", \"PT\", \"BG\", \"ES\", \"LU\", \"RO\", \"CZ\", \"FR\", \"HU\", "
                                + "\"SI\", \"DK\", \"HR\", \"MT\", \"SK\", \"DE\", \"IT\", \"NL\", \"FI\", \"EE\", "
                                + "\"CY\", \"AT\", \"SE\", \"IE\", \"LV\", \"PL\"]")
                    }))
        }
    )
    public ResponseEntity<String> getCountryList(
        @RequestHeader(value = API_VERSION_HEADER, required = false) String apiVersion
    ) {
        CountryListEntity countryList = countryListService.getCountryList();
        return ResponseEntity.ok(countryList.getRawData());
    }

}
