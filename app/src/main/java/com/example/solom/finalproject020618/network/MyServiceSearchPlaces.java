package com.example.solom.finalproject020618.network;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MyServiceSearchPlaces extends IntentService {

/*
    private OnLocationClick callback;
*/


    private static Context mContext;

    private static final String ACTION_NEARBY = "com.example.solom.finalproject020618.network.action.NEARBY";
    private static final String ACTION_NEARBY_MILE = "com.example.solom.finalproject020618.network.action.NEARBY_MILE";
    private static final String ACTION_BYTEXT = "com.example.solom.finalproject020618.network.action.BYTEXT";
    private static final String ACTION_BYTEXT_MILE = "com.example.solom.finalproject020618.network.action.BYTEXT";

    // TODO: Rename parameters
    private static final String EXTRA_LAT = "com.example.solom.finalproject020618.network.extra.LAT";
    private static final String EXTRA_LNG = "com.example.solom.finalproject020618.network.extra.LNG";
    private static final String EXTRA_TYPE = "com.example.solom.finalproject020618.network.extra.TYPE";
    private static final String EXTRA_TEXT = "com.example.solom.finalproject020618.network.extra.TEXT";
    private static final String EXTRA_POSITION = "com.example.solom.finalproject020618.network.extra.POSITION";

    public MyServiceSearchPlaces() {
        super("MyServiceSearchPlaces");


    }


    public static void startActionNEARBY(Context context, int position, double lat, double lng) {

        mContext = context;

        Intent intent = new Intent(context, MyServiceSearchPlaces.class);

        intent.setAction(ACTION_NEARBY);
        //intent.putExtra(EXTRA_TEXT, text);
        intent.putExtra(EXTRA_POSITION, position);
        intent.putExtra(EXTRA_LAT, lat);
        intent.putExtra(EXTRA_LNG, lng);
        //intent.putExtra(EXTRA_TYPE, type);
        context.startService(intent);
    }

    public static void startActionNEARBY_MILE(Context context, int position, double lat, double lng) {

        mContext = context;

        Intent intent = new Intent(context, MyServiceSearchPlaces.class);

        intent.setAction(ACTION_NEARBY_MILE);
        //intent.putExtra(EXTRA_TEXT, text);
        intent.putExtra(EXTRA_POSITION, position);
        intent.putExtra(EXTRA_LAT, lat);
        intent.putExtra(EXTRA_LNG, lng);
        //intent.putExtra(EXTRA_TYPE, type);
        context.startService(intent);
    }


    public static void startActionBYTEXT(Context context, String text, double lat, double lng) {

        mContext = context;


        Intent intent = new Intent(context, MyServiceSearchPlaces.class);
        intent.setAction(ACTION_BYTEXT);
        intent.putExtra(EXTRA_TEXT, text);
        intent.putExtra(EXTRA_LAT, lat);
        intent.putExtra(EXTRA_LNG, lng);
        context.startService(intent);
    }

    public static void startActionBYTEXT_MILE(Context context, String text, double lat, double lng) {

        mContext = context;


        Intent intent = new Intent(context, MyServiceSearchPlaces.class);
        intent.setAction(ACTION_BYTEXT_MILE);
        intent.putExtra(EXTRA_TEXT, text);
        intent.putExtra(EXTRA_LAT, lat);
        intent.putExtra(EXTRA_LNG, lng);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_NEARBY.equals(action)) {
                /*final double lat = Double.valueOf(intent.getStringExtra(EXTRA_LAT));
                final double lng = Double.valueOf(intent.getStringExtra(EXTRA_LNG));*/
                final int position = intent.getIntExtra(EXTRA_POSITION, 0);
                final double lat = intent.getDoubleExtra(EXTRA_LAT, 0.0);
                final double lng = intent.getDoubleExtra(EXTRA_LNG, 0.0);
                //final String type = intent.getStringExtra(EXTRA_TYPE);
                handleActionNEARBY(position, lat, lng);
            } else if (ACTION_BYTEXT.equals(action)) {
                final String text = intent.getStringExtra(EXTRA_TEXT);
                final double lat = intent.getDoubleExtra(EXTRA_LAT, 0.0);
                final double lng = intent.getDoubleExtra(EXTRA_LNG, 0.0);
                handleActionBYTEXT(text, lat, lng);
            } else if (ACTION_NEARBY_MILE.equals(action)) {
                /*final double lat = Double.valueOf(intent.getStringExtra(EXTRA_LAT));
                final double lng = Double.valueOf(intent.getStringExtra(EXTRA_LNG));*/
                final int position = intent.getIntExtra(EXTRA_POSITION, 0);
                final double lat = intent.getDoubleExtra(EXTRA_LAT, 0.0);
                final double lng = intent.getDoubleExtra(EXTRA_LNG, 0.0);
                //final String type = intent.getStringExtra(EXTRA_TYPE);
                handleActionNEARBY_MILES(position, lat, lng);
            } else if (ACTION_BYTEXT_MILE.equals(action)) {
                final String text = intent.getStringExtra(EXTRA_TEXT);
                final double lat = intent.getDoubleExtra(EXTRA_LAT, 0.0);
                final double lng = intent.getDoubleExtra(EXTRA_LNG, 0.0);
                handleActionBYTEXT_MILE(text, lat, lng);
            }
        }
    }


    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionNEARBY(int position, double lat, double lng) {
        String url;
        String jsn;
        Intent intent;
        switch (position) {
            case 0:
                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat +
                        "," + lng + "&radius=1500&type=bar&key=AIzaSyCV6zUzGS_1i-2PJMXCYtsFuEudoxwA0xY";
                jsn = sendHttpRequest(url);
                intent = new Intent("com.example.solom.finalproject020618.MyReciever");
                intent.putExtra("JSON", jsn);
                intent.putExtra("NEARBY", "NEARBY");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                Toast.makeText(mContext, "Bar Selected", Toast.LENGTH_SHORT).show();
                break;


            case 1:
                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat +
                        "," + lng + "&radius=1500&type=bank&key=AIzaSyCV6zUzGS_1i-2PJMXCYtsFuEudoxwA0xY";
                jsn = sendHttpRequest(url);
                intent = new Intent("com.example.solom.finalproject020618.MyReciever");
                intent.putExtra("JSON", jsn);
                intent.putExtra("NEARBY", "NEARBY");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                Toast.makeText(mContext, "Bank Selected", Toast.LENGTH_SHORT).show();

                break;

            case 2:
                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat +
                        "," + lng + "&radius=1500&type=cafe&key=AIzaSyCV6zUzGS_1i-2PJMXCYtsFuEudoxwA0xY";
                jsn = sendHttpRequest(url);
                intent = new Intent("com.example.solom.finalproject020618.MyReciever");
                intent.putExtra("JSON", jsn);
                intent.putExtra("NEARBY", "NEARBY");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                Toast.makeText(mContext, "Café Selected", Toast.LENGTH_SHORT).show();

                break;

            case 3:
                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat +
                        "," + lng + "&radius=1500&type=bakery&key=AIzaSyCV6zUzGS_1i-2PJMXCYtsFuEudoxwA0xY";
                jsn = sendHttpRequest(url);
                intent = new Intent("com.example.solom.finalproject020618.MyReciever");
                intent.putExtra("JSON", jsn);
                intent.putExtra("NEARBY", "NEARBY");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                Toast.makeText(mContext, "Bakery Selected", Toast.LENGTH_SHORT).show();

                break;

            case 4:
                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat +
                        "," + lng + "&radius=1500&type=ATM&key=AIzaSyCV6zUzGS_1i-2PJMXCYtsFuEudoxwA0xY";
                jsn = sendHttpRequest(url);
                intent = new Intent("com.example.solom.finalproject020618.MyReciever");
                intent.putExtra("JSON", jsn);
                intent.putExtra("NEARBY", "NEARBY");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                Toast.makeText(mContext, "ATM Selected", Toast.LENGTH_SHORT).show();


                break;

            case 5:
                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat +
                        "," + lng + "&radius=1500&type=supermarket&key=AIzaSyCV6zUzGS_1i-2PJMXCYtsFuEudoxwA0xY";
                jsn = sendHttpRequest(url);
                intent = new Intent("com.example.solom.finalproject020618.MyReciever");
                intent.putExtra("JSON", jsn);
                intent.putExtra("NEARBY", "NEARBY");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                Toast.makeText(mContext, "Supermarket Selected", Toast.LENGTH_SHORT).show();

                break;

            case 6:
                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat +
                        "," + lng + "&radius=1500&type=store&key=AIzaSyCV6zUzGS_1i-2PJMXCYtsFuEudoxwA0xY";
                jsn = sendHttpRequest(url);
                intent = new Intent("com.example.solom.finalproject020618.MyReciever");
                intent.putExtra("JSON", jsn);
                intent.putExtra("NEARBY", "NEARBY");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                Toast.makeText(mContext, "Store Selected", Toast.LENGTH_SHORT).show();

                break;

        }


    }

    private void handleActionNEARBY_MILES(int position, double lat, double lng) {
        String url;
        String jsn;
        Intent intent;
        switch (position) {
            case 0:
                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat +
                        "," + lng + "&radius=1500&type=bar&key=AIzaSyCV6zUzGS_1i-2PJMXCYtsFuEudoxwA0xY";
                jsn = sendHttpRequest(url);
                intent = new Intent("com.example.solom.finalproject020618.MyReciever");
                intent.putExtra("JSON", jsn);
                intent.putExtra("NEARBY_MILE", "NEARBY_MILE");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                Toast.makeText(mContext, "Bar Selected", Toast.LENGTH_SHORT).show();
                break;


            case 1:
                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat +
                        "," + lng + "&radius=1500&type=bank&key=AIzaSyCV6zUzGS_1i-2PJMXCYtsFuEudoxwA0xY";
                jsn = sendHttpRequest(url);
                intent = new Intent("com.example.solom.finalproject020618.MyReciever");
                intent.putExtra("JSON", jsn);
                intent.putExtra("NEARBY_MILE", "NEARBY_MILE");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                Toast.makeText(mContext, "Bank Selected", Toast.LENGTH_SHORT).show();

                break;

            case 2:
                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat +
                        "," + lng + "&radius=1500&type=cafe&key=AIzaSyCV6zUzGS_1i-2PJMXCYtsFuEudoxwA0xY";
                jsn = sendHttpRequest(url);
                intent = new Intent("com.example.solom.finalproject020618.MyReciever");
                intent.putExtra("JSON", jsn);
                intent.putExtra("NEARBY_MILE", "NEARBY_MILE");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                Toast.makeText(mContext, "Café Selected", Toast.LENGTH_SHORT).show();

                break;

            case 3:
                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat +
                        "," + lng + "&radius=1500&type=bakery&key=AIzaSyCV6zUzGS_1i-2PJMXCYtsFuEudoxwA0xY";
                jsn = sendHttpRequest(url);
                intent = new Intent("com.example.solom.finalproject020618.MyReciever");
                intent.putExtra("JSON", jsn);
                intent.putExtra("NEARBY_MILE", "NEARBY_MILE");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                Toast.makeText(mContext, "Bakery Selected", Toast.LENGTH_SHORT).show();

                break;

            case 4:
                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat +
                        "," + lng + "&radius=1500&type=ATM&key=AIzaSyCV6zUzGS_1i-2PJMXCYtsFuEudoxwA0xY";
                jsn = sendHttpRequest(url);
                intent = new Intent("com.example.solom.finalproject020618.MyReciever");
                intent.putExtra("JSON", jsn);
                intent.putExtra("NEARBY_MILE", "NEARBY_MILE");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                Toast.makeText(mContext, "ATM Selected", Toast.LENGTH_SHORT).show();


                break;

            case 5:
                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat +
                        "," + lng + "&radius=1500&type=supermarket&key=AIzaSyCV6zUzGS_1i-2PJMXCYtsFuEudoxwA0xY";
                jsn = sendHttpRequest(url);
                intent = new Intent("com.example.solom.finalproject020618.MyReciever");
                intent.putExtra("JSON", jsn);
                intent.putExtra("NEARBY_MILE", "NEARBY_MILE");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                Toast.makeText(mContext, "Supermarket Selected", Toast.LENGTH_SHORT).show();

                break;

            case 6:
                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat +
                        "," + lng + "&radius=1500&type=store&key=AIzaSyCV6zUzGS_1i-2PJMXCYtsFuEudoxwA0xY";
                jsn = sendHttpRequest(url);
                intent = new Intent("com.example.solom.finalproject020618.MyReciever");
                intent.putExtra("JSON", jsn);
                intent.putExtra("NEARBY_MILE", "NEARBY_MILE");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                Toast.makeText(mContext, "Store Selected", Toast.LENGTH_SHORT).show();

                break;

        }


    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBYTEXT(String text, double lat, double lng) {

        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + text +
                "&location=" + lat + "," + lng + "&radius=1500&key=AIzaSyCV6zUzGS_1i-2PJMXCYtsFuEudoxwA0xY";
        String jsn = sendHttpRequest(url);
        Intent intent = new Intent("com.example.solom.finalproject020618.MyReciever");
        intent.putExtra("BYTEXT", "BYTEXT");
        intent.putExtra("JSON", jsn);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);


    }
    private void handleActionBYTEXT_MILE(String text, double lat, double lng) {

        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + text +
                "&location=" + lat + "," + lng + "&radius=1500&key=AIzaSyCV6zUzGS_1i-2PJMXCYtsFuEudoxwA0xY";
        String jsn = sendHttpRequest(url);
        Intent intent = new Intent("com.example.solom.finalproject020618.MyReciever");
        intent.putExtra("BYTEXT_MILE", "BYTEXT_MILE");
        intent.putExtra("JSON", jsn);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);


    }


    private String sendHttpRequest(String urlString) {
        BufferedReader input = null;
        HttpURLConnection httpCon = null;
        InputStream input_stream = null;
        InputStreamReader input_stream_reader = null;
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(urlString);
            httpCon = (HttpURLConnection) url.openConnection();
            if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e("TAG", "Cannot Connect to : " + urlString);

                return null;
            }

            input_stream = httpCon.getInputStream();
            input_stream_reader = new InputStreamReader(input_stream);
            input = new BufferedReader(input_stream_reader);
            String line;
            while ((line = input.readLine()) != null) {
                response.append(line + "\n");
            }


        } catch (Exception e) {
            e.printStackTrace();


        } finally {
            if (input != null) {
                try {
                    input_stream_reader.close();
                    input_stream.close();
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }
                if (httpCon != null) {
                    httpCon.disconnect();
                }
            }
        }
        return response.toString();
    }
}
