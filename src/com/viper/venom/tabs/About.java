/*
 * Copyright (C) 2014-2016 The Dirty Unicorns Project
 *
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
 */

package com.viper.venom.tabs;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.internal.logging.MetricsProto.MetricsEvent;
import com.android.internal.util.viper.PackageUtils;
import android.os.SystemProperties;

public class About extends SettingsPreferenceFragment {
    private static final String TAG = "About";
    private static final String KEY_VIPEROTA = "viper_ota";
    private static final String KEY_VIPEROTA_PACKAGE_NAME = "com.viper.ota";

    private PreferenceScreen mViperOTA;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.about);
        mViperOTA = (PreferenceScreen) findPreference(KEY_VIPEROTA);
        String buildtype = SystemProperties.get("ro.viper.buildtype","unofficial");
        if (!buildtype.equalsIgnoreCase("official") || !PackageUtils.isAppInstalled(getActivity(), KEY_VIPEROTA_PACKAGE_NAME)) {
            getPreferenceScreen().removePreference(mViperOTA);
        }
    }

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.VENOM;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}

