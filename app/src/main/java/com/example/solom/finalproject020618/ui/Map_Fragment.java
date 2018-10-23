package com.example.solom.finalproject020618.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.solom.finalproject020618.MainActivity;
import com.example.solom.finalproject020618.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

public class Map_Fragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener {

    private FusedLocationProviderClient mFusedLocationProviderClient;
    protected Location lastLocation;

    private GoogleMap mMap;
    private SearchView mSearchView;


    @Override
    public void onStart() {
        super.onStart();
        getMapAsync(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());


    }


    private void getLastLocation() {

//system is trying to get device's location and runs the nearby search if successful


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }


        mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    lastLocation = task.getResult();

                    LatLng myLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(myLocation).title("You Are Here"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(17), 3000, null);

                    Toast.makeText(getContext(), R.string.youAreHere, Toast.LENGTH_SHORT).show();


                }
            }
        });


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(this);

//system gets relevant data from the list fragment to show it on the map. if there is no data to display, the system will look for the device's location

        Bundle bundle = getArguments();
        if (bundle != null) {
            try {
              /*  getLocation(bundle.getDouble("LAT"), bundle.getDouble("LNG"), bundle.getString("ADDRESS"),
                        bundle.getString("LocationName"), bundle.getDouble("DISTANCE"), bundle.getDouble("RATING"),
                        bundle.getString("CONVERTER"));*/

              getLocation(bundle.getString("ADDRESS"), bundle.getDouble("LAT"), bundle.getDouble("LNG"), bundle.getString("ICON"),
              bundle.getString("LocationName"),  bundle.getDouble("RATING"), bundle.getString("PHOTOREF"),
                      bundle.getDouble("DISTANCE"), bundle.getString("CONVERTER"));

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            getLastLocation();
        }

        mMap.setInfoWindowAdapter(this);

    }

    public void getLocation(String address, double lat, double lng, String icon, String locationName, double rating, String photoRef,
                            double distance, String converter) {

//the system is finding device's location and showing it on map with a marker
        if (mMap != null) {


            LatLng itemLocation = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(itemLocation));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(itemLocation));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17), 3000, null);


        }
    }


    @Override
    public void onInfoWindowClick(Marker marker) {

//system is sending relevant data on specific location to show in a new activity
        Bundle bundle = getArguments();


        String locationName = bundle.getString("LocationName");
        String address = bundle.getString("ADDRESS");
        String photoRef = bundle.getString("PHOTOREF");
        double rating = bundle.getDouble("RATING");
        double distance = bundle.getDouble("DISTANCE");
        String converter = bundle.getString("CONVERTER");

        Intent intent = new Intent(getContext(), Marker_Location.class);
        intent.putExtra("LocationName", locationName);
        intent.putExtra("ADDRESS", address);
        intent.putExtra("PHOTOREF", photoRef);
        intent.putExtra("RATING", rating);
        intent.putExtra("DISTANCE", distance);
        intent.putExtra("CONVERTER", converter);
        startActivity(intent);


    }


    @Override
    public View getInfoWindow(Marker marker) {

//system is displaying some short description when pressing the marker
        Bundle bundle = getArguments();

        View v = getLayoutInflater().inflate(R.layout.marker_info, null, false);


        try {


            TextView title = v.findViewById(R.id.marker_Info_name);
            title.setText(bundle.getString("LocationName"));
            ImageView someImage = v.findViewById(R.id.someImage);
            TextView address = v.findViewById(R.id.marker_info_address);
            address.setText(bundle.getString("ADDRESS"));
            Picasso.get().load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + bundle.getString("PHOTOREF") + "&key=AIzaSyCV6zUzGS_1i-2PJMXCYtsFuEudoxwA0xY").into(someImage);


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {


        return null;
    }

}
