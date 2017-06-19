package com.example.s17.moniteryourlife.HomeDrawer;

import android.content.Intent;
import android.graphics.Color;
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
import com.example.s17.moniteryourlife.Network.RestClient;
import com.example.s17.moniteryourlife.NextViewActivity;
import com.example.s17.moniteryourlife.ProgressDuringPeriodActivity;
import com.example.s17.moniteryourlife.R;
import com.example.s17.moniteryourlife.SQLite.DatabaseHelper;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by s17 on 4/18/2016.
 */
public class ProgerssReportFragment extends android.support.v4.app.Fragment {
    private Button viewPeroid;
    private Button viewSteps;
    private GraphView stepsView;
    private EditText progressDate;
    private Button viewNext;
  //  public  ArrayList<Object> reportData;
    public static ProgerssReportFragment newInstance() {
        ProgerssReportFragment fragment = new ProgerssReportFragment();
        return fragment;
    }

    public ProgerssReportFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.progress_report, container, false);
        viewSteps = (Button) rootView.findViewById(R.id.viewBtn);
        progressDate = (EditText) rootView.findViewById(R.id.progressDate);
        viewPeroid = (Button) rootView.findViewById(R.id.viewPeriodBtn);
        viewNext = (Button)rootView.findViewById(R.id.nextGraphicView);
        stepsView = (GraphView)rootView.findViewById(R.id.stepGraph);
        stepsView.setVisibility(View.INVISIBLE);
        viewSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Method to draw steps record view
                String date = progressDate.getText().toString();
                System.out.println(">>>>>>>>>>>>>>>>>>>>date" + date);
                String userName = Config.getUserName(getActivity());
                if (date.equals("")) {
                    Toast.makeText(getActivity(), "Not enough information.", Toast.LENGTH_SHORT).show();
                } else {
                    drawStepsGraph(userName, date);
                }
            }
        });
        viewPeroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(getActivity(), ProgressDuringPeriodActivity.class);
                startActivity(i);
            }
        });

        viewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                String date = progressDate.getText().toString();
                i.putExtra("Date",date);
                i.setClass(getActivity(), NextViewActivity.class);
                startActivity(i);
            }
        });
        return rootView;
    }

    public void drawStepsGraph(String userName, String date) {
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        ArrayList<Integer> stepsArray = dbHelper.getStepsArray(userName, date);
        ArrayList<String> timeArray = dbHelper.getTimeArray(userName, date);
        if (stepsArray.size() == 0) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>step Array size"+stepsArray.size());
            Toast.makeText(getActivity(), "No steps record on this day.", Toast.LENGTH_SHORT).show();
        } else {
            stepsView.setVisibility(View.VISIBLE);
            stepsView.setTitle("Steps record of a day");
            stepsView.setTitleTextSize(40);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
            for (int i = 0; i < stepsArray.size(); i++) {
                Double steps = Double.parseDouble(stepsArray.get(i).toString());

                series.appendData(new DataPoint(i, steps), true, 10);
            }
            series.setDrawDataPoints(true);
            stepsView.addSeries(series);
            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(stepsView);
            //stepsArray.toArray(sa);
            String[] timeString = (String[]) timeArray.toArray(new String[0]);
            staticLabelsFormatter.setHorizontalLabels(timeString);
            stepsView.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
            stepsView.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
            stepsView.getGridLabelRenderer().setLabelsSpace(10);
            stepsView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
            stepsView.getGridLabelRenderer().setLabelsSpace(10);
        }
    }
    }


