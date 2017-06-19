package com.example.s17.moniteryourlife.SQLite;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by s17 on 4/14/2016.
 */
public class STEP implements Parcelable {
    //Database constants
    public static final String TABLE_NAME = "step";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME ="time";
    public static final String COLUMN_STEPS = "steps";
    public static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_USER_ID + " INTEGER NOT NULL, " +
                    COLUMN_DATE + " TEXT NOT NULL, " +
                    COLUMN_TIME + " TEXT NOT NULL, " +
                    COLUMN_STEPS  + " INTEGER NOT NULL, " +
                    " FOREIGN KEY(user_id) REFERENCES USER(id) " +
                    ")";
    //Properties
    private long id;
    private long user_id;
    private String date;
    //define the register date type as String
    private String time;
    private int steps;

    //Static method to create Parcelable object
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public STEP createFromParcel(Parcel in){ return new STEP(in);}
        public STEP[] newArray(int size){return new STEP[size];}
    };

    //Default constructor with required params
    public STEP(long id, long user_id, String date, String time, int steps){
        this.id = id;
        this.user_id = user_id;
        this.date = date;
        this.time = time;
        this.steps = steps;
    }
    //Basic constructor for creating a new record
    public STEP(long user_id, String date, String time, int steps){
        this.user_id = user_id;
        this.date = date;
        this.time = time;
        this.steps = steps;
    }


    public STEP(Parcel in){
        this.id = in.readLong();
        this.user_id = in.readLong();
        this.date = in.readString();
        this.time = in.readString();
        this.steps = in.readInt();

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //Method to write values to parcel in a specific order

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(user_id);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeInt(steps);
    }
}
