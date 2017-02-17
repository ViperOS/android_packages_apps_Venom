package com.viper.venom.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v14.preference.SwitchPreference;
import android.text.format.DateFormat;
import android.provider.Settings;
import android.os.UserHandle;
import android.view.View;
import android.widget.EditText;

import com.android.internal.logging.MetricsProto.MetricsEvent;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import java.util.Date;

public class MiscSettings extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    private static final String WIRED_RINGTONE_FOCUS_MODE = "wired_ringtone_focus_mode";

    private ListPreference mWiredHeadsetRingtoneFocus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.misc_settings);
        ContentResolver resolver = getActivity().getContentResolver();

        mWiredHeadsetRingtoneFocus = (ListPreference) findPreference(WIRED_RINGTONE_FOCUS_MODE);
        int mWiredHeadsetRingtoneFocusValue = Settings.Global.getInt(resolver,
                Settings.Global.WIRED_RINGTONE_FOCUS_MODE, 1);
        mWiredHeadsetRingtoneFocus.setValue(Integer.toString(mWiredHeadsetRingtoneFocusValue));
        mWiredHeadsetRingtoneFocus.setSummary(mWiredHeadsetRingtoneFocus.getEntry());
        mWiredHeadsetRingtoneFocus.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mWiredHeadsetRingtoneFocus) {
            int mWiredHeadsetRingtoneFocusValue = Integer.valueOf((String) newValue);
            int index = mWiredHeadsetRingtoneFocus.findIndexOfValue((String) newValue);
            mWiredHeadsetRingtoneFocus.setSummary(
                    mWiredHeadsetRingtoneFocus.getEntries()[index]);
            Settings.Global.putInt(resolver, Settings.Global.WIRED_RINGTONE_FOCUS_MODE,
                    mWiredHeadsetRingtoneFocusValue);
            return true;
        }
        return false;
    }

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.VENOM;
    }
	
}