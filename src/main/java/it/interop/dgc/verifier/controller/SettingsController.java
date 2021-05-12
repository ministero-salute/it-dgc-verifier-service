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

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/v1/dgc")
@RequiredArgsConstructor
public class SettingsController {
    
    /**
     * Http Method for getting the italian business rules.
     */
    @GetMapping(path = "/settings", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Gets list of italian business rules.",
        tags = {"Business Rules"},
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Returns a list of italian business rules.",
        		content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = @ArraySchema(schema = @Schema(implementation = String.class)),
                        examples = {@ExampleObject(value = "[\"Business rule 1\",\"Business rule 2\"]")}
                    )
            )
        })
    public ResponseEntity<List<String>> getSettings() {

        return ResponseEntity.ok(new ArrayList<String>());
    }

}
