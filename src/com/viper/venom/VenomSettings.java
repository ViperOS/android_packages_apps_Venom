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

package com.viper.venom;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.viper.venom.bubbletab.BubbleTab;
import com.viper.venom.tabs.StatusBar;
import com.viper.venom.tabs.Recents;
import com.viper.venom.tabs.Lockscreen;
import com.viper.venom.tabs.System;
import com.viper.venom.tabs.About;

public class VenomSettings extends SettingsPreferenceFragment {

    private static final String TAG = "VenomSettings";

    ViewPager mViewPager;
    String titleString[];
    ViewGroup mContainer;
    BubbleTab bubbleTab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContainer = container;

        View view = inflater.inflate(R.layout.venom, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        bubbleTab = (BubbleTab) view.findViewById(R.id.bubbleTab);
        TabAdapter TabAdapter = new TabAdapter(getFragmentManager());
        mViewPager.setAdapter(TabAdapter);
        bubbleTab.setupWithViewPager(mViewPager);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }

    class TabAdapter extends FragmentPagerAdapter {
        String titles[] = getTitles();
        private Fragment frags[] = new Fragment[titles.length];

        public TabAdapter(FragmentManager fm) {
            super(fm);
            frags[0] = new StatusBar();
            frags[1] = new Recents();
            frags[2] = new Lockscreen();
            frags[3] = new System();
            frags[4] = new About();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return frags[position];
        }

        @Override
        public int getCount() {
            return frags.length;
        }
    }

    private String[] getTitles() {
        String titleString[];
        titleString = new String[]{
                    getString(R.string.status_bar_tab),
                    getString(R.string.recents_tab),
                    getString(R.string.lockscreen_tab),
                    getString(R.string.system_tab),
                    getString(R.string.about_tab)};
        return titleString;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.VENOM;
    }
}