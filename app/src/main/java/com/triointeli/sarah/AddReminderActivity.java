package com.triointeli.sarah;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.triointeli.sarah.DatabaseModels.Reminder;

import java.util.ArrayList;

public class AddReminderActivity extends AppCompatActivity {

    EditText placeEnter, placeExit, reminderContent;
    ImageView saveDateTime, savePlaceEnterExit;
    TextView datetext, timetext;
    Button datePickerButton, timePickerButton;
    ImageButton reminderContentSetButton;
    Spinner placeEnterSpinner, placeExitSpinner;

    String placeEnterText, placeExitText,timeTextString, dateTextString, reminderContentString;

    private TimePicker alarmTimePicker;
    private DatePicker alarmDatePicker;

    LinearLayout placeDataLayout,dateTimeLayout,remainderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        placeEnter = (EditText) findViewById(R.id.placeOnEnter_EditText_AddRem_popup1);
        placeExit = (EditText) findViewById(R.id.placeOnLeave_EditText_AddRem_popup1);

        placeDataLayout=(LinearLayout)findViewById(R.id.placeDataLayout);
        dateTimeLayout=(LinearLayout)findViewById(R.id.dateTimeLayout);
        remainderLayout=(LinearLayout)findViewById(R.id.remainderLayout);

        datetext = (TextView) findViewById(R.id.dateText);
        timetext = (TextView) findViewById(R.id.timeText);

        datePickerButton = (Button) findViewById(R.id.dateButton);
        timePickerButton = (Button) findViewById(R.id.timeButton);
        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        alarmDatePicker = (DatePicker) findViewById(R.id.alarmDatePicker);

        datetext.setText("12th jan");
        timetext.setText("4:00p.m");

//        placeEnterSpinner = (Spinner) findViewById(R.id.s1);
//        placeExitSpinner = (Spinner) findViewById(R.id.s2);
//
//
//        String[] spinnerEntry = new String[MainActivity.reminders.size()];
//        for (int i=0;i<MainActivity.reminders.size();i++){
//            spinnerEntry[i]=MainActivity.reminders.get(i).getPlaceOnEnter();
//        }
//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_spinner_item, spinnerEntry);
//        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
//
//// Spinner spinYear = (Spinner)findViewById(R.id.spin);
//        placeEnterSpinner.setAdapter(spinnerArrayAdapter);
//
//        String[] spinnerExit = new String[MainActivity.reminders.size()];
//        for (int i=0;i<MainActivity.reminders.size();i++){
//            spinnerEntry[i]=MainActivity.reminders.get(i).getPlaceOnEnter();
//        }
//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_spinner_item, spinnerEntry);
//        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
//
//// Spinner spinYear = (Spinner)findViewById(R.id.spin);
//        placeEnterSpinner.setAdapter(spinnerArrayAdapter);
//
//
//        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.subject_spinner, android.R.layout.simple_spinner_dropdown_item);
//        s1.setAdapter(adapter);
//        s1.setOnItemSelectedListener(this);

        reminderContent = (EditText) findViewById(R.id.addNewReminderContent_popup3);
        reminderContentSetButton = (ImageButton) findViewById(R.id.next1st_ImageBtn_AddReminder_popup3);


        savePlaceEnterExit = (ImageView) findViewById(R.id.next1st_ImageBtn_AddReminder_popup1);

        savePlaceEnterExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeEnterText = placeEnter.getText().toString();
                placeExitText = placeExit.getText().toString();
                placeDataLayout.setVisibility(View.INVISIBLE);
                dateTimeLayout.setVisibility(View.VISIBLE);
            }

        });

        saveDateTime = (ImageView) findViewById(R.id.next1st_ImageBtn_AddReminder_popup2);
        saveDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeTextString = timetext.getText().toString();
                dateTextString = datetext.getText().toString();
                datetext.setText("12th jan");
                timetext.setText("4:00p.m");
                remainderLayout.setVisibility(View.VISIBLE);
                dateTimeLayout.setVisibility(View.INVISIBLE);
            }
        });
        reminderContentSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminderContentString = reminderContent.getText().toString();
                MainActivity.reminders.add(new Reminder(reminderContentString, dateTextString + "@" + timeTextString, true, placeEnterText, placeExitText));

                remainderLayout.setVisibility(View.INVISIBLE);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
//        Spinner spin = (Spinner) parent;
//        Spinner spin2 = (Spinner) parent;
//        if (spin.getId() == R.id.s1) {
//            TextView selectedTextView = (TextView) view;
//            filter1 = selectedTextView.getText().toString();
//        }
//        if (spin2.getId() == R.id.s2) {
//            TextView selectedTextView = (TextView) view;
//            filter2 = selectedTextView.getText().toString();
//        }
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }

}
