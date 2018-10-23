package com.example.solom.finalproject020618;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.solom.finalproject020618.db.DBHandler;
import com.example.solom.finalproject020618.db.Place;
import com.example.solom.finalproject020618.network.MyServiceSearchPlaces;
import com.example.solom.finalproject020618.network.OnLocationClick;
import com.example.solom.finalproject020618.ui.CheckBatteryStatusReceiver;
import com.example.solom.finalproject020618.ui.List_Fragment;
import com.example.solom.finalproject020618.ui.Map_Fragment;

import com.example.solom.finalproject020618.ui.PowerConnectionReceiver;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnLocationClick {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    public MyServiceSearchPlaces service;
    public SharedPreferences preferences;
    public String sharedPrefFile = "com.example.solom.finalproject020618";
    protected Location mLastLocation;
    List_Fragment list_fragment;
    ArrayList<Place> list = new ArrayList<>();
    boolean flag = true;
    private FusedLocationProviderClient mFusedLocationClient;
    private MyReciever reciver;
    private ProgressBar mProgressBar;
    private double latitude1;
    private double longitude1;
    private PowerConnectionReceiver receiverPower;
    private CheckBatteryStatusReceiver batteryStatusReceiver;
    private Spinner mSpinner;
    private ImageView searchChoose;
    private SearchView searchText;
    private ArrayAdapter<CharSequence> typeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//load language

        loadLocale();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        list_fragment = (List_Fragment) getSupportFragmentManager().findFragmentByTag("LIST");

        IntentFilter filter = new IntentFilter("com.example.solom.finalproject020618.MyReciever");
        reciver = new MyReciever();

        LocalBroadcastManager.getInstance(this).registerReceiver(reciver, filter);

        batteryStatusReceiver = new CheckBatteryStatusReceiver();
        registerReceiver(batteryStatusReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

//load the list fragment for phone
        if (findViewById(R.id.smallDevice) != null) {

            list_fragment = new List_Fragment();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().add(R.id.smallDevice, list_fragment, "LIST").addToBackStack("LIST").commit();
        }


        typeAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.places_types, android.R.layout.simple_spinner_dropdown_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mSpinner = findViewById(R.id.spinnerChoose2);
        mSpinner.setAdapter(typeAdapter);

//choose a nearby type search user would like to search for
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                preferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
                String Convert = preferences.getString("Km or Miles", "Kilometers");


                mProgressBar = findViewById(R.id.progress_loading_recycler);
                mProgressBar.setVisibility(View.VISIBLE);

//separate action for kilometers and miles
                try {


                    if (Convert.equals("Kilometers")) {
                        MyServiceSearchPlaces.startActionNEARBY(MainActivity.this, parent.getSelectedItemPosition(), mLastLocation.getLatitude(), mLastLocation.getLongitude());


                    } else {


                        MyServiceSearchPlaces.startActionNEARBY_MILE(MainActivity.this, parent.getSelectedItemPosition(), mLastLocation.getLatitude(), mLastLocation.getLongitude());


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        searchText = findViewById(R.id.searchText2);


//gives the user the option to choose what kind of search he wants - nearby or free text
        searchChoose = findViewById(R.id.searchType);
        searchChoose.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
                dialog.setTitle(Html.fromHtml("<font color='#919191'>" + (getString(R.string.SearchDialog) + "</font>")));
                dialog.setPositiveButton("Category", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        searchText.setVisibility(View.INVISIBLE);
                        mSpinner.setVisibility(View.VISIBLE);

                    }
                });
                dialog.setNegativeButton("Text", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                        //  imm.showSoftInput(searchText, InputMethodManager.SHOW_IMPLICIT);
                        //   imm.showSoftInput(searchText, InputMethodManager.SHOW_FORCED);

                        mSpinner.setVisibility(View.INVISIBLE);
                        searchText.setVisibility(View.VISIBLE);
                        // imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                       /* InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);*/
                        // imm.showSoftInput(searchText, InputMethodManager.SHOW_IMPLICIT);
                        // openKeyboard();

                        showSoftKeyboard(searchText);


                    }
                });
                dialog.show();


            }
        });
        //   InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //imm.showSoftInput(searchText, InputMethodManager.SHOW_IMPLICIT);
        // imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

       /* searchText.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.showSoftInput(view, 0);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    }
                }

            }
        });*/
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener()

        {

            //let user write free text to search for a place
            @Override
            public boolean onQueryTextSubmit(String query) {
                /*InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchText, InputMethodManager.SHOW_IMPLICIT);*/


                preferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
                String Convert = preferences.getString("Km or Miles", "Kilometers");

                mProgressBar = findViewById(R.id.progress_loading_recycler);
                mProgressBar.setVisibility(View.VISIBLE);


                try {
                    if (Convert.equals("Kilometers")) {
                        MyServiceSearchPlaces.startActionBYTEXT(MainActivity.this, query, mLastLocation.getLatitude(), mLastLocation.getLongitude());

                    } else {
                        MyServiceSearchPlaces.startActionBYTEXT_MILE(MainActivity.this, query, mLastLocation.getLatitude(), mLastLocation.getLongitude());

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


//pressing the fab button will create a new map fragment for phone or will ask for last location of the device
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {

                if (findViewById(R.id.smallDevice) != null) {


                    Map_Fragment maps = new Map_Fragment();

                    FragmentManager fm = getSupportFragmentManager();

                    fm.beginTransaction().replace(R.id.smallDevice, maps, "MAPS").addToBackStack("MAPS").commit();

                    mSpinner.setVisibility(View.GONE);
                    searchText.setVisibility(View.GONE);
                    searchChoose.setVisibility(View.GONE);
                } else {
                    getLastLocation();
                }


            }
        });
    }


    private void openKeyboard() {
        searchText.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.showSoftInput(view, 0);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    }
                }

            }
        });
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            //imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            imm.showSoftInput(searchText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    //load language
    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", Activity.MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }


    @Override
    protected void onStart() {

        super.onStart();

//the system cheaks for required permissions. if permissions are not allowd, the system will ask the user for them
        if (!checkPermissions()) {
            requestPermissions();
        } else {

//if permission are granted, the system will look for the device's location

            getLastLocation();

        }

    }

    @Override
    protected void onPause() {
        super.onPause();

//unregister receivers

        unregisterReceiver(receiverPower);

        try {
            if (batteryStatusReceiver != null) {
                unregisterReceiver(batteryStatusReceiver);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

//register receiver

        receiverPower = new PowerConnectionReceiver();

        IntentFilter ifilter = new IntentFilter();
        ifilter.addAction(Intent.ACTION_POWER_CONNECTED);
        ifilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(receiverPower, ifilter);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //unregister receiver

        LocalBroadcastManager.getInstance(this).unregisterReceiver(reciver);
    }

    @Override
    public void onBackPressed() {
        mSpinner.setVisibility(View.VISIBLE);
        searchChoose.setVisibility(View.VISIBLE);
        searchText.setVisibility(View.INVISIBLE);


        int count = getFragmentManager().getBackStackEntryCount();

//if back pressed on phone, the system will go back to the list fragment and try to get device's location
    /*    if (count == 0 && (findViewById(R.id.smallDevice) != null)) {
            super.onBackPressed();
            list_fragment = new List_Fragment();
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.smallDevice, list_fragment, "LIST").addToBackStack("LIST").commit();
            getLastLocation();
//if back pressed on tablet, the system will exit the app
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }*/

        if (count == 0) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);


        if (shouldProvideRationale) {

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });

        } else {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
            onStart();

        }


    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        DBHandler handler = new DBHandler(MainActivity.this);


        //noinspection SimplifiableIfStatement
        if (id == R.id.Settings) {


            return true;
        }


       /* if (id == R.id.favorites) {
            return true;
        }*/


        switch (id) {

//Show list from Database

            case R.id.favorites:
                mSpinner.setVisibility(View.GONE);
                searchText.setVisibility(View.GONE);
                searchChoose.setVisibility(View.GONE);
                list = handler.getAllPlaces();
                list_fragment.setRecyclerView(list);


                break;
//choose language to display
            case R.id.English:
                setLocale("en");
                finish();
                startActivity(getIntent());
                recreate();


                break;

            case R.id.Hebrew:
                setLocale("iw");
                finish();
                startActivity(getIntent());
                recreate();


                break;
//start the main activity
            case R.id.home_button:
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);

                break;

//delte favorite list from DB
            case R.id.deleteList:

                handler.deleteAll();
                list = handler.getAllPlaces();
                list_fragment.setRecyclerView(list);


                break;

//choose between Kilometers or Miles
            case R.id.Miles_km:


                preferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                String Convert = preferences.getString("Km or Miles", "Kilometers");

                if (Convert.equals("Kilometers")) {
                    editor.clear();
                    editor.putString("Km or Miles", "Miles");
                    editor.apply();
                    Toast.makeText(this, "Miles", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent2);


                } else if (Convert.equals("Miles")) {
                    editor.clear();
                    editor.putString("Km or Miles", "Kilometers");
                    editor.apply();
                    Toast.makeText(this, "Km", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent2);


                }


                break;

//Safe exit from app

            case R.id.exitapp:
                Intent intent3 = new Intent(Intent.ACTION_MAIN);
                intent3.addCategory(Intent.CATEGORY_HOME);
                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent3);
                finish();


        }

        return super.onOptionsItemSelected(item);
    }

    private void getLastLocation() {
//system is trying to get device's location and runs the nearby search if successful
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }


        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    mLastLocation = task.getResult();

                    latitude1 = (float) mLastLocation.getLatitude();
                    longitude1 = (float) mLastLocation.getLongitude();


                    preferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
                    String Convert = preferences.getString("Km or Miles", "Kilometers");

                    if (Convert.equals("Kilometers")) {
                        try {
                            MyServiceSearchPlaces.startActionNEARBY(MainActivity.this, 0, mLastLocation.getLatitude(), mLastLocation.getLongitude());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        try {
                            MyServiceSearchPlaces.startActionNEARBY_MILE(MainActivity.this, 0, mLastLocation.getLatitude(), mLastLocation.getLongitude());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
    }

    @Override
    public void onLocationClick(String address, double lat, double lng, String icon, String locationName, double rating, String photoRef, double distance, String converter) {


//pressing an item on the list will lead to the map fragment, and will send the relevant item's data to display on map
        Map_Fragment maps = (Map_Fragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMap);

        Bundle bundle = new Bundle();
        bundle.putString("ADDRESS", address);
        bundle.putDouble("LAT", lat);
        bundle.putDouble("LNG", lng);
        bundle.putString("ICON" , icon);

        bundle.putString("LocationName", locationName);
        bundle.putDouble("RATING", rating);
        bundle.putString("PHOTOREF", photoRef);

        bundle.putDouble("DISTANCE", distance);
        bundle.putString("CONVERTER", converter);


//Tablet

        if (maps != null) {
            maps.getLocation(address, lat, lng, icon,locationName, rating, photoRef, distance, converter);


            maps.setArguments(bundle);

//small device
        } else {
            maps = new Map_Fragment();

            maps.setArguments(bundle);

            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.smallDevice, maps, "MAPS").addToBackStack("MAPS").commit();
            mSpinner.setVisibility(View.GONE);
            searchText.setVisibility(View.GONE);
            searchChoose.setVisibility(View.GONE);
        }


    }


    public class MyReciever extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {

//system is trying to get the JSON and attach it the constructor for further use
            ArrayList<Place> list = new ArrayList<>();

            String jsn = intent.getStringExtra("JSON");
            if (intent.hasExtra("NEARBY")) {

                try {

                    //Toast.makeText(MainActivity.this, intent.getStringExtra("JSON"), Toast.LENGTH_SHORT).show();
                    double lat = 0;
                    double lng = 0;
                    String icon = "";
                    String locationName = "";
                    double rating = 0;
                    String photoRef = "";
                    String address = "";
                    String converter = "";


                    JSONObject object = new JSONObject(jsn);

                    JSONArray arr = object.getJSONArray("results");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject itm = arr.getJSONObject(i);
                        JSONObject geo = itm.getJSONObject("geometry");
                        JSONObject loc = geo.getJSONObject("location");
                        lat = loc.getDouble("lat");
                        lng = loc.getDouble("lng");
                        icon = itm.getString("icon");
                        locationName = itm.getString("name");
                        /*JSONObject hours = itm.getJSONObject("opening_hours");
                        boolean open = hours.getBoolean("open_now");*/
                        if (!itm.isNull("photos")) {
                            JSONArray photos = itm.getJSONArray("photos");
                            JSONObject pic = photos.getJSONObject(0);
                            int height = pic.getInt("height");
                            photoRef = pic.getString("photo_reference");
                            int width = pic.getInt("width");
                        }

                        if (!itm.isNull("rating")) {
                            rating = itm.getDouble("rating");
                        }
                        address = itm.getString("vicinity");

                        Location mylocation = new Location("mylocation");
                        mylocation.setLatitude(latitude1);
                        mylocation.setLongitude(longitude1);
                        double distance = 0.0;
                        Location nextlocation = new Location("nextlocation");
                        nextlocation.setLatitude(lat);
                        nextlocation.setLongitude(lng);
                        distance = mylocation.distanceTo(nextlocation) / 1000;
                        converter = "Km";


                        list.add(new Place(address, lat, lng, icon, locationName, rating, photoRef, distance, converter));
                    }
                    try {
                        list_fragment.setRecyclerView(list);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (intent.hasExtra("BYTEXT")) {

                try {

                    double lat = 0;
                    double lng = 0;
                    String icon = "";
                    String locationName = "";
                    double rating = 0;
                    String photoRef = "";
                    String address = "";
                    String converter = "";

                    JSONObject object = new JSONObject(jsn);

                    JSONArray arr = object.getJSONArray("results");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject itm = arr.getJSONObject(i);
                        address = itm.getString("formatted_address");
                        JSONObject geo = itm.getJSONObject("geometry");
                        JSONObject loc = geo.getJSONObject("location");
                        lat = loc.getDouble("lat");
                        lng = loc.getDouble("lng");
                        icon = itm.getString("icon");
                        locationName = itm.getString("name");
                        /*JSONObject hours = itm.getJSONObject("opening_hours");
                        boolean open = hours.getBoolean("open_now");*/
                        if (!itm.isNull("photos")) {
                            JSONArray photos = itm.getJSONArray("photos");
                            JSONObject pic = photos.getJSONObject(0);
                            int height = pic.getInt("height");
                            photoRef = pic.getString("photo_reference");
                            int width = pic.getInt("width");
                        }

                        if (!itm.isNull("rating")) {
                            rating = itm.getDouble("rating");
                        }

                        Location mylocation = new Location("mylocation");
                        mylocation.setLatitude(latitude1);
                        mylocation.setLongitude(longitude1);
                        double distance = 0.0;
                        Location nextlocation = new Location("nextlocation");
                        nextlocation.setLatitude(lat);
                        nextlocation.setLongitude(lng);
                        distance = mylocation.distanceTo(nextlocation) / 1000;
                        converter = "Km";

                        list.add(new Place(address, lat, lng, icon, locationName, rating, photoRef, distance, converter));


                    }
                    list_fragment.setRecyclerView(list);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else if (intent.hasExtra("NEARBY_MILE")) {

                try {

                    //Toast.makeText(MainActivity.this, intent.getStringExtra("JSON"), Toast.LENGTH_SHORT).show();
                    double lat = 0;
                    double lng = 0;
                    String icon = "";
                    String locationName = "";
                    double rating = 0;
                    String photoRef = "";
                    String address = "";
                    String converter = "";


                    JSONObject object = new JSONObject(jsn);

                    JSONArray arr = object.getJSONArray("results");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject itm = arr.getJSONObject(i);
                        JSONObject geo = itm.getJSONObject("geometry");
                        JSONObject loc = geo.getJSONObject("location");
                        lat = loc.getDouble("lat");
                        lng = loc.getDouble("lng");
                        icon = itm.getString("icon");
                        locationName = itm.getString("name");
                        /*JSONObject hours = itm.getJSONObject("opening_hours");
                        boolean open = hours.getBoolean("open_now");*/
                        if (!itm.isNull("photos")) {
                            JSONArray photos = itm.getJSONArray("photos");
                            JSONObject pic = photos.getJSONObject(0);
                            int height = pic.getInt("height");
                            photoRef = pic.getString("photo_reference");
                            int width = pic.getInt("width");
                        }

                        if (!itm.isNull("rating")) {
                            rating = itm.getDouble("rating");
                        }
                        address = itm.getString("vicinity");

                        Location mylocation = new Location("mylocation");
                        mylocation.setLatitude(latitude1);
                        mylocation.setLongitude(longitude1);
                        double distance = 0.0;
                        Location nextlocation = new Location("nextlocation");
                        nextlocation.setLatitude(lat);
                        nextlocation.setLongitude(lng);
                        distance = mylocation.distanceTo(nextlocation) / 1000;
                        distance = distance * 0.621371;
                        converter = "Miles";


                        list.add(new Place(address, lat, lng, icon, locationName, rating, photoRef, distance, converter));
                    }
                    try {
                        list_fragment.setRecyclerView(list);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (intent.hasExtra("BYTEXT_MILE")) {

                try {

                    double lat = 0;
                    double lng = 0;
                    String icon = "";
                    String locationName = "";
                    double rating = 0;
                    String photoRef = "";
                    String address = "";
                    String converter = "";


                    JSONObject object = new JSONObject(jsn);

                    JSONArray arr = object.getJSONArray("results");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject itm = arr.getJSONObject(i);
                        address = itm.getString("formatted_address");
                        JSONObject geo = itm.getJSONObject("geometry");
                        JSONObject loc = geo.getJSONObject("location");
                        lat = loc.getDouble("lat");
                        lng = loc.getDouble("lng");
                        icon = itm.getString("icon");
                        locationName = itm.getString("name");
                        /*JSONObject hours = itm.getJSONObject("opening_hours");
                        boolean open = hours.getBoolean("open_now");*/
                        if (!itm.isNull("photos")) {
                            JSONArray photos = itm.getJSONArray("photos");
                            JSONObject pic = photos.getJSONObject(0);
                            int height = pic.getInt("height");
                            photoRef = pic.getString("photo_reference");
                            int width = pic.getInt("width");
                        }

                        if (!itm.isNull("rating")) {
                            rating = itm.getDouble("rating");
                        }

                        Location mylocation = new Location("mylocation");
                        mylocation.setLatitude(latitude1);
                        mylocation.setLongitude(longitude1);
                        double distance = 0.0;
                        Location nextlocation = new Location("nextlocation");
                        nextlocation.setLatitude(lat);
                        nextlocation.setLongitude(lng);
                        distance = mylocation.distanceTo(nextlocation) / 1000;
                        distance = distance * 0.621371;
                        converter = "Miles";


                        list.add(new Place(address, lat, lng, icon, locationName, rating, photoRef, distance, converter));


                    }
                    list_fragment.setRecyclerView(list);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }
    }

}
