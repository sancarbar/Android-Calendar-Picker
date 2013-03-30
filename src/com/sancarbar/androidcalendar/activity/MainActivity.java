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
import android.view.View;
import android.widget.Button;
import com.sancarbar.androidcalendar.R;
import com.sancarbar.androidcalendar.util.CalendarUtil;
import com.sancarbar.androidcalendar.util.Constants;

import java.util.Date;

/**
 * MainActivity for testing the library
 * @author Santiago Carrillo
 *
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private Date startDate;
    private Date endDate;
    private Button fromDateEditText;
    private Button toDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        fromDateEditText = (Button) findViewById(R.id.fromDateEditText);
        fromDateEditText.setOnClickListener(this);
        toDateEditText = (Button) findViewById(R.id.toDateEditText);
        toDateEditText.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, CalendarActivity.class);
        intent.putExtra(Constants.START_DATE_KEY, startDate);
        intent.putExtra(Constants.END_DATE_KEY, endDate);
        switch (view.getId()){
            case R.id.fromDateEditText:
                intent.putExtra(Constants.IS_FROM_DATE_KEY, true);
                break;
            case R.id.toDateEditText:
                intent.putExtra(Constants.IS_FROM_DATE_KEY, false);
                break;
        }
        startActivityForResult(intent, Constants.CALENDAR_PICKER_ACTIVITY_RESULT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == Activity.RESULT_OK && requestCode == Constants.CALENDAR_PICKER_ACTIVITY_RESULT){
            startDate = (Date) data.getSerializableExtra(Constants.START_DATE_KEY);
            endDate = (Date) data.getSerializableExtra(Constants.END_DATE_KEY);
            updateDatesView();
        }else
            super.onActivityResult(requestCode, resultCode, data);

    }

    private void updateDatesView(){
        if(null != startDate)
            fromDateEditText.setText(CalendarUtil.getLocaleFormatDate(startDate, this));
        if(null != endDate)
            toDateEditText.setText(CalendarUtil.getLocaleFormatDate(endDate, this));
    }
}
