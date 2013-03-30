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


package com.sancarbar.androidcalendar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.sancarbar.androidcalendar.ui.CalendarButton;
import com.sancarbar.androidcalendar.util.CalendarUtil;

import java.util.Calendar;
import java.util.Date;


/**
 * Calendar Picker Main Activity
 * @author Santiago Carrillo
 *
 */
public class CalendarActivity extends Activity implements View.OnClickListener {

    private Date startDate;
    private Date endDate;
    private final String START_DATE_KEY = "start_date_key";
    private final String END_DATE_KEY = "end_date_key";
    private ScrollView scrollViewCalendar;
    LayoutInflater layoutInflater;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);
        if(null != savedInstanceState){
            startDate = (Date) savedInstanceState.get(START_DATE_KEY);
            endDate = (Date) savedInstanceState.get(END_DATE_KEY);
        }
        scrollViewCalendar = (ScrollView) findViewById(R.id.scrollViewCalendar);
        layoutInflater = getLayoutInflater();
        fillCalendarView();


    }


    private void fillCalendarView(){
        Calendar calendar = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.add(Calendar.YEAR, 1);
        LinearLayout calendarMonthsLayout = new LinearLayout(this);
        calendarMonthsLayout.setOrientation(LinearLayout.VERTICAL);
        while(calendar.getTime().before(calendarEnd.getTime())){
            calendarMonthsLayout.addView(getMonthLayout(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)));
            calendar.add(Calendar.MONTH, 1);
        }
        scrollViewCalendar.addView(calendarMonthsLayout);

    }


    private LinearLayout getMonthLayout(int month, int year){
        LinearLayout monthsLayout = (LinearLayout) layoutInflater.inflate(R.layout.month_view, null);
        LinearLayout monthDayButtonsContainer = (LinearLayout) monthsLayout.findViewById(R.id.monthDayButtonsContainer);
        LinearLayout weekDaysLabelsLayout = (LinearLayout) monthsLayout.findViewById(R.id.weekDaysLabelsLayout);
        TextView textViewMonthName = (TextView) monthsLayout.findViewById(R.id.textViewMonthName);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.set(year, month, 1);
        calendarEnd.add(Calendar.MONTH, 1);
        textViewMonthName.setText(CalendarUtil.getMonthNameFromLocale(month)+ " "+year);
        setWeekDaysLabels(weekDaysLabelsLayout);
        LinearLayout weekLayout = getWeekLinearLayout();
        for (int i = 1; i < calendar.get(Calendar.DAY_OF_WEEK); i++) {
            weekLayout.addView(getEmptyButton());
        }
        while (calendar.getTime().before(calendarEnd.getTime())) {
            CalendarButton calendarButton = new CalendarButton(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            calendarButton.setOnClickListener(this);
            if(calendar.getTime().before(Calendar.getInstance().getTime()))
                calendarButton.setEnabled(false);
            weekLayout.addView(calendarButton);
            if(calendar.get(Calendar.DAY_OF_WEEK) == 7){
                monthDayButtonsContainer.addView(weekLayout);
                weekLayout = getWeekLinearLayout();
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        for (int i = calendar.get(Calendar.DAY_OF_WEEK); i < 8; i++) {
            weekLayout.addView(getEmptyButton());
        }
        monthDayButtonsContainer.addView(weekLayout);



        return monthsLayout;
    }


    private void setWeekDaysLabels(LinearLayout weekDaysLabelsLayout){
        TextView sunday, monday, tuesday, wednesday, thursday, friday, saturday;
        sunday = (TextView) weekDaysLabelsLayout.findViewById(R.id.weekDayLabelSunday);
        sunday.setText(CalendarUtil.getShortWeekdayName(1));
        monday = (TextView) weekDaysLabelsLayout.findViewById(R.id.weekDayLabelMonday);
        monday.setText(CalendarUtil.getShortWeekdayName(2));
        tuesday = (TextView) weekDaysLabelsLayout.findViewById(R.id.weekDayLabelTuesday);
        tuesday.setText(CalendarUtil.getShortWeekdayName(3));
        wednesday = (TextView) weekDaysLabelsLayout.findViewById(R.id.weekDayLabelWednesday);
        wednesday.setText(CalendarUtil.getShortWeekdayName(4));
        thursday = (TextView) weekDaysLabelsLayout.findViewById(R.id.weekDayLabelThursday);
        thursday.setText(CalendarUtil.getShortWeekdayName(5));
        friday = (TextView) weekDaysLabelsLayout.findViewById(R.id.weekDayLabelFriday);
        friday.setText(CalendarUtil.getShortWeekdayName(6));
        saturday = (TextView) weekDaysLabelsLayout.findViewById(R.id.weekDayLabelSaturday);
        saturday.setText(CalendarUtil.getShortWeekdayName(7));

    }

    private LinearLayout getWeekLinearLayout(){
        LinearLayout weekLayout = new LinearLayout(this);
        weekLayout.setOrientation(LinearLayout.HORIZONTAL);
        weekLayout.setBackgroundColor(android.R.color.white);
        weekLayout.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        weekLayout.setLayoutParams(params);
        return  weekLayout;
    }

    @Override
    public void onClick(View view) {
        Log.d("Developer", "Button text: "+((Button) view).getText());
    }


    private Button getEmptyButton(){
        Button button = new Button(this);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        button.setLayoutParams(param);
        button.setEnabled(false);
        return button;
    }
}
