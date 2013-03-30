package com.sancarbar.androidcalendar.util;

import java.text.DateFormatSymbols;

/**
 * Provides methods to obtain dates related data based on Locale
 * @author Santiago Carrillo
 *
 */
public class CalendarUtil {

    private static final DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();


    public static String getMonthNameFromLocale(int month){
        if(month < 0 || month > 11)
            throw new IndexOutOfBoundsException("Invalid index, value must be between 0 and 11");

        return dateFormatSymbols.getMonths()[month];
    }

    public static String getShortWeekdayName(int day){
        if(day < 1 || day > 7)
            throw new IndexOutOfBoundsException("Invalid index, value must be between 1 and 7");
        return dateFormatSymbols.getShortWeekdays()[day];
    }


}
