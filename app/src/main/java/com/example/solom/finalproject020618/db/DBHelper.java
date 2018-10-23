package com.example.solom.finalproject020618.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context, String name,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        try {

            String cmd = "CREATE TABLE " + Constants.TABLE_NAME +
                    " (" + Constants.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, "
                    + Constants.COLUMN_ADDRESS + " TEXT, " +
                    Constants.COLUMN_LAT + " TEXT, " +
                    Constants.COLUMN_LANG + " TEXT, " +
                    Constants.COLUMN_ICON + " TEXT, " +
                    Constants.COLUMN_NAME + " TEXT, " +
                    Constants.COLUMN_PHOTOREF + " TEXT, " +
                    Constants.COLUMN_RATING + " TEXT, " +
                    Constants.COLUMN_DISTANCE + " TEXT, " +
                    Constants.COLUMN_CONVERTER + " TEXT);";

            db.execSQL(cmd);

        } catch (SQLException e) {
            e.getCause();
        }

//        Constants.COLUMN_TYPE + " TEXT
//        Constants.COLUMN_PLACE_ID + " TEXT, " +


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
