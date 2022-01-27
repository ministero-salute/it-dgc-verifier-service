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

package it.interop.dgc.verifier.service;

import it.interop.dgc.verifier.entity.BusinessRuleEntity;
import it.interop.dgc.verifier.entity.dto.BusinessRuleListItemDto;
import it.interop.dgc.verifier.repository.BusinessRuleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BusinessRuleService {

    private final BusinessRuleRepository businessRuleRepository;

    /**
     *  Gets list of all business rules ids and hashes.
     *
     */
    public List<BusinessRuleListItemDto> getBusinessRulesList() {
        List<BusinessRuleListItemDto> businessRuleListItemDto = new ArrayList<BusinessRuleListItemDto>();
        List<BusinessRuleEntity> rulesItems = businessRuleRepository.findAllByOrderByIdentifierAsc();
        rulesItems.forEach(rulesItem ->
            businessRuleListItemDto.add(
                new BusinessRuleListItemDto(
                    rulesItem.getIdentifier(),
                    rulesItem.getVersion(),
                    rulesItem.getCountry(),
                    rulesItem.getHash()
                )
            )
        );

        return businessRuleListItemDto;
    }

    /**
     *  Gets list of all business rules ids and hashes for a country.
     */
    public List<BusinessRuleListItemDto> getBusinessRulesListForCountry(
        String country
    ) {
        List<BusinessRuleListItemDto> businessRuleListItemDto = new ArrayList<BusinessRuleListItemDto>();
        List<BusinessRuleEntity> rulesItems = businessRuleRepository.findAllByCountryOrderByIdentifierAsc(
            country.toUpperCase(Locale.ROOT)
        );
        rulesItems.forEach(rulesItem ->
            businessRuleListItemDto.add(
                new BusinessRuleListItemDto(
                    rulesItem.getIdentifier(),
                    rulesItem.getVersion(),
                    rulesItem.getCountry(),
                    rulesItem.getHash()
                )
            )
        );

        return businessRuleListItemDto;
    }

    /**f
     *  Gets  a business rule by hash.
     */
    @Transactional
    public BusinessRuleEntity getBusinessRuleByCountryAndHash(
        String country,
        String hash
    ) {
        return businessRuleRepository.findOneByCountryAndHash(country, hash);
    }
}
