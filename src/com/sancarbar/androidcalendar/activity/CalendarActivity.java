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


package com.sancarbar.androidcalendar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.sancarbar.androidcalendar.R;
import com.sancarbar.androidcalendar.ui.CalendarButton;
import com.sancarbar.androidcalendar.util.CalendarUtil;
import com.sancarbar.androidcalendar.util.Constants;
import java.util.Calendar;
import java.util.Date;


/**
 * Calendar Picker Main Activity
 * @author Santiago Carrillo
 *
 */
public class CalendarActivity extends Activity implements View.OnClickListener {

    private Date departureDate;
    private Date returnDate;
    private ScrollView scrollViewCalendar;
    private LayoutInflater layoutInflater;
    private boolean isDepartureDate = false;



    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);
        Intent intent = getIntent();
        if(null != intent){
            departureDate = (Date) intent.getSerializableExtra(Constants.DEPARTURE_DATE_KEY);
            returnDate = (Date) intent.getSerializableExtra(Constants.RETURN_DATE_KEY);
            isDepartureDate =  intent.getBooleanExtra(Constants.IS_FROM_DATE_KEY, false);
        }
        scrollViewCalendar = (ScrollView) findViewById(R.id.scrollViewCalendar);
        layoutInflater = getLayoutInflater();
        fillCalendarView();
        findViewById(R.id.buttonToday).setOnClickListener(this);
        findViewById(R.id.buttonTomorrow).setOnClickListener(this);
        TextView titleBarText = ((TextView) findViewById(R.id.titleBarText));
        if(isDepartureDate)
            titleBarText.setText(getResources().getText(R.string.departure_date));
        else
            titleBarText.setText(getResources().getText(R.string.return_date));

    }


    private void fillCalendarView(){
        Calendar calendar = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(calendar.getTime());
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
        calendarEnd.setTime(calendar.getTime());
        Log.d("Developer","Calendar before: "+calendar.getTime().before(calendarEnd.getTime()));
        calendarEnd.add(Calendar.MONTH, 1);
        textViewMonthName.setText(CalendarUtil.getMonthNameFromLocale(month)+ " "+year);
        setWeekDaysLabels(weekDaysLabelsLayout);
        LinearLayout weekLayout = getWeekLinearLayout();
        for (int i = 1; i < calendar.get(Calendar.DAY_OF_WEEK); i++) {
            weekLayout.addView(getEmptyButton());
        }
        while (calendar.getTime().before(calendarEnd.getTime())) {
            CalendarButton calendarButton = new CalendarButton(this, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            Log.d("Developer","Adding date: "+calendar.getTime());
            if(CalendarUtil.isTodayInCalendar(calendar)){
                if(null != departureDate && CalendarUtil.isSameDateInCalendar(calendar, departureDate))
                    calendarButton.setBackgroundResource(R.drawable.calendar_button_pressed_selected_date);
                else if(null != returnDate && CalendarUtil.isSameDateInCalendar(calendar, returnDate))
                    calendarButton.setBackgroundResource(R.color.green);
                else
                    calendarButton.setBackgroundResource(R.drawable.calendar_button_pressed_selector);
            } else if (CalendarUtil.isWeekendDayInCalendar(calendar)) {
                if(null != departureDate && CalendarUtil.isSameDateInCalendar(calendar, departureDate))
                    calendarButton.setBackgroundResource(R.drawable.calendar_button_weekend_selected_date);
                else if(null != returnDate && CalendarUtil.isSameDateInCalendar(calendar, returnDate))
                    calendarButton.setBackgroundResource(R.color.green);
                else
                    calendarButton.setBackgroundResource(R.drawable.calendar_button_weekend_selector);

            } else{
                if(null != departureDate && CalendarUtil.isSameDateInCalendar(calendar, departureDate))
                    calendarButton.setBackgroundResource(R.drawable.calendar_button_normal_selected_date);
                else if(null != returnDate && CalendarUtil.isSameDateInCalendar(calendar, returnDate))
                    calendarButton.setBackgroundResource(R.color.green);
                else
                    calendarButton.setBackgroundResource(R.drawable.calendar_button_normal_selector);
            }
            calendarButton.setOnClickListener(this);
            if(CalendarUtil.isDateBeforeToday(calendar))
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
        Calendar calendar = Calendar.getInstance();
        if(view.getId() == R.id.buttonTomorrow || view.getId() == R.id.buttonToday){
            if(view.getId() == R.id.buttonTomorrow )
                calendar.add(Calendar.DAY_OF_MONTH, 1);
        }else{
            CalendarButton calendarButton = ((CalendarButton) view);
            calendar.set(calendarButton.getYear(), calendarButton.getMonth(), calendarButton.getDay());
        }
        Intent resultIntent = new Intent();
        if(isDepartureDate)
            departureDate = calendar.getTime();
        else
            returnDate = calendar.getTime();
        if(null != departureDate && null != returnDate){
            if(departureDate.after(returnDate) && isDepartureDate)
                returnDate = departureDate;
            else if(returnDate.before(departureDate))
                departureDate = returnDate;
        }
        resultIntent.putExtra(Constants.DEPARTURE_DATE_KEY, departureDate);
        resultIntent.putExtra(Constants.RETURN_DATE_KEY, returnDate);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }


    private Button getEmptyButton(){
        Button button = new Button(this);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        button.setLayoutParams(param);
        button.setEnabled(false);
        button.setBackgroundResource(R.drawable.calendar_button_normal_selector);
        return button;
    }


}
