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

package it.interop.dgc.verifier.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class DgcaBusinessRulesResponseException
    extends ResponseStatusException {

    private static final long serialVersionUID = 3798203895162932731L;

    private final String code;
    private final String details;
    private final String sentValues;
    private final String problem;

    /**
     * All Args constructor for DgcaBusinessRulesResponseException.
     *
     * @param status     the HTTP Status.
     * @param code       the error code.
     * @param details    the details of the problem.
     * @param sentValues the values sent to cause the error.
     * @param problem    short problem description.
     */
    public DgcaBusinessRulesResponseException(
        HttpStatus status,
        String code,
        String problem,
        String sentValues,
        String details
    ) {
        super(status);
        this.code = code;
        this.details = details;
        this.sentValues = sentValues;
        this.problem = problem;
    }
}
