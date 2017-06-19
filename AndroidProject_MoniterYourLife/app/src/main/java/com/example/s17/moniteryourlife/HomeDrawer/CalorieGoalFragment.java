package com.example.s17.moniteryourlife.HomeDrawer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s17.moniteryourlife.Config;
import com.example.s17.moniteryourlife.FragementUtil.Report;
import com.example.s17.moniteryourlife.Network.RestClient;
import com.example.s17.moniteryourlife.R;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;

/**
 * Created by s17 on 4/18/2016.
 */
public class CalorieGoalFragment extends android.support.v4.app.Fragment {
    private EditText calorieGoal;
    private TextView timeTxt;
    private Button resetBtn;
    private TextView showCalorie;

    // private String calorieGoalStr;
    public static CalorieGoalFragment newInstance() {
        CalorieGoalFragment fragment = new CalorieGoalFragment();
        return fragment;
    }

    public CalorieGoalFragment() {
    }

    public void setArgument(Bundle bundle) {
        super.setArguments(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calorie_goal, container, false);
        timeTxt = (TextView) rootView.findViewById(R.id.timeTxt);
        calorieGoal = (EditText) rootView.findViewById(R.id.calorieGoalEditV);
        showCalorie = (TextView) rootView.findViewById(R.id.showCalorie);
        resetBtn = (Button) rootView.findViewById(R.id.resetBtn);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        final String date = dateFormat.format(new java.util.Date());
//        System.out.println(">>>>>>>>>>>>>>>>Today"+date);
        final String userName = Config.getUserName(getActivity());
        showCalorieGoal(userName, date);
        timeTxt.setText("Today is : " + date);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String calorieGoalStr = calorieGoal.getText().toString();
                Report report = new Report(userName, date, calorieGoalStr);
                updateCalorieGoal(report);
            }
        });
        return rootView;
    }

    public void updateCalorieGoal(Report report) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return RestClient.updateCalorieGoal(params[0], params[1], params[2]);
            }

            @Override
            protected void onPostExecute(String s) {
                if (s.equals("OK")) {
                    Toast.makeText(getActivity(), "Successfully updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(report.getUserName(), report.getDietDate(), report.getReportGoal());
    }


    public void showCalorieGoal(String userName, String dietDate) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return RestClient.getCalorieGoal(params[0], params[1]);
            }

            @Override
            protected void onPostExecute(String s) {
                if (s.equals("Not Found")||s.equals("")) {
                    showCalorie.setText("No Record");
                } else {
                    System.out.println(">>>>>>>>>>>>>>>>>>>>"+s);
                    showCalorie.setText(s);
                }
            }
        }.execute(userName, dietDate);
    }
}


