package com.example.s17.moniteryourlife;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.s17.moniteryourlife.Network.RestClient;
import com.example.s17.moniteryourlife.SQLite.DatabaseHelper;
import com.example.s17.moniteryourlife.SQLite.USER;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataEntryActivity extends AppCompatActivity {
    private Button submitButton;
    private EditText ageEditV;
    private EditText heightEditV;
    private EditText weightEditV;
    private EditText stpPMileEditV;
    private RadioGroup genderGroup;
    private Spinner activitySpinner;
    private List<String>  activity_list;
    private ArrayAdapter<String> activity_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        activity_list = new ArrayList<String>();
        activitySpinner = (Spinner)findViewById(R.id.activity_spinner);
        activity_list.add("Little/No exercise");
        activity_list.add("Lightly Active" );
        activity_list.add("Moderately active");
        activity_list.add("Very active");
        activity_list.add("Extra active");
        activity_adapter = new ArrayAdapter<String>(DataEntryActivity.this,
                android.R.layout.simple_spinner_item, activity_list);
        activitySpinner.setAdapter(activity_adapter);
        AddUserToRestDB();
        submitButton = (Button)findViewById(R.id.registerbtn2);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject userJson = AddUserToRestDB();
                new AsyncTask<JSONObject, Void, String>() {
                    @Override
                    protected String doInBackground(JSONObject... params) {
                        return RestClient.insertUser(params[0]);
                    }
                    @Override
                    protected void onPostExecute(String result) {
                            Intent i = new Intent();
                            i.setClass(DataEntryActivity.this, LoginActivity.class);
                            startActivity(i);
                    }
                }.execute(userJson);
            }
        });

    }

    public JSONObject AddUserToRestDB(){
        String userName;
        String userAge;
        String userHeight;
        String userWeight;
        String stepPMile;
        String gender = null;
        String level_Of_Activity ;
        long radioBtnId;
        userName = Config.getUserName(DataEntryActivity.this);
        ageEditV = (EditText)findViewById(R.id.ageEdit);
        heightEditV = (EditText)findViewById(R.id.heightEdit);
        weightEditV = (EditText)findViewById(R.id.weightEdit);
        stpPMileEditV = (EditText)findViewById(R.id.stepPMileEdit);
        genderGroup = (RadioGroup)findViewById(R.id.radioGroup);
        userHeight = heightEditV.getText().toString();
        userWeight = weightEditV.getText().toString();
        userAge = ageEditV.getText().toString();
        stepPMile = stpPMileEditV.getText().toString();
        radioBtnId = genderGroup.getCheckedRadioButtonId ();
        if(radioBtnId == R.id.radioMale )
        {
            gender="M";
        }else if(radioBtnId == R.id.radioFemale){
            gender="F";
        }
        level_Of_Activity = (String)activitySpinner.getSelectedItem();
        String level= null;
        switch(level_Of_Activity){
            case "Little/No exercise":
                level = "1";
                break;
            case "Lightly Active":
                level = "2";
                break;
            case "Moderately active":
                level = "3";
                break;
            case "Very active":
                level = "4";
                break;
            case "Extra active":
                level = "5";
                break;
        }
        System.out.println(gender);
       try {
           JSONObject userJson = new JSONObject();
           userJson.put("userName", userName);
           userJson.put("userAge", userAge);
           userJson.put("userGender", gender);
           userJson.put("userHeight", userHeight);
           userJson.put("userWeight", userWeight);
           userJson.put("userlevelOfActivity", level);
           userJson.put("userstepPerMiles",stepPMile);
          return userJson;
       }catch(Exception e){
           e.printStackTrace();
       }
        return null;
    }
        }

