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

import android.content.Context;
import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
 import android.support.v14.preference.SwitchPreference;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.android.internal.logging.nano.MetricsProto;

import com.viper.venom.support.preferences.CustomSeekBarPreference;

public class StatusBarMisc extends SettingsPreferenceFragment implements Preference.OnPreferenceChangeListener {

    private static final String SYSUI_ROUNDED_CONTENT_PADDING = "sysui_rounded_content_padding";
    private static final String SYSUI_ROUNDED_SIZE = "sysui_rounded_size";
    private static final String KEY_STATUS_BAR_LOGO = "status_bar_logo";

    private CustomSeekBarPreference mContentPadding;
    private CustomSeekBarPreference mCornerRadius;
    private SwitchPreference mShowVpLogo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.status_bar_misc);
        PreferenceScreen prefSet = getPreferenceScreen();
        ContentResolver resolver = getActivity().getContentResolver();

        mContentPadding = (CustomSeekBarPreference) findPreference(SYSUI_ROUNDED_CONTENT_PADDING);
        int contentPadding = Settings.Secure.getInt(resolver,
                Settings.Secure.SYSUI_ROUNDED_CONTENT_PADDING, 1);
                mContentPadding.setValue(contentPadding / 1);
                mContentPadding.setOnPreferenceChangeListener(this);

        mCornerRadius = (CustomSeekBarPreference) findPreference(SYSUI_ROUNDED_SIZE);
        int cornerRadius = Settings.Secure.getInt(resolver,
                Settings.Secure.SYSUI_ROUNDED_SIZE, 1);
                mCornerRadius.setValue(cornerRadius / 1);
                mCornerRadius.setOnPreferenceChangeListener(this);

        mShowVpLogo = (SwitchPreference) findPreference(KEY_STATUS_BAR_LOGO);
        mShowVpLogo.setChecked((Settings.System.getInt(getContentResolver(),
             Settings.System.STATUS_BAR_LOGO, 0) == 1));
        mShowVpLogo.setOnPreferenceChangeListener(this);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mContentPadding) {
            int value = (Integer) newValue;
            Settings.Secure.putInt(getContentResolver(),
                    Settings.Secure.SYSUI_ROUNDED_CONTENT_PADDING, value * 1);
            return true;
        } else if (preference == mCornerRadius) {
            int value = (Integer) newValue;
            Settings.Secure.putInt(getContentResolver(),
                    Settings.Secure.SYSUI_ROUNDED_SIZE, value * 1);
            return true;
        } else if  (preference == mShowVpLogo) {
            boolean value = (Boolean) newValue;
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.STATUS_BAR_LOGO, value ? 1 : 0);
            return true;
        }
        return false;
     }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.VENOM;
    }
}