package com.example.s17.moniteryourlife.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.s17.moniteryourlife.FragementUtil.Location;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by s17 on 4/14/2016.
 * DatabaseHelper class extends SQLiteOpenHelper which designed to create and manage the database.
 * There, we can build database tables,upgrade tables or data and add any database method required
 * to interact with the database.
 */
public class DatabaseHelper  extends SQLiteOpenHelper {

    //Set database properties
    public static final String DATABASE_NAME = "CalorieDB";
    public static final int DATABASE_VERSION = 1;

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER.CREATE_STATEMENT);
        db.execSQL(STEP.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + STEP.TABLE_NAME);
        onCreate(db);
    }

    //User database method
    public void addUser(USER user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER.COLUMN_NAME, user.getName());
        values.put(USER.COLUMN_PASSWORD, user.getPassword());
        values.put(USER.COLUMN_REGISTER_DATE, user.getRegister_date());
        values.put(USER.COLUMN_LATITUDE, user.getLatitude());
        values.put(USER.COLUMN_LONGITUDE, user.getLongitude());
        db.insert(USER.TABLE_NAME, null, values);
        db.close();
    }

    //This method can be used to verify the existing user,check whether the same user name has already existed
    public boolean getUsers(String userName, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from user where name=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[]{userName, password});
        if (cursor.moveToFirst() == true) {
            cursor.close();
            return true;
        }
        return false;
    }

    public boolean findUsername(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from user where name=? ";
        Cursor cursor = db.rawQuery(sql, new String[]{userName});
        if (cursor.moveToFirst() == true) {
            cursor.close();
            return true;
        }
        return false;
    }

    public long findUserId(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from user where name=? ";
        Cursor cursor = db.rawQuery(sql, new String[]{userName});
        if (cursor.moveToFirst() == true) {
            long userId = cursor.getLong(0);
            cursor.close();
            return userId;
        }
        return -1;
    }


    public void setLocation(String userName, Location location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER.COLUMN_LATITUDE, location.getLatitude());
        db.update("user", values, "name=?", new String[]{userName});
        values.put(USER.COLUMN_LONGITUDE, location.getLongtitude());
        db.update("user", values, "name=?", new String[]{userName});
    }

    //Step table methods
    public void addStep(String userName, int steps) {
        long userId = findUserId(userName);
        if (userId != -1) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
            String date = dateFormat.format(new java.util.Date());
            SimpleDateFormat timFormat = new SimpleDateFormat("hh:mm:ss");
            String time = timFormat.format(new java.util.Date());
            STEP step = new STEP(userId, date, time, steps);

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(STEP.COLUMN_USER_ID, step.getUser_id());
            values.put(STEP.COLUMN_DATE, step.getDate());
            values.put(STEP.COLUMN_TIME, step.getTime());
            values.put(STEP.COLUMN_STEPS, step.getSteps());
            db.insert(STEP.TABLE_NAME, null, values);
            db.close();
        }
    }

    public int totalSteps(String userName, String date) {
        int totalSteps = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        long userId = findUserId(userName);
        String sql = "select * from step where user_id=" + userId + " and date= " + "date";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int steps = cursor.getInt(4);
                totalSteps += steps;
            } while (cursor.moveToNext());
            cursor.close();
            return totalSteps;
        }
        return -1;
    }

    public  ArrayList<Integer> getStepsArray(String userName, String date) {
        ArrayList<Integer> stepsArray = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        long userId = findUserId(userName);
        String sql = "select * from step where user_id=" + userId + " and date= " + "date";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int step = cursor.getInt(4);
                stepsArray.add(step);
            } while (cursor.moveToNext());
        }
        return stepsArray;
    }

    public ArrayList<String> getTimeArray(String userName, String date){
        ArrayList<String> timeArray = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        long userId = findUserId(userName);
        String sql = "select * from step where user_id=" + userId + " and date= " + "date";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String time = cursor.getString(3);
                timeArray.add(time);
            } while (cursor.moveToNext());
        }
        return timeArray;
    }
}