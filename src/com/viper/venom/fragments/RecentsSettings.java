package com.viper.venom.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v14.preference.SwitchPreference;

import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;


import com.android.internal.logging.MetricsProto.MetricsEvent;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.android.settings.utils.Helpers;

import net.margaritov.preference.colorpicker.ColorPickerPreference;
import com.viper.venom.preference.SystemSettingSwitchPreference;

public class RecentsSettings extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    private static final String MEMBAR_COLOR = "systemui_recents_mem_barcolor";
    private static final String MEM_TEXT_COLOR = "systemui_recents_mem_textcolor";
    private static final String RECENTS_CLEAR_ALL_LOCATION = "recents_clear_all_location";
    private static final String IMMERSIVE_RECENTS = "immersive_recents";
    private ColorPickerPreference mMemBarColor;
    private ColorPickerPreference mMemTextColor;

    private static final String RECENTS_TYPE = "recents_use_grid";
    private static final String RECENTS_LOCK = "recents_lock_icon";

    private SwitchPreference mRecentsClearAll;
    private ListPreference mRecentsClearAllLocation;
    private ListPreference mRecentsType;
    private ListPreference mImmersiveRecents;
    private SystemSettingSwitchPreference mRecentsLock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.recents_settings);

        PreferenceScreen prefSet = getPreferenceScreen();
        ContentResolver resolver = getActivity().getContentResolver();
		
        mRecentsType = (ListPreference) findPreference(RECENTS_TYPE);
        int type = Settings.System.getIntForUser(getActivity().getContentResolver(),
                            Settings.System.RECENTS_USE_GRID, 0,
                            UserHandle.USER_CURRENT);
        mRecentsType.setValue(String.valueOf(type));
        mRecentsType.setSummary(mRecentsType.getEntry());
        mRecentsType.setOnPreferenceChangeListener(this);

        mRecentsLock = (SystemSettingSwitchPreference) findPreference(RECENTS_LOCK);
        mRecentsLock.setEnabled(type == 0 ? true : false);

        if (type == 1){
            mRecentsLock.setChecked(false);
            Settings.System.putInt(resolver, Settings.System.RECENTS_LOCK_ICON, 0);
        }
		
        // clear all location
        mRecentsClearAllLocation = (ListPreference) prefSet.findPreference(RECENTS_CLEAR_ALL_LOCATION);
        int location = Settings.System.getIntForUser(resolver,
                Settings.System.RECENTS_CLEAR_ALL_LOCATION, 3, UserHandle.USER_CURRENT);
        mRecentsClearAllLocation.setValue(String.valueOf(location));
        mRecentsClearAllLocation.setSummary(mRecentsClearAllLocation.getEntry());
        mRecentsClearAllLocation.setOnPreferenceChangeListener(this);

        // Recents memory bar bar color
        mMemBarColor  =
                (ColorPickerPreference) findPreference(MEMBAR_COLOR);
        final int intColorBar = Settings.System.getInt(resolver,
                Settings.System.SYSTEMUI_RECENTS_MEM_BARCOLOR, 0x00ffffff);
        String hexColorBar = String.format("#%08x", (0x00ffffff & intColorBar));
        if (hexColorBar.equals("#00ffffff")) {
            mMemBarColor.setSummary(R.string.default_string);
        } else {
            mMemBarColor.setSummary(hexColorBar);
        }
        mMemBarColor.setNewPreviewColor(intColorBar);
        mMemBarColor.setOnPreferenceChangeListener(this);

        // Recents memory bar text color
        mMemTextColor  =
                (ColorPickerPreference) findPreference(MEM_TEXT_COLOR);
        final int intColorText = Settings.System.getInt(resolver,
                Settings.System.SYSTEMUI_RECENTS_MEM_TEXTCOLOR, 0x00ffffff);
        String hexColorText = String.format("#%08x", (0x00ffffff & intColorText));
        if (hexColorText.equals("#00ffffff")) {
            mMemTextColor.setSummary(R.string.default_string);
        } else {
            mMemTextColor.setSummary(hexColorText);
        }
        mMemTextColor.setNewPreviewColor(intColorText);
        mMemTextColor.setOnPreferenceChangeListener(this);

        mImmersiveRecents = (ListPreference) findPreference(IMMERSIVE_RECENTS);
        mImmersiveRecents.setValue(String.valueOf(Settings.System.getInt(
                resolver, Settings.System.IMMERSIVE_RECENTS, 0)));
        mImmersiveRecents.setSummary(mImmersiveRecents.getEntry());
        mImmersiveRecents.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mRecentsType) {
            Settings.System.putInt(resolver, Settings.System.RECENTS_USE_GRID,
                    Integer.valueOf((String) newValue));
            int val = Integer.parseInt((String) newValue);

            mRecentsLock.setEnabled(val == 0 ? true : false);
            if (val == 1){
            mRecentsLock.setChecked(false);
            Settings.System.putInt(resolver, Settings.System.RECENTS_LOCK_ICON, 0);
            }

            if (val== 0 || val == 1) {
                Helpers.showSystemUIrestartDialog(getActivity());
            }
            mRecentsType.setValue(String.valueOf(newValue));
            mRecentsType.setSummary(mRecentsType.getEntry());
        } else if (preference == mRecentsClearAllLocation) {
            int location = Integer.valueOf((String) newValue);
            int index = mRecentsClearAllLocation.findIndexOfValue((String) newValue);
            Settings.System.putIntForUser(resolver,
                    Settings.System.RECENTS_CLEAR_ALL_LOCATION, location, UserHandle.USER_CURRENT);
            mRecentsClearAllLocation.setSummary(mRecentsClearAllLocation.getEntries()[index]);
            return true;
        } else if (preference == mMemBarColor) {
            String hex = ColorPickerPreference.convertToARGB(
                Integer.parseInt(String.valueOf(newValue)));
            if (hex.equals("#00ffffff")) {
                preference.setSummary(R.string.default_string);
            } else {
                preference.setSummary(hex);
            }
            int intHex = ColorPickerPreference.convertToColorInt(hex);
            Settings.System.putInt(resolver,
                    Settings.System.SYSTEMUI_RECENTS_MEM_BARCOLOR,
                    intHex);
            return true;
        } else if (preference == mMemTextColor) {
            String hex = ColorPickerPreference.convertToARGB(
                Integer.parseInt(String.valueOf(newValue)));
            if (hex.equals("#00ffffff")) {
                preference.setSummary(R.string.default_string);
            } else {
                preference.setSummary(hex);
            }
            int intHex = ColorPickerPreference.convertToColorInt(hex);
            Settings.System.putInt(resolver,
                    Settings.System.SYSTEMUI_RECENTS_MEM_TEXTCOLOR,
                    intHex);
            return true;
        }else if (preference == mImmersiveRecents) {
            Settings.System.putInt(resolver, Settings.System.IMMERSIVE_RECENTS,
                    Integer.valueOf((String) newValue));
            mImmersiveRecents.setValue(String.valueOf(newValue));
            mImmersiveRecents.setSummary(mImmersiveRecents.getEntry());
            return true;
        }
    return false;
    }

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.VENOM;
    }
}
