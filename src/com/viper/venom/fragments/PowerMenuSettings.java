/*
 * Copyright (C) 2018 ViperOS Project
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

package com.viper.venom.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.provider.SearchIndexableResource;
import android.provider.Settings;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import java.util.List;
import java.util.ArrayList;

import com.viper.venom.preference.SecureSettingSwitchPreference;

public class PowerMenuSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "PowerMenuSettings";

    private SecureSettingSwitchPreference mAdvancedReboot;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.powermenu_settings);

        final PreferenceScreen prefScreen = getPreferenceScreen();
        final ContentResolver contentResolver = getContext().getContentResolver();

        mAdvancedReboot = (SecureSettingSwitchPreference)
                findPreference(Settings.Secure.ADVANCED_REBOOT);
        mAdvancedReboot.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
         boolean result = false;
         if (preference instanceof SecureSettingSwitchPreference) {
             if (preference == mAdvancedReboot) {
                boolean value = (Boolean) objValue;
                Settings.Secure.putInt(getContentResolver(), Settings.Secure.ADVANCED_REBOOT,
                        value ? 1:0);
                Settings.Secure.putInt(getContentResolver(), Settings.Secure.GLOBAL_ACTION_DNAA,
                        value ? 1:0);
             }
             return true;
         }
         return result;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.VENOM;
    }

}