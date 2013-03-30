package com.sancarbar.androidcalendar.ui;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import com.sancarbar.androidcalendar.R;

/**
 * Calendar Button to holds the dates related data
 * @author Santiago Carrillo
 *
 */
public class CalendarButton extends Button{

    private int year;
    private int month;
    private  int day;


    public CalendarButton(Context context, int year, int month, int day) {
        super(context);
        this.year = year;
        this.month = month;
        this.day = day;
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        setLayoutParams(param);
        setText("" + day);
        setTextSize(18);
        setTextColor(R.color.gray);
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

}
