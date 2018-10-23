package com.example.solom.finalproject020618.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


import java.util.ArrayList;

public class DBHandler {

    private DBHelper helper;

    public DBHandler(Context context) {
        helper = new DBHelper(context, Constants.DATABASE_NAME, null,
                Constants.DATABASE_VERSION);
    }

    public boolean getLocationNameDB(String name) {

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(Constants.TABLE_NAME, null, "location_name=?",
                    new String[]{name}, null, null, null);

        } catch (SQLException e) {
            e.getCause();
        }

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            if (cursor.isNull(cursor.getColumnIndex("_id"))) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public void addPlace(Place place) {

        SQLiteDatabase db = null;

        try {

            db = helper.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(Constants.COLUMN_ADDRESS, place.getAddress());
            values.put(Constants.COLUMN_LAT, place.getLat());
            values.put(Constants.COLUMN_LANG, place.getLng());
            values.put(Constants.COLUMN_ICON, place.getIcon());
            values.put(Constants.COLUMN_NAME, place.getLocationName());
            values.put(Constants.COLUMN_PHOTOREF, place.getPhotoRef());
            // values.put(Constants.COLUMN_PLACE_ID, place.getPlace_id());
            values.put(Constants.COLUMN_RATING, place.getRating());
/*
            values.put(Constants.COLUMN_TYPE, place.getType());
*/
            values.put(Constants.COLUMN_DISTANCE, place.getDistance());
            values.put(Constants.COLUMN_CONVERTER, place.getConverter());


            db.insert(Constants.TABLE_NAME, null, values);

        } catch (SQLiteException e) {

        } finally {
            if (db.isOpen())
                db.close();
        }


    }

    public void deletePlace(String id) {

        SQLiteDatabase db = helper.getWritableDatabase();
        try {


            db.delete(Constants.TABLE_NAME, "_id=?", new String[]{id});
        } catch (SQLiteException e) {

        } finally {
            if (db.isOpen())
                db.close();
        }
    }

    public void deletePlaceByName(String name) {

        SQLiteDatabase db = helper.getWritableDatabase();
        try {


            //db.delete(Constants.TABLE_NAME, "_id=?", new String[]{name});
            db.delete(Constants.TABLE_NAME, "location_name=?", new String[]{name});
        } catch (SQLiteException e) {

        } finally {
            if (db.isOpen())
                db.close();
        }
    }


    public void updatePlace(Place place, String id) {

        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(Constants.COLUMN_ADDRESS, place.getAddress());
            values.put(Constants.COLUMN_LAT, place.getLat());
            values.put(Constants.COLUMN_LANG, place.getLng());
            values.put(Constants.COLUMN_ICON, place.getIcon());
            values.put(Constants.COLUMN_NAME, place.getLocationName());
            values.put(Constants.COLUMN_PHOTOREF, place.getPhotoRef());
            //   values.put(Constants.COLUMN_PLACE_ID, place.getPlace_id());
            values.put(Constants.COLUMN_RATING, place.getRating());
            // values.put(Constants.COLUMN_TYPE, place.getType());
            values.put(Constants.COLUMN_DISTANCE, place.getDistance());
            values.put(Constants.COLUMN_CONVERTER, place.getConverter());


            db.update(Constants.TABLE_NAME, values, "_id=?",
                    new String[]{id});
        } catch (SQLiteException e) {

        } finally {
            if (db.isOpen())
                db.close();
        }
    }

    public ArrayList<Place> getAllPlaces() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = null;
        try {

            cursor = db.query(Constants.TABLE_NAME, null, null,
                    null, null, null, null);

        } catch (SQLiteException e) {
            e.getCause();
        }

        ArrayList<Place> table = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String address = cursor.getString(1);
            double lat = cursor.getDouble(2);
            double lng = cursor.getDouble(3);
            String icon = cursor.getString(4);
            String locationName = cursor.getString(5);
            String photoRef = cursor.getString(6);
            double rating = cursor.getDouble(7);
            // String placeId = cursor.getString(7);
            double distance = cursor.getDouble(8);
            String converter = cursor.getString(9);


            table.add(new Place(address, lat, lng, icon, locationName, rating, photoRef, distance, id, converter));


        }
        return table;
    }

    public Place getPlace(String id1) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = null;
        try {

            cursor = db.query(Constants.TABLE_NAME, null, "_id=?",
                    new String[]{id1}, null, null, null);

        } catch (SQLiteException e) {
            e.getCause();
        }

        Place table;

        cursor.moveToFirst();
        int id = cursor.getInt(0);
        String address = cursor.getString(1);
        double lat = cursor.getDouble(2);
        double lng = cursor.getDouble(3);
        String icon = cursor.getString(4);
        String locationName = cursor.getString(5);
        String photoRef = cursor.getString(6);
        //String placeId = cursor.getString(7);
        double rating = cursor.getDouble(7);
        double distance = cursor.getDouble(8);


        table = new Place(address, lat, lng, icon, locationName, rating, photoRef, distance, id);


        return table;
    }

    public void deleteAll() {

        SQLiteDatabase db = helper.getWritableDatabase();
        try {


            db.delete(Constants.TABLE_NAME, null, null);
        } catch (SQLiteException e) {

        } finally {
            if (db.isOpen())
                db.close();
        }


    }
}
