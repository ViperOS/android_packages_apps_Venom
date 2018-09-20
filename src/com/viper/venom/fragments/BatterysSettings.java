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


import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceGroup;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.PreferenceCategory;
import android.support.v14.preference.PreferenceFragment;
import android.support.v14.preference.SwitchPreference;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;

import java.util.List;
import java.util.ArrayList;

import com.viper.venom.preference.ColorSelectPreference;
import com.viper.venom.preference.CustomSeekBarPreference;
import com.viper.venom.preference.SystemCheckBoxPreference;

public class BatterysSettings extends SettingsPreferenceFragment implements Preference.OnPreferenceChangeListener {

    private static final String TAG = "BatterysSettings";

    private static final String STATUSBAR_BATTERY_STYLE = "statusbar_battery_style";
    private static final String STATUSBAR_BATTERY_PERCENT = "statusbar_battery_percent_enable";
    private static final String STATUSBAR_CHARGING_COLOR = "statusbar_battery_charging_color";
    private static final String STATUSBAR_BATTERY_PERCENT_INSIDE = "statusbar_battery_percent_inside";
    private static final String STATUSBAR_BATTERY_SHOW_BOLT = "statusbar_battery_charging_image";

    private ListPreference mBatteryStyle;
    private ListPreference mBatteryPercent;
    private ColorSelectPreference mChargingColor;
    private Preference mPercentInside;
    private Preference mShowBolt;
    private int mBatteryStyleValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.batterys_settings);

        PreferenceScreen prefScreen = getPreferenceScreen();
        ContentResolver resolver = getActivity().getContentResolver();

        mBatteryStyle = (ListPreference) findPreference(STATUSBAR_BATTERY_STYLE);
        mBatteryStyleValue = Settings.System.getInt(resolver,
                Settings.System.STATUSBAR_BATTERY_STYLE, 0);

        mBatteryStyle.setValue(Integer.toString(mBatteryStyleValue));
        mBatteryStyle.setSummary(mBatteryStyle.getEntry());
        mBatteryStyle.setOnPreferenceChangeListener(this);

        mChargingColor = (ColorSelectPreference) prefScreen.findPreference(STATUSBAR_CHARGING_COLOR);
        int chargingColor = Settings.System.getInt(resolver, Settings.System.STATUSBAR_BATTERY_CHARGING_COLOR, 0xFFFFFFFF);
        mChargingColor.setColor(chargingColor);
        String hexColor = String.format("#%08X", chargingColor);
        mChargingColor.setSummary(hexColor);
        mChargingColor.setOnPreferenceChangeListener(this);

        mPercentInside = findPreference(STATUSBAR_BATTERY_PERCENT_INSIDE);

        mBatteryPercent = (ListPreference) findPreference(STATUSBAR_BATTERY_PERCENT);
        int showPercent = Settings.System.getInt(resolver,
                Settings.System.STATUSBAR_BATTERY_PERCENT, 0);
        int forceShowPercent = Settings.System.getInt(resolver,
                Settings.System.STATUSBAR_BATTERY_FORCE_PERCENT, 0);
        int batteryPercentValue = 0;
        if (showPercent == 1) {
            batteryPercentValue = 1;
        } else if (forceShowPercent == 1) {
            batteryPercentValue = 2;
        }
        mBatteryPercent.setValue(Integer.toString(batteryPercentValue));
        mBatteryPercent.setSummary(mBatteryPercent.getEntry());
        mBatteryPercent.setOnPreferenceChangeListener(this);

        mShowBolt = findPreference(STATUSBAR_BATTERY_SHOW_BOLT);

        //updateEnablement();
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        // If we didn't handle it, let preferences handle it.
        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mBatteryStyle) {
            mBatteryStyleValue = Integer.valueOf((String) newValue);
            int index = mBatteryStyle.findIndexOfValue((String) newValue);
            mBatteryStyle.setSummary(
                    mBatteryStyle.getEntries()[index]);
            Settings.System.putInt(getContentResolver(),
                    Settings.System.STATUSBAR_BATTERY_STYLE, mBatteryStyleValue);
        } else if (preference == mChargingColor) {
            String hexColor = String.format("#%08X", mChargingColor.getColor());
            mChargingColor.setSummary(hexColor);
            Settings.System.putInt(resolver,
                    Settings.System.STATUSBAR_BATTERY_CHARGING_COLOR, mChargingColor.getColor());
        } else if (preference == mBatteryPercent) {
            int batteryPercentValue = Integer.valueOf((String) newValue);
            if (batteryPercentValue == 1) {
                Settings.System.putInt(getContentResolver(),
                        Settings.System.STATUSBAR_BATTERY_PERCENT, 1);
                Settings.System.putInt(getContentResolver(),
                        Settings.System.STATUSBAR_BATTERY_FORCE_PERCENT, 0);
            } else if (batteryPercentValue == 2) {
                Settings.System.putInt(getContentResolver(),
                        Settings.System.STATUSBAR_BATTERY_PERCENT, 0);
                Settings.System.putInt(getContentResolver(),
                        Settings.System.STATUSBAR_BATTERY_FORCE_PERCENT, 1);
            } else {
                Settings.System.putInt(getContentResolver(),
                        Settings.System.STATUSBAR_BATTERY_PERCENT, 0);
                Settings.System.putInt(getContentResolver(),
                        Settings.System.STATUSBAR_BATTERY_FORCE_PERCENT, 0);
            }
            mBatteryPercent.setValue(Integer.toString(batteryPercentValue));
            mBatteryPercent.setSummary(mBatteryPercent.getEntry());
        }
        //updateEnablement();
        return true;
    }

    /*private void updateEnablement() {
        mPercentInside.setEnabled(mBatteryStyleValue != 3 && mBatteryStyleValue != 4);
        mShowBolt.setEnabled(mBatteryStyleValue != 3 && mBatteryStyleValue != 4);
        mBatteryPercent.setEnabled(mBatteryStyleValue != 3);
    }*/

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.VENOM;
    }

}