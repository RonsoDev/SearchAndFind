package com.example.solom.finalproject020618.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.solom.finalproject020618.MainActivity;
import com.example.solom.finalproject020618.R;
import com.squareup.picasso.Picasso;

public class Marker_Location extends AppCompatActivity {


    ImageView bigPhoto;
    TextView location_info_name_set;
    TextView location_info_address_set;
    RatingBar location_info_ratingBar;
    TextView location_info_ratingNumber;
    TextView location_info_distance_set;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marker__location);


        bigPhoto = findViewById(R.id.bigPhoto);
        location_info_name_set = findViewById(R.id.location_info_name_set);
        location_info_address_set = findViewById(R.id.location_info_address_set);
        location_info_ratingBar = findViewById(R.id.location_info_ratingBar);
        location_info_ratingNumber = findViewById(R.id.location_info_ratingNumber);
        location_info_distance_set = findViewById(R.id.location_info_distance_set);

        location_info_name_set.setText(getIntent().getStringExtra("LocationName"));
        location_info_address_set.setText(getIntent().getStringExtra("ADDRESS"));
        location_info_ratingNumber.setText(String.valueOf(getIntent().getDoubleExtra("RATING", 0.0)));
        location_info_ratingBar.setRating(Float.parseFloat((String.valueOf(getIntent().getDoubleExtra("RATING", 0.0)))));
        location_info_distance_set.setText(String.format("%.2f", getIntent().getDoubleExtra("DISTANCE", 0.0)) + " " + getIntent().getStringExtra("CONVERTER"));
        Picasso.get().load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + getIntent().getStringExtra("PHOTOREF") + "&key=AIzaSyCV6zUzGS_1i-2PJMXCYtsFuEudoxwA0xY").into(bigPhoto);

        ImageView shareButton = findViewById(R.id.shareImage);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent waIntent = new Intent(Intent.ACTION_SEND);
                waIntent.setType("text/plain");
                String text = "Check Out This Place:" + "\n" +
                        " " + location_info_name_set.getText().toString() + "\n" + location_info_address_set.getText().toString();

//sharing the location information with other users using other apps (whatsapp, gmail etc')
                waIntent.putExtra(Intent.EXTRA_TEXT, text);

                try {
                    startActivity(Intent.createChooser(waIntent, "Share with a friend"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Marker_Location.this, "There are no Sharing clients installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {

//system is starting the main activity
        Intent intent = new Intent(Marker_Location.this, MainActivity.class);
        startActivity(intent);

    }


}



