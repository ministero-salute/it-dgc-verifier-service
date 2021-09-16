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

package it.interop.dgc.verifier.testdata;

import it.interop.dgc.verifier.entity.SettingEntity;

public class SettingsTestHelper {

    public static final String TEST_COLLECTION = "settings";

    public static final String TEST_SETTINGS_1_NAME = "vaccine_start_day";

    public static final String TEST_SETTINGS_1_TYPE = "VACCINE1";

    public static final String TEST_SETTINGS_1_VALUE = "15";

    public static final String TEST_SETTINGS_2_NAME = "vaccine_end_day";

    public static final String TEST_SETTINGS_2_TYPE = "VACCINE1";

    public static final String TEST_SETTINGS_2_VALUE = "180";

    public static final String TEST_RESPONSE_SETTING_1 =
        "[{\"name\":\"vaccine_start_day\",\"type\":\"VACCINE1\",\"value\":\"15\"}]";

    public static final String TEST_RESPONSE_ALL_SETTINGS =
        "[{\"name\":\"vaccine_start_day\",\"type\":\"VACCINE1\",\"value\":\"15\"},{\"name\":\"vaccine_end_day\",\"type\":\"VACCINE1\",\"value\":\"180\"}]";

    public static SettingEntity getSetting(int settingIndex) {
        SettingEntity entity = null;

        switch (settingIndex) {
            case 1:
                entity =
                    new SettingEntity(
                        TEST_SETTINGS_1_NAME,
                        TEST_SETTINGS_1_TYPE,
                        TEST_SETTINGS_1_VALUE
                    );
                break;
            case 2:
                entity =
                    new SettingEntity(
                        TEST_SETTINGS_2_NAME,
                        TEST_SETTINGS_1_TYPE,
                        TEST_SETTINGS_2_VALUE
                    );
                break;
        }
        return entity;
    }
}
