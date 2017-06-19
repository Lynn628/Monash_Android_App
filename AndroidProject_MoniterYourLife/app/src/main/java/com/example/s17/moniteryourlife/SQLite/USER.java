package com.example.s17.moniteryourlife.SQLite;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by s17 on 4/13/2016.
 */
public class USER implements Parcelable {
    //Database constants
    public static final String TABLE_NAME = "user";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PASSWORD ="password";
    public static final String COLUMN_REGISTER_DATE ="register_date";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String CREATE_STATEMENT =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    COLUMN_NAME + " TEXT NOT NULL UNIQUE, " +
                    COLUMN_PASSWORD + " TEXT NOT NULL, " +
                    COLUMN_REGISTER_DATE + " TEXT NOT NULL, " +
                    COLUMN_LATITUDE  + " REAL, " +
                    COLUMN_LONGITUDE + " REAL " +
                    ")";
    //Properties
    private long id;
    private String name;
    private String password;
    //define the register date type as String
    private String register_date;
    private double latitude;
    private double longitude;
    //Static method to create Parcelable object
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public USER createFromParcel(Parcel in){ return new USER(in);}
        public USER[] newArray(int size){return new USER[size];}
    };

    //Default constructor with required params
    public USER(long id, String name, String password, String register_date,
                double latitude, double longitude){
        this.id = id;
        this.name = name;
        this.password = password;
        this.register_date = register_date;
        this.latitude = latitude;
        this.longitude =longitude;
    }
    //Basic constructor for creating a new user
    public USER(String name, String password, String register_date,
                double latitude, double longitude){
        this.name = name;
        this.password = password;
        this.register_date = register_date;
        this.latitude = latitude;
        this.longitude =longitude;
    }

    public USER(String name, String password){
        this.name = name;
        this.password = password;
    }

    public USER(Parcel in){
        this.id = in.readLong();
        this.name = in.readString();
        this.register_date = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //Method to write values to parcel in a specific order

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(password);
        dest.writeString(register_date);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}
