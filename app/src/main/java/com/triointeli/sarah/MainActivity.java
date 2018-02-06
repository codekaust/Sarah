package com.triointeli.sarah;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.triointeli.sarah.DatabaseModels.Reminder;
import com.triointeli.sarah.DatabaseModels.YourPlaces;

import java.text.DateFormat;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private RecyclerView mRecyclerView;
    public static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private EditText tmpToStoreAddLocnName;
    private String tmpEditTextLocation;
    private View mAddPlaceView;

    private final static int MY_PERMISSION_FINE_LOCATION = 101;
    private final static int PLACE_PICKER_REQUEST = 1;

    private ArrayList<YourPlaces> yourPlacesArrayList;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private NotificationCompat.Builder builder;

    private View addReminderPopup;
    private View addReminderPopup2;
    private View addReminderPopup3;
    Menu menu_ourPlcaes;
    int subMenuCount;
    TextView currentLocation;
    private int indexSubmenu;
    Button datePickerButton, timePickerButton;
    ImageView saveDateTime,savePlaceEnterExit;
    TextView datetext, timetext;
    NotificationManagerCompat notificationManager;
    EditText placeEnter,placeExit,reminderContent;
    private TimePicker alarmTimePicker;
    private DatePicker alarmDatePicker;
    private boolean datePickerShow = false;
    private boolean timePickerShow = false;
    Realm realm;
    AlertDialog dialogAddPlace;
    AlertDialog dialogAddPlace2;
    AlertDialog dialogAddPlace3;
    ArrayList<Reminder> reminders;
    String placeEnterText,placeExitText;
    String timeTextString,dateTextString,reminderContentString;
    ImageButton reminderContentSetButton;
    AlertDialog.Builder builderReminder;
    private static final int NOTIFICATION_ID_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        indexSubmenu = 0;

        requestPermission();
        realm = Realm.getDefaultInstance();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        currentLocation = (TextView) findViewById(R.id.current_place);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buildGoogleApiClent();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        menu.add("Title1");
        menu.add("Title2");
        //menu.getItem(2).getSubMenu().add("home");*/
        menu_ourPlcaes = menu.getItem(3).getSubMenu();
        subMenuCount = menu_ourPlcaes.size();
        reminders = new ArrayList<>();
        addReminderPopup = getLayoutInflater().inflate(R.layout.add_reminder_popup, null);
        addReminderPopup2 = getLayoutInflater().inflate(R.layout.add_reminder_popup_2, null);
        addReminderPopup3 = getLayoutInflater().inflate(R.layout.add_reminder_popup_3, null);

        datePickerButton = (Button) addReminderPopup2.findViewById(R.id.dateButton);
        timePickerButton = (Button) addReminderPopup2.findViewById(R.id.timeButton);
        saveDateTime = (ImageView) addReminderPopup2.findViewById(R.id.next1st_ImageBtn_AddReminder_popup2);
        placeEnter=(EditText)addReminderPopup.findViewById(R.id.placeOnEnter_EditText_AddRem_popup1);
        placeExit=(EditText)addReminderPopup.findViewById(R.id.placeOnLeave_EditText_AddRem_popup1);
        savePlaceEnterExit=(ImageView)addReminderPopup.findViewById(R.id.next1st_ImageBtn_AddReminder_popup1);
        placeEnter.setText("home");
        placeExit.setText("LHC");
        datetext=(TextView)addReminderPopup2.findViewById(R.id.dateText);
        timetext=(TextView)addReminderPopup2.findViewById(R.id.timeText);
        reminderContent=(EditText)addReminderPopup3.findViewById(R.id.addNewReminderContent_popup3);
        reminderContentSetButton=(ImageButton)addReminderPopup3.findViewById(R.id.next1st_ImageBtn_AddReminder_popup3);
        reminderContent.setText("phn-005");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builderReminder = new AlertDialog.Builder(MainActivity.this);
                builderReminder.setView(addReminderPopup);
                dialogAddPlace = builderReminder.create();
                dialogAddPlace.setCustomTitle(getLayoutInflater().inflate(R.layout.add_place_popup_title, null));
                dialogAddPlace.show();

            }
        });

        // ERROR ERROR       @@@@@@@@ERROR




        savePlaceEnterExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeEnterText = placeEnter.getText().toString();
                placeExitText = placeExit.getText().toString();
                builderReminder.show().dismiss();
//                builderReminder.dism)
//                dialogAddPlace.
//                dialogAddPlace.cancel();
//                AlertDialog.Builder builderReminder2 = new AlertDialog.Builder(MainActivity.this);
                builderReminder.setView(addReminderPopup2);
                dialogAddPlace2 = builderReminder.create();
                dialogAddPlace2.setCustomTitle(addReminderPopup2);
                dialogAddPlace2.show();
                datetext.setText("12th jan");
                timetext.setText("4:00p.m");
            }
        });

        saveDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeTextString = timetext.getText().toString();
                dateTextString = datetext.getText().toString();
                dialogAddPlace2.cancel();
                AlertDialog.Builder builderReminder3 = new AlertDialog.Builder(MainActivity.this);
                builderReminder3.setView(addReminderPopup3);
                final AlertDialog dialogAddPlace3 = builderReminder3.create();
                dialogAddPlace3.setCustomTitle(addReminderPopup3);
                dialogAddPlace3.show();
                datetext.setText("12th jan");
                timetext.setText("4:00p.m");
            }
        });

        reminderContentSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminderContentString=reminderContent.getText().toString();
                reminders.add(new Reminder(reminderContentString,dateTextString+"@"+timeTextString,true,placeEnterText,placeExitText));
                dialogAddPlace3.cancel();
            }
        });





        datetext.setText("12th jan");
        timetext.setText("4:00p.m");
        alarmTimePicker = (TimePicker) addReminderPopup2.findViewById(R.id.alarmTimePicker);
        alarmDatePicker = (DatePicker) addReminderPopup2.findViewById(R.id.alarmDatePicker);
//        datePickerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                DatePickerFragment dialog = new DatePickerFragment();
//                dialog.show(getSupportFragmentManager(), "MainActivity.DateDialog");
//            }
//        });

        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timePickerShow) {
                    timePickerButton.setVisibility(View.GONE);
                    timePickerShow = false;
                } else {
                    timePickerButton.setVisibility(View.VISIBLE);
                    timePickerShow = true;
                }
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        DateFormat.getTimeInstance(DateFormat.SHORT).format(calander);
        mAdapter = new ReminderAdapter(reminders);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        reminders.add(new Reminder("fine", "yeah", true));
//        reminders.add(new Reminder("wtf", "fk u", false));

        yourPlacesArrayList = new ArrayList<YourPlaces>();
        addCurrentlyStoredPlacesToArrayList();

        builder = new NotificationCompat.Builder(this);
        notificationManager = NotificationManagerCompat.from(getApplicationContext());
    }

//    @Override
//    protected Dialog onCreateDialog(int id) {
//        // TODO Auto-generated method stub
//        if (id == 999) {
//            return new DatePickerDialog(this,
//                    myDateListener, year, month, day);
//        }
//        return null;
//    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
//                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void addCurrentlyStoredPlacesToArrayList() {

        menu_ourPlcaes.clear();
        indexSubmenu = 0;

        RealmResults<YourPlaces> places = realm.where(YourPlaces.class).findAll();

        // Use an iterator to add all
        realm.beginTransaction();

        for (YourPlaces place : places) {
            yourPlacesArrayList.add(place);
            displaySubmenu(place);
        }

        realm.commitTransaction();
    }


    protected synchronized void buildGoogleApiClent() {
        //this object helps us to connect with Google Api Services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            //Intent intentProfile=new Intent(this,class);
            //startActivity(intentProfile);
        } else if (id == R.id.nav_talk_sarah) {
            //Intent intentSarah=new Intent(this,class);
            //startActivity(intentSarah);
        } else if (id == R.id.nav_login) {
            //Intent intentLogin=new Intent(this,class);
            //startActivity(intentLogin);
        } else if (id == R.id.addPlace) {
            mAddPlaceView = getLayoutInflater().inflate(R.layout.popup_add_your_place, null);
            tmpToStoreAddLocnName = (EditText) mAddPlaceView.findViewById(R.id.placeName_addPlacePopupEditText);

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setView(mAddPlaceView);
            final AlertDialog dialogAddPlace = builder.create();
            dialogAddPlace.setCustomTitle(getLayoutInflater().inflate(R.layout.add_place_popup_title, null));
            dialogAddPlace.show();

            ImageButton next = (ImageButton) mAddPlaceView.findViewById(R.id.nextImageButton);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(tmpToStoreAddLocnName.getText().toString())) {
                        Snackbar.make(mAddPlaceView, "Please specify name.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        tmpEditTextLocation = tmpToStoreAddLocnName.getText().toString();

                        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                        try {
                            Intent intent = builder.build(MainActivity.this);
                            startActivityForResult(intent, PLACE_PICKER_REQUEST);
                        } catch (GooglePlayServicesRepairableException e) {
                            e.printStackTrace();
                        } catch (GooglePlayServicesNotAvailableException e) {
                            e.printStackTrace();
                        }
                        dialogAddPlace.dismiss();
                    }
                }
            });

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {

                Place place = PlacePicker.getPlace(MainActivity.this, data);
                LatLng locationLatLng = place.getLatLng();
                addYourPlace(locationLatLng, tmpEditTextLocation);
                tmpToStoreAddLocnName.setText(null);

            }
        }
    }

    private void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "This app requires location permissions to be granted", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    public void addYourPlace(final LatLng latLng, final String name) {

        final String LAT = Double.toString(latLng.latitude);
        final String LNG = Double.toString(latLng.longitude);

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                YourPlaces place = bgRealm.createObject(YourPlaces.class);
                place.setPlaceLAT(LAT);
                place.setPlaceLNG(LNG);
                place.setName(name);

                //add new place to database
                yourPlacesArrayList.add(place);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Toast.makeText(MainActivity.this, "Successfully Stored", Toast.LENGTH_SHORT).show();
                addCurrentlyStoredPlacesToArrayList();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //this method is triggered as the connection is established(See onStart())
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat #requestPermissions
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(10000);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        mGoogleApiClient.connect();

    }

    //this method is triggered every time t=loacation is changes either by our own app or by the system
    @Override
    public void onLocationChanged(Location location) {

        float[] dist = new float[1];

        for (int i = 0; i < yourPlacesArrayList.size(); i++) {

            Location.distanceBetween(Double.parseDouble(yourPlacesArrayList.get(i).getPlaceLAT()), Double.parseDouble(yourPlacesArrayList.get(i).getPlaceLNG()),
                    location.getLatitude(), location.getLongitude(), dist);

            if (dist[0] < 500) {
                Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();

                //  NotificationCompat.Action action= new NotificationCompat.Action.Builder(R.drawable.logo_ sarah,getString("open app",actionP));

                builder.setSmallIcon(R.drawable.logo_sarah);
                builder.setAutoCancel(true);
                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo_sarah));
                builder.setContentTitle("from SARAH");
                builder.setContentText("You have enterred a marked location");
                builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
//                builder.extend(new NotificationCompat.WearableExtender().addAction(action))

                Toast.makeText(this, "test2", Toast.LENGTH_SHORT).show();

                notificationManager.notify(NOTIFICATION_ID_1, builder.build());
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onStart() {
        super.onStart();

        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }

        super.onStop();
    }

    private void displaySubmenu(final YourPlaces place) {
        menu_ourPlcaes.add(0, indexSubmenu, Menu.NONE, place.getName()).setIcon(R.drawable.ic_room_black_24dp).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                currentLocation.setText(place.getName());
                reminders.clear();
//                mAdapter.notifyDataSetChanged();
                return false;
            }
        });

        indexSubmenu++;

    }

    private void displayReminder(final YourPlaces place) {

    }
//    private void displaySubmenu() {
////        Log.i(objects.get(0).getName(),"point ma252");
//        menu_ourPlcaes.add(0, indexSubmenu, Menu.NONE, yourPlacesArrayList.get(indexSubmenu).getName()).setIcon(R.drawable.ic_room_black_24dp).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                currentLocation.setText(yourPlacesArrayList.get(item.getItemId()).getName());
//                reminders.clear();
////                reminders.add(new Reminder(objects.get(item.getItemId()).getReminders().get(0), objects.get(item.getItemId()).getTime(),objects.get(item.getItemId()).getChecked().get(0)));
//                mAdapter.notifyDataSetChanged();
//                return false;
//            }
//        });
//        indexSubmenu++;
//
//    }

}

