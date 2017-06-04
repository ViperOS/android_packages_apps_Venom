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
import com.android.settings.R;

import com.android.internal.logging.MetricsProto.MetricsEvent;
import com.android.settings.SettingsPreferenceFragment;

public class RecentsSettings extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {
		
    private static final String INTENT_RESTART_SYSTEMUI = "restart_systemui";

    private static final String RECENTS_CLEAR_ALL_LOCATION = "recents_clear_all_location";
    private static final String RECENTS_TYPE = "recents_use_grid";
	
    private SwitchPreference mRecentsClearAll;
    private ListPreference mRecentsClearAllLocation;
    private ListPreference mRecentsType;

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
		
        // clear all location
        mRecentsClearAllLocation = (ListPreference) prefSet.findPreference(RECENTS_CLEAR_ALL_LOCATION);
        int location = Settings.System.getIntForUser(resolver,
                Settings.System.RECENTS_CLEAR_ALL_LOCATION, 3, UserHandle.USER_CURRENT);
        mRecentsClearAllLocation.setValue(String.valueOf(location));
        mRecentsClearAllLocation.setSummary(mRecentsClearAllLocation.getEntry());
        mRecentsClearAllLocation.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mRecentsType) {
            Settings.System.putInt(getContentResolver(), Settings.System.RECENTS_USE_GRID,
                    Integer.valueOf((String) newValue));
            int val = Integer.parseInt((String) newValue);
            if (val== 0 || val == 1) {
                getActivity().getApplicationContext().sendBroadcastAsUser(new Intent(INTENT_RESTART_SYSTEMUI), new UserHandle(UserHandle.USER_ALL));
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
        }
    return false;
    }

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.VENOM;
    }
}