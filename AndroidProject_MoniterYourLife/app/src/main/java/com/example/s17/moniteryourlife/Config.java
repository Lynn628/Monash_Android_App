package com.example.s17.moniteryourlife;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by s17 on 4/17/2016.
 */
public class Config {
    public static final String USER_NAME = "userName";
    public static void cacheUserName(Context context,String userName){
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(USER_NAME,userName);
        editor.commit();
    }

    public static String getUserName(Context context){
        return context.getSharedPreferences("config",Context.MODE_PRIVATE).getString(USER_NAME,"");
    }

    final String LOGIN = "http://localhost:8080/CalorieCounter/webresources/entities.user/login";
    final String REGISTER = "http://localhost:8080/CalorieCounter/webresources/entities.user/register";

}
