package com.example.s17.moniteryourlife.HomeDrawer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.s17.moniteryourlife.Config;
import com.example.s17.moniteryourlife.FragementUtil.Report;
import com.example.s17.moniteryourlife.FragementUtil.ReportStep;
import com.example.s17.moniteryourlife.Network.RestClient;
import com.example.s17.moniteryourlife.R;
import com.example.s17.moniteryourlife.SQLite.DatabaseHelper;

import java.text.SimpleDateFormat;


/**
 * Created by s17 on 4/16/2016.
 */
public class StepFragment  extends android.support.v4.app.Fragment {
    private Button submitBtn;
    private Button totalStepBtn;
    private EditText stepsEditV;
    private DatabaseHelper dbHelper;
    public static StepFragment newInstance() {
        StepFragment fragment = new StepFragment();
        return fragment;
    }

    public StepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step, container, false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        final String currentDate = dateFormat.format(new java.util.Date());
        final String userName = Config.getUserName(getActivity());
        dbHelper = new DatabaseHelper(getActivity().getApplicationContext());
        stepsEditV = (EditText)rootView.findViewById(R.id.stepEditV);
        submitBtn = (Button)rootView.findViewById(R.id.submitBtn);
        totalStepBtn = (Button)rootView.findViewById(R.id.totalStepBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stepStr = stepsEditV.getText().toString();
                if(!"".equals(stepStr)){
                    int steps = Integer.parseInt(stepStr);
                    addStepsToLocal(Config.getUserName(getActivity()), steps);
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                }
            }
        });

        totalStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
                String currentDate = dateFormat.format(new java.util.Date());
                Integer totoalSteps = dbHelper.totalSteps(Config.getUserName(getActivity()), currentDate);
                String totalStepsStr = totoalSteps.toString();
                ReportStep step = new ReportStep(userName,currentDate,totalStepsStr);
                //When total steps send to the server, the server will calculate the total calorie burned of user on that day,
                //and insert int the report table
                addStepsToServer(userName, currentDate, totalStepsStr);
                Toast.makeText(getActivity(), "You have walked "+ totalStepsStr +" today", Toast.LENGTH_SHORT).show();

            }
        });
        return rootView;
    }

    public void addStepsToLocal(String userName, int steps){
        dbHelper.addStep(Config.getUserName(getActivity()), steps);
    }

    public void addStepsToServer(final String userName,final String date,final String totoalSteps){
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return RestClient.daliySteps(params[0],params[1],params[2]);
            }
            @Override
            protected void onPostExecute(String s) {
                if (s.equals("OK")) {
                    Toast.makeText(getActivity(), "Steps has successfully updated", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(userName,date,totoalSteps);
    }

}
