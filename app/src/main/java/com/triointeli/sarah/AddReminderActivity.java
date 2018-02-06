package com.triointeli.sarah;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.triointeli.sarah.DatabaseModels.Reminder;
import com.triointeli.sarah.DatabaseModels.YourPlaces;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;

public class AddReminderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText placeEnter, placeExit, reminderContent;
    ImageView saveDateTime, savePlaceEnterExit;
    TextView datetext, timetext;
    Button datePickerButton, timePickerButton, showDateTimeButton;
    ImageButton reminderContentSetButton;
    Spinner placeEnterSpinner, placeExitSpinner;
    String placeEnterText, placeExitText, timeTextString, dateTextString, reminderContentString, spinnerEntry, spinnerExit;
    private Calendar calendar;
    AlarmManager alarmManager;

    Realm realm;
    long dateTime;
    private int i = 0;

    private TimePicker alarmTimePicker;
    private DatePicker alarmDatePicker;
    public String arrayNameYourPlaces[];

    LinearLayout placeDataLayout,dateTimeLayout,remainderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        realm=Realm.getDefaultInstance();
        arrayNameYourPlaces = new String[MainActivity.counterYouPlaces];
        RealmResults<YourPlaces> placesTEMP = realm.where(YourPlaces.class).findAll();
        realm.beginTransaction();
        for (YourPlaces place : placesTEMP) {
            arrayNameYourPlaces[i] = place.getName();
            i++;
        }
        realm.commitTransaction();
        i = 0;
        dateTime=123456;

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        placeEnter = (EditText) findViewById(R.id.placeOnEnter_EditText_AddRem_popup1);
        placeExit = (EditText) findViewById(R.id.placeOnLeave_EditText_AddRem_popup1);

        placeDataLayout = (LinearLayout) findViewById(R.id.placeDataLayout);
        dateTimeLayout = (LinearLayout) findViewById(R.id.dateTimeLayout);
        remainderLayout = (LinearLayout) findViewById(R.id.remainderLayout);

        datetext = (TextView) findViewById(R.id.dateText);
        timetext = (TextView) findViewById(R.id.timeText);

        showDateTimeButton = (Button) findViewById(R.id.showDateTimeButton);

        datePickerButton = (Button) findViewById(R.id.dateButton);
        timePickerButton = (Button) findViewById(R.id.timeButton);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmTimePicker.setVisibility(View.GONE);
                alarmDatePicker.setVisibility(View.VISIBLE);
            }
        });
        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmDatePicker.setVisibility(View.GONE);
                alarmTimePicker.setVisibility(View.VISIBLE);
            }
        });
        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        alarmDatePicker = (DatePicker) findViewById(R.id.alarmDatePicker);

        placeEnterSpinner = (Spinner) findViewById(R.id.spinnerEntry);
        placeExitSpinner = (Spinner) findViewById(R.id.spinnerExit);

//        placeEnterSpinner.setPrompt("hello");
        ArrayAdapter<String> spinnerArrayAdapterEntry = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, arrayNameYourPlaces);
        spinnerArrayAdapterEntry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placeEnterSpinner.setAdapter(spinnerArrayAdapterEntry);

        ArrayAdapter<String> spinnerArrayAdapterExit = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, arrayNameYourPlaces);
        spinnerArrayAdapterExit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        placeExitSpinner.setAdapter(spinnerArrayAdapterExit);

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
                remainderLayout.setVisibility(View.VISIBLE);
                dateTimeLayout.setVisibility(View.INVISIBLE);
                datePickerButton.setVisibility(View.GONE);
                timePickerButton.setVisibility(View.GONE);
                onSaveDateTimeClicked();
            }
        });
        reminderContentSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminderContentString = reminderContent.getText().toString();

                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {
                        Reminder reminder=bgRealm.createObject(Reminder.class);
                        reminder.setDateTime(dateTime);
                        reminder.setDone(false);
                        reminder.setPlaceOnEnter(placeEnterText);
                        reminder.setPlaceOnLeave(placeExitText);
                        reminder.setReminderContent(reminderContentString);

                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        // Transaction was a success.
                        Toast.makeText(AddReminderActivity.this, "Successfully Stored", Toast.LENGTH_SHORT).show();
                        MainActivity.reminders.add(new Reminder(reminderContentString,dateTime,false,placeEnterText,placeExitText));
                        MainActivity.mAdapter.notifyDataSetChanged();

                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        // Transaction failed and was automatically canceled.
                        Toast.makeText(AddReminderActivity.this, "Couldn't add.\nPleas try again.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AddReminderActivity.this,MainActivity.class));
                        finish();
                    }
                });

                remainderLayout.setVisibility(View.INVISIBLE);
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        Log.i("point ara198","clicked");
        Spinner spin = (Spinner) parent;
        Spinner spin2 = (Spinner) parent;
        if (spin.getId() == R.id.spinnerEntry) {
            TextView selectedTextView = (TextView) view;
            spinnerEntry = selectedTextView.getText().toString();
        }
        if (spin2.getId() == R.id.spinnerExit) {
            TextView selectedTextView = (TextView) view;
            spinnerExit = selectedTextView.getText().toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void onSaveDateTimeClicked() {
        Log.i("MyActivity", "Alarm On");
        calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, alarmDatePicker.getMonth());
        calendar.set(Calendar.YEAR, alarmDatePicker.getYear());
        calendar.set(Calendar.DAY_OF_MONTH, alarmDatePicker.getDayOfMonth());
        calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
        calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
        calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
        String time = calendar.getTime().toString();
        Log.i("point MA127", time);
        Log.i("point MA128", calendar.getTimeInMillis() + "");
        Log.i("point MA129", System.currentTimeMillis() + "");
        Intent myIntent = new Intent(AddReminderActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AddReminderActivity.this, (int) System.currentTimeMillis() % 50000, myIntent, PendingIntent.FLAG_ONE_SHOT);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
    }
//        else {
//            Log.i("point MA135",pendingIntent.toString());
//            Log.i("point MA134",alarmManager.toString());
//            alarmManager.cancel(pendingIntent);
////            setAlarmText("");
//            Log.i("MyActivity", "Alarm Off");
//        }
}

