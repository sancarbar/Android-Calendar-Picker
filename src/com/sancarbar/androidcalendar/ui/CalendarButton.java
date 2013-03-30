/*
 * Santiago Carrillo Barbosa - Android Calendar Picker
 * email: sancarbar@gmail.com
 *
 * https://github.com/sancarbar/Android-Calendar-Picker
 *
 * (C) 2013, Santiago Carrillo
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation;
 * version 3 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */


package com.sancarbar.androidcalendar.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Calendar Button to holds the dates related data
 * @author Santiago Carrillo
 *
 */
public class CalendarButton extends Button{

    private int year;
    private int month;
    private  int day;
    private final int blackColor = Color.parseColor("#3C3C3C");
    private final int lightGrayColor = Color.parseColor("#d0d0d0");


    public CalendarButton(Context context, int year, int month, int day) {
        super(context);
        this.year = year;
        this.month = month;
        this.day = day;
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        setLayoutParams(param);
        setText("" + day);
        setTypeface(null, Typeface.BOLD);
        setTextColor(blackColor);
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if(enabled){
            setTypeface(null, Typeface.BOLD);
            setTextColor(blackColor);
        } else{
            setTypeface(null, Typeface.NORMAL);
            setTextColor(lightGrayColor);
        }
    }
}
