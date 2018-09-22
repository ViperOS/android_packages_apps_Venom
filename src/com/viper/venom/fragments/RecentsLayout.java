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

import android.app.Activity;
import android.content.Context;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v14.preference.SwitchPreference;
import android.provider.Settings;
import com.android.settings.R;

import java.util.Arrays;
import java.util.HashSet;

import com.android.settings.SettingsPreferenceFragment;
import com.android.internal.logging.nano.MetricsProto.MetricsEvent;

import com.viper.venom.utils.DeviceUtils;

public class RecentsLayout extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {


    private ListPreference mRecentsType;
    private static final String RECENTS_TYPE = "recents_layout_style";

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.recents_layout);

        ContentResolver resolver = getActivity().getContentResolver();
        final PreferenceScreen prefSet = getPreferenceScreen();

        // recents type
        mRecentsType = (ListPreference) findPreference(RECENTS_TYPE);
        int style = Settings.System.getIntForUser(resolver,
                Settings.System.RECENTS_LAYOUT_STYLE, 0, UserHandle.USER_CURRENT);
        mRecentsType.setValue(String.valueOf(style));
        mRecentsType.setSummary(mRecentsType.getEntry());
        mRecentsType.setOnPreferenceChangeListener(this);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        if (preference == mRecentsType) {
            int style = Integer.valueOf((String) objValue);
            int index = mRecentsType.findIndexOfValue((String) objValue);
            Settings.System.putIntForUser(getActivity().getContentResolver(),
                    Settings.System.RECENTS_LAYOUT_STYLE, style, UserHandle.USER_CURRENT);
            mRecentsType.setSummary(mRecentsType.getEntries()[index]);
            DeviceUtils.restartSystemUi(getContext());
        return true;
        }
    return false;

    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.VENOM;
    }

}