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

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.interop.dgc.verifier.entity.CountryListEntity;
import it.interop.dgc.verifier.repository.CountryListRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CountryListService {

    private static final Long COUNTRY_LIST_ID = 1L;

    private final CountryListRepository countryListRepository;

    /**
     * Gets the actual country list.
     * @return the country list.
     */
    @Transactional
    public CountryListEntity getCountryList() {
        CountryListEntity  cle = countryListRepository.getFirstById(COUNTRY_LIST_ID);
        if (cle == null) {
            cle =  new CountryListEntity(COUNTRY_LIST_ID,"[]");
        }
        return cle;
    }
    
}
