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

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.interop.dgc.verifier.entity.ValueSetEntity;
import it.interop.dgc.verifier.entity.dto.ValueSetListItemDto;
import it.interop.dgc.verifier.repository.ValueSetRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ValueSetService {

    private final ValueSetRepository valueSetRepository;

    /**
     *  Gets list of all value set ids and hashes.
     */
    public List<ValueSetListItemDto> getValueSetsList() {
    	
    	List<ValueSetListItemDto> valueSetItemsDto = new ArrayList<ValueSetListItemDto>();
        List<ValueSetEntity> valueSetItems = valueSetRepository.findAllByOrderByIdAsc();
        valueSetItems.forEach(valueSet -> valueSetItemsDto.add(new ValueSetListItemDto(valueSet.getIdentifier(), valueSet.getHash())));
        return valueSetItemsDto;
    }

    /**
     *  Gets a value set by its hash value.
     */
    @Transactional
    public ValueSetEntity getValueSetByHash(String hash) {

        return valueSetRepository.findOneByHash(hash);
    }

}
