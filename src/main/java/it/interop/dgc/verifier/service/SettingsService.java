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

package it.interop.dgc.verifier.service;


import java.util.List;

import org.springframework.stereotype.Component;

import it.interop.dgc.verifier.entity.SettingEntity;
import it.interop.dgc.verifier.repository.SettingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SettingsService {

    private final SettingsRepository settingsRepository;

    /**
     * Method to query the db for a list of settings.
     *
     * @return A list of all settings found. If no setting was found an empty list is returned.
     */
    public List<SettingEntity> getAllSettings() {
        List<SettingEntity> settings = settingsRepository.findAll();
        log.info("getAllSettings - response {}", settings);
        return settings;
    }

}
