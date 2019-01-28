/*
 * Copyright (C) 2006 Blankeer
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

package com.viper.venom.customtab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class TitleIndicator extends BaseIndicator {
    private int mTextSize = 11;
    private int mTextColorResId;

    public TitleIndicator(Context context) {
        this(context, null);
    }

    public TitleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View getTabItemView(PagerAdapter adapter, int position) {
        TextView view = new TextView(getContext());
        view.setText(adapter.getPageTitle(position));
        view.setTextSize(mTextSize);
        view.setGravity(Gravity.CENTER);
        if (mTextColorResId != 0) {
            view.setTextColor(getResources().getColorStateList(mTextColorResId));
        }
        return view;
    }


    @Override
    protected void drawItemUnder(Canvas canvas, float left, float top, float right, float bottom, Paint paint) {
        if (paint != null) {
            canvas.drawRect(left, top, right, bottom, paint);
        }
    }

    @Override
    protected void drawTabUnder(Canvas canvas, float left, float top, float right, float bottom, Paint paint) {
        canvas.drawRect(left, top, right, bottom, paint);
    }

    @Override
    protected void drawDivider(Canvas canvas, float left, float top, float right, float bottom, Paint paint) {
        canvas.drawRect(left, top, right, bottom, paint);
    }

    public int getTextColorResId() {
        return mTextColorResId;
    }

    public void setTextColorResId(int mTextColorResId) {
        this.mTextColorResId = mTextColorResId;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
    }
}