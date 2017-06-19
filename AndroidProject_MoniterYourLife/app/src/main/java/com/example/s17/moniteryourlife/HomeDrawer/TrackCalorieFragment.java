package com.example.s17.moniteryourlife.HomeDrawer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.s17.moniteryourlife.Config;
import com.example.s17.moniteryourlife.FragementUtil.Report;
import com.example.s17.moniteryourlife.Network.RestClient;
import com.example.s17.moniteryourlife.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.DoubleBuffer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by s17 on 4/18/2016.
 */
public class TrackCalorieFragment extends android.support.v4.app.Fragment {
    private TextView goalTxt;
    private TextView calorieConsumeTxt;
    private TextView calorieBurnedTxt;
    private TextView remainTxt;
    public static TrackCalorieFragment newInstance() {
        TrackCalorieFragment fragment = new TrackCalorieFragment();
        return fragment;
    }

    public TrackCalorieFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview= inflater.inflate(R.layout.track_calorie, container, false);
        goalTxt = (TextView)rootview.findViewById(R.id.goalTxt);
        calorieConsumeTxt = (TextView)rootview.findViewById(R.id.calorieConsumeTxt);
        calorieBurnedTxt = (TextView)rootview.findViewById(R.id.calorieBurnedTxt);
        remainTxt = (TextView)rootview.findViewById(R.id.remainTxt);
        String userName = Config.getUserName(getActivity());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        String date = dateFormat.format(new java.util.Date());
        showReport(userName, date);
        return rootview;
    }

    public void showReport(final String userName, final String date){
        new AsyncTask<String, Void, String >(){
            @Override
            protected String doInBackground(String... params) {

                return RestClient.getReport(params[0],params[1]);
            }
            @Override
            protected void onPostExecute(String s) {
                    try {
                        DecimalFormat df = new DecimalFormat( "0.00");
                        JSONObject reportContent = new JSONObject(s);
                        Report report = new Report(userName, date);
                        if(reportContent.optDouble("reportGoal",0)==0) {
                            goalTxt.setText("No record");
                        }else{
                            Double goal = reportContent.optDouble("reportGoal");
                            goalTxt.setText(df.format(goal)+"cal");
                        }
                        if(reportContent.optDouble("reportcarlorieConsumed",0)==0){
                            calorieConsumeTxt.setText("No record");
                        }else{
                            Double calorieConsume = reportContent.optDouble("reportcarlorieConsumed");
                            calorieConsumeTxt.setText(df.format(calorieConsume)+"cal");
                        }
                        if(reportContent.optDouble("reportcarlorieBurned",0)==0){
                            calorieBurnedTxt.setText("No record");
                        }else{
                            Double calorieBurned = reportContent.optDouble("reportcarlorieBurned");
                            calorieBurnedTxt.setText(df.format(calorieBurned)+"cal");
                        }
                       if(reportContent.optDouble("reportRemaining",0)==0){
                            remainTxt.setText("No record");

                       }else{
                           Double remaining = reportContent.optDouble("reportRemaining");
                           remainTxt.setText(df.format(remaining)+"cal");
                       }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
            }
        }.execute(userName,date);

    }
}
