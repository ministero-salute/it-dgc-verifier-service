/*-
 *   Copyright (C) 2021 Presidenza del Consiglio dei Ministri.
 *   Please refer to the AUTHORS file for more information. 
 *   This program is free software: you can redistribute it and/or modify 
 *   it under the terms of the GNU Affero General Public License as 
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *   This program is distributed in the hope that it will be useful, 
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of 
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 *   GNU Affero General Public License for more details.
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program. If not, see <https://www.gnu.org/licenses/>.   
 */

package it.interop.dgc.verifier.controller;

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
import it.interop.dgc.verifier.entity.SettingEntity;
import it.interop.dgc.verifier.service.SettingsService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/v1/dgc")
@RequiredArgsConstructor
public class SettingsController {
    
	private final SettingsService settingsService;
	
    /**
     * Http Method for getting the italian business rules and other settings.
     */
    @GetMapping(path = "/settings", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Gets list of italian business rules and other settings.",
        tags = {"Settings"},
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Returns a list of settings.",
        		content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = @ArraySchema(schema = @Schema(implementation = SettingEntity.class)),
                        examples = {@ExampleObject(value = "[{\"name\":\"vaccine_start_day\",\"type\":\"VACCINE1\",\"value\":\"15\"},{\"name\":\"vaccine_end_day\",\"type\":\"VACCINE1\",\"value\":\"180\"}]")}
                    )
            )
        })
    public ResponseEntity<List<SettingEntity>> getSettings() {
        return ResponseEntity.ok(settingsService.getAllSettings());
    }

}
