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
package com.sancarbar.androidcalendar.util;

import android.content.Context;
import android.text.format.DateFormat;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

/**
 * Provides methods to obtain dates related data based on Locale
 * @author Santiago Carrillo
 *
 */
public class CalendarUtil {

    private static final DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();

    private static final Calendar todayCalendar = Calendar.getInstance();


    public static String getMonthNameFromLocale(int month){
        if(month < 0 || month > 11)
            throw new IndexOutOfBoundsException("Invalid index, value must be between 0 and 11");

        return dateFormatSymbols.getMonths()[month].toUpperCase();
    }

    public static String getShortWeekdayName(int day){
        if(day < 1 || day > 7)
            throw new IndexOutOfBoundsException("Invalid index, value must be between 1 and 7");
        String dayName = dateFormatSymbols.getShortWeekdays()[day];
        return dayName.replace(dayName.charAt(0), Character.toUpperCase(dayName.charAt(0)));
    }


    public static boolean isTodayInCalendar(Calendar calendar){
        return todayCalendar.get(Calendar.DAY_OF_MONTH) ==   calendar.get(Calendar.DAY_OF_MONTH)
                && todayCalendar.get(Calendar.MONTH) ==   calendar.get(Calendar.MONTH)
                && todayCalendar.get(Calendar.YEAR) ==   calendar.get(Calendar.YEAR);
    }

    public static boolean isWeekendDayInCalendar(Calendar calendar){
        return calendar.get(Calendar.DAY_OF_WEEK) ==   Calendar.SATURDAY ||
                calendar.get(Calendar.DAY_OF_WEEK) ==   Calendar.SUNDAY;
    }

    public static boolean isDateBeforeToday(Calendar calendar){
        Calendar validationCalendar = Calendar.getInstance();
        validationCalendar.setTime(calendar.getTime());
        validationCalendar.set(Calendar.MILLISECOND, todayCalendar.get(Calendar.MILLISECOND));
        validationCalendar.set(Calendar.SECOND, todayCalendar.get(Calendar.SECOND));
        validationCalendar.set(Calendar.MINUTE, todayCalendar.get(Calendar.MINUTE));
        validationCalendar.set(Calendar.HOUR_OF_DAY, todayCalendar.get(Calendar.HOUR_OF_DAY));
        return  validationCalendar.getTime().before(todayCalendar.getTime());
    }

    public static boolean isSameDateInCalendar(Calendar calendar, Date date){
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        return dateCalendar.get(Calendar.DAY_OF_MONTH) ==   calendar.get(Calendar.DAY_OF_MONTH)
                && dateCalendar.get(Calendar.MONTH) ==   calendar.get(Calendar.MONTH)
                && dateCalendar.get(Calendar.YEAR) ==   calendar.get(Calendar.YEAR);
    }

    public static String getLocaleFormatDate(Date date, Context context){
        return DateFormat.getDateFormat(context).format(date);
    }


}
