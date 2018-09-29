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

package com.viper.venom.about;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viper.venom.about.DiagonalView;

import com.android.settings.R;

public class FancyAboutPage extends RelativeLayout {
    private TextView l1,l2,birname,jbcname,esromname,rkschunkname,matheeusafname,kleyname,joshuaname,zjrdroidname,birdescription,jbcdescription,esromdescription,rkschunkdescription,matheeusafdescription,kleydescription,joshuadescription,zjrdroiddescription;
    DiagonalView diagonalView;
    ImageView bir,jbc,esrom,rk,matheeusaf,kley,joshua,zjrdroid,gg,tl,tw,git;
    String twitterurl,ggurl,tlurl,githuburl;
    private void init(Context context) {
        //do stuff that was in your original constructor...
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_fancy_about_page, this, true);
        l1=(TextView) findViewById(R.id.name);
        l2=(TextView) findViewById(R.id.description);
        birname=(TextView) findViewById(R.id.birname);
        jbcname=(TextView) findViewById(R.id.jbcname);
        esromname=(TextView) findViewById(R.id.esromname);
        rkschunkname=(TextView) findViewById(R.id.rkschunkname);
        matheeusafname=(TextView) findViewById(R.id.matheeusafname);
        kleyname=(TextView) findViewById(R.id.kleyname);
        joshuaname=(TextView) findViewById(R.id.joshuaname);
        zjrdroidname=(TextView) findViewById(R.id.zjrdroidname);
        birdescription=(TextView) findViewById(R.id.birdescription);
        jbcdescription=(TextView) findViewById(R.id.jbcdescription);
        esromdescription=(TextView) findViewById(R.id.esromdescription);
        rkschunkdescription=(TextView) findViewById(R.id.rkschunkdescription);
        matheeusafdescription=(TextView) findViewById(R.id.matheeusafdescription);
        kleydescription=(TextView) findViewById(R.id.kleydescription);
        joshuadescription=(TextView) findViewById(R.id.joshuadescription);
        zjrdroiddescription=(TextView) findViewById(R.id.zjrdroiddescription);
        bir=(ImageView) findViewById(R.id.bir);
        jbc=(ImageView) findViewById(R.id.jbc);
        esrom=(ImageView) findViewById(R.id.esrom);
        rk=(ImageView) findViewById(R.id.rk);
        matheeusaf=(ImageView) findViewById(R.id.matheeusaf);
        kley=(ImageView) findViewById(R.id.kley);
        joshua=(ImageView) findViewById(R.id.joshua);
        zjrdroid=(ImageView) findViewById(R.id.zjrdroid);
        tw=(ImageView) findViewById(R.id.twitter);
        gg=(ImageView) findViewById(R.id.google);
        tl=(ImageView) findViewById(R.id.telegram);
        git=(ImageView) findViewById(R.id.github);
        diagonalView = (DiagonalView) findViewById(R.id.background);
    }
    public FancyAboutPage(Context context) {
        super(context);
        init(context);
    }
    public FancyAboutPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public FancyAboutPage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs,defStyle);
        init(context);
    }
    public void setName(String name){
       l1.setText(name);
    }
    public void setDescription(String description){
       l2.setText(description);
    }
    public void setCoverTintColor(int color){
        diagonalView.setTintColor(color);
    }
    public void setCover(int drawable){
        diagonalView.setImageResource(drawable);
    }

    public void addTwitterLink(String twitterAddress){
        tw.setVisibility(VISIBLE);
        twitterurl=twitterAddress;
        tw.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!twitterurl.startsWith("http://") && !twitterurl.startsWith("https://"))
                    twitterurl = "http://" + twitterurl;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterurl));
                getContext().startActivity(browserIntent);
            }
        });
    }
    public void addGoogleLink(String googleAddress){
        gg.setVisibility(VISIBLE);
        ggurl=googleAddress;
        gg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ggurl.startsWith("http://") && !ggurl.startsWith("https://")) {
                    ggurl = "http://" + ggurl;
                }
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ggurl));
                getContext().startActivity(browserIntent);
            }
        });

    }
    public void addTelegramLink(String telegramAddress){
        tl.setVisibility(VISIBLE);
        tlurl=telegramAddress;
        tl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tlurl.startsWith("http://") && !tlurl.startsWith("https://")) {
                    tlurl = "http://" + tlurl;
                }
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tlurl));
                getContext().startActivity(browserIntent);
            }
        });

    }
    public void addGitHubLink(String githubAddress){
        git.setVisibility(VISIBLE);
        githuburl=githubAddress;
        git.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!githuburl.startsWith("http://") && !githuburl.startsWith("https://"))
                    githuburl = "http://" + githuburl;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(githuburl));
                getContext().startActivity(browserIntent);
            }
        });

    }
    public void setAppIcon(int Icon){
       bir.setImageResource(Icon);
       jbc.setImageResource(Icon);
       esrom.setImageResource(Icon);
       rk.setImageResource(Icon);
       matheeusaf.setImageResource(Icon);
       kley.setImageResource(Icon);
       joshua.setImageResource(Icon);
       zjrdroid.setImageResource(Icon);
    }
    public void setAppName(String AppName){
       birname.setText(AppName);
       jbcname.setText(AppName);
       esromname.setText(AppName);
       rkschunkname.setText(AppName);
       matheeusafname.setText(AppName);
       kleyname.setText(AppName);
       joshuaname.setText(AppName);
       zjrdroidname.setText(AppName);
    }
    public void setAppDescription(String AppDescription){
        birdescription.setText(AppDescription);
        jbcdescription.setText(AppDescription);
        esromdescription.setText(AppDescription);
        rkschunkdescription.setText(AppDescription);
        matheeusafdescription.setText(AppDescription);
        kleydescription.setText(AppDescription);
        joshuadescription.setText(AppDescription);
        zjrdroiddescription.setText(AppDescription);
    }

}
