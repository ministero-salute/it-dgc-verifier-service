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

package it.interop.dgc.verifier.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import it.interop.dgc.verifier.entity.SignerInformationEntity;
import it.interop.dgc.verifier.repository.SignerInformationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SignerInformationService {

    private final SignerInformationRepository signerInformationRepository;


    /**
     * Method to query the db for a certificate with a resume token.
     *
     * @param resumeToken defines which certificate should be returned.
     * @return certificate if found else null.
     */
    public SignerInformationEntity getCertificate(Long resumeToken) {
        log.info("getCertificate - resumeToken {}", resumeToken);
    	if (resumeToken == null) {
            return signerInformationRepository.findFirstValidOrderByIdAsc();
        } else {
            return signerInformationRepository.findFirstValidByIdGreaterThanOrderByIdAsc(resumeToken);
        }
    }


    /**
     * Method to query the db for a list of kid from all certificates.
     *
     * @return A list of kids of all certificates found. If no certificate was found an empty list is returned.
     */
    public List<String> getListOfValidKids() {
        ArrayList<String> responseArray = new ArrayList<>();

        List<SignerInformationEntity> validSignerInformationList = signerInformationRepository.findAllValidByOrderByIdAsc();

        if (validSignerInformationList != null) {
        	log.info("getListOfValidKids - validIds count {}", validSignerInformationList.size());
        	for (SignerInformationEntity validSignerInformation : validSignerInformationList) {
                responseArray.add(validSignerInformation.getKid());
            }
        }
        log.info("getListOfValidKids - response Kid {}", responseArray);
    	
        return responseArray;
    }

}
