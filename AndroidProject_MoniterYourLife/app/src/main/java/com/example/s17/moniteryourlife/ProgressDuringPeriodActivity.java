package com.example.s17.moniteryourlife;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.s17.moniteryourlife.HomeDrawer.ProgerssReportFragment;
import com.example.s17.moniteryourlife.Network.RestClient;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ProgressDuringPeriodActivity extends AppCompatActivity {
    private GraphView progressView;
    private EditText beginDate;
    private EditText endDate;
    private Button periodBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_during_period);
        beginDate = (EditText) findViewById(R.id.beginDate);
        endDate = (EditText) findViewById(R.id.endDate);
        progressView = (GraphView) findViewById(R.id.periodGraph);
        progressView.setVisibility(View.INVISIBLE);
        periodBtn = (Button)findViewById(R.id.peroidBtn);
        periodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        String begin = beginDate.getText().toString();
        String end = endDate.getText().toString();
        String userName = Config.getUserName(getApplicationContext());
       if((begin.equals(""))||(end.equals(""))){
            Toast.makeText(ProgressDuringPeriodActivity.this, "Not enough information.", Toast.LENGTH_SHORT).show();
            } else{
                    getUserReportList(userName, begin, end);
               }
            }
        });

    }

    public void getUserReportList(String userName, String beginDate, String endDate) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return RestClient.queryUserReportDuringPeriod(params[0], params[1], params[2]);
            }

            @Override
            protected void onPostExecute(String result) {
                ArrayList<Double> stepsArray = new ArrayList<Double>();
                ArrayList<Double> calorieGoalArray = new ArrayList<Double>();
                ArrayList<Double> calorieConsumeArray = new ArrayList<Double>();
                ArrayList<Double> calorieBurnedArray = new ArrayList<Double>();
                ArrayList<String> dateArray = new ArrayList<String>();
                if (result.isEmpty()) {
                    Toast.makeText(ProgressDuringPeriodActivity.this, "No record during this period", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONArray reportArray = new JSONArray(result);
                        for(int i = 0; i < reportArray.length(); i++) {
                            JSONObject reportItem = reportArray.getJSONObject(i);
                            String originDate = reportItem.optString("dietDate");
                            String dietDate = originDate.substring(5,10);
                            dateArray.add(dietDate);
                            if (reportItem.optInt("reporttotalSteps", 0) == 0) {
                                stepsArray.add(0.0);
                            } else {
                                Integer totalSteps = reportItem.optInt("reporttotalSteps");
                                Double steps = Double.parseDouble(String.valueOf(totalSteps));
                                stepsArray.add(steps);
                            }
                            if (reportItem.optDouble("reportGoal", 0) == 0) {
                                calorieGoalArray.add(0.0);
                            } else {
                                Double goal = reportItem.optDouble("reportGoal");
                                calorieGoalArray.add(goal);
                            }
                            if (reportItem.optDouble("reportcarlorieBurned", 0) == 0) {
                                calorieBurnedArray.add(0.0);
                            } else {
                                Double calorieBurned = reportItem.optDouble("reportcarlorieBurned");
                                calorieBurnedArray.add(calorieBurned);
                            }
                            if (reportItem.optDouble("reportcarlorieConsumed", 0) == 0) {

                                calorieConsumeArray.add(0.0);
                            } else {
                                Double calorieConsumed = reportItem.optDouble("reportcarlorieConsumed");
                                calorieConsumeArray.add(calorieConsumed);
                            }
                                System.out.println(">>>>>>>>>>>>>>>>in dataPoint" + reportItem.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                drawPeroidGraph(stepsArray, calorieGoalArray, calorieConsumeArray,
                        calorieBurnedArray, dateArray);
            }
        }.execute(userName, beginDate, endDate);
    }

    public void  drawPeroidGraph(ArrayList<Double>stepsArray,ArrayList<Double>calorieGoalArray,
                                 ArrayList<Double>calorieConsumeArray,ArrayList<Double> calorieBurnedArray,
                                 ArrayList<String> dateArray){
        LineGraphSeries<DataPoint> stepLineSeries = new LineGraphSeries<DataPoint>();
        LineGraphSeries<DataPoint> calorieGoalSeries = new LineGraphSeries<DataPoint>();
        LineGraphSeries<DataPoint> calorieConsumeSeries = new LineGraphSeries<DataPoint>();
        LineGraphSeries<DataPoint> calorieBurnedSeries = new LineGraphSeries<DataPoint>();
        if(dateArray.size() >= 2) {
            for (int i = 0; i < dateArray.size(); i++) {
                stepLineSeries.appendData(new DataPoint(i, stepsArray.get(i)), false, 10);
                calorieGoalSeries.appendData(new DataPoint(i, calorieGoalArray.get(i)), false, 10);
                calorieBurnedSeries.appendData(new DataPoint(i, calorieBurnedArray.get(i)), false, 10);
                calorieConsumeSeries.appendData(new DataPoint(i, calorieConsumeArray.get(i)), false, 10);
            }
            progressView.setTitle("Report of a period");
            progressView.setTitleTextSize(40);
            stepLineSeries.setDrawDataPoints(true);
            stepLineSeries.setColor(Color.BLUE);
            calorieGoalSeries.setDrawDataPoints(true);
            calorieGoalSeries.setColor(Color.GREEN);
            calorieConsumeSeries.setDrawDataPoints(true);
            calorieConsumeSeries.setColor(Color.RED);
            calorieBurnedSeries.setDrawDataPoints(true);
            calorieBurnedSeries.setColor(Color.YELLOW);
            stepLineSeries.setTitle("Total steps");
            calorieBurnedSeries.setTitle("Calorie Burned");
            calorieConsumeSeries.setTitle("Calorie Consumed");
            calorieGoalSeries.setTitle("Calorie Goal");
            progressView.getLegendRenderer().setVisible(true);
            progressView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
            progressView = (GraphView) findViewById(R.id.periodGraph);
            progressView.addSeries(stepLineSeries);
            progressView.addSeries(calorieGoalSeries);
            progressView.addSeries(calorieBurnedSeries);
            progressView.addSeries(calorieConsumeSeries);
            progressView.setVisibility(View.VISIBLE);
            String[] date = dateArray.toArray(new String[0]);
            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(progressView);
            staticLabelsFormatter.setHorizontalLabels(date);
            progressView.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
            progressView.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
            progressView.getGridLabelRenderer().setLabelsSpace(10);
            progressView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
            progressView.getGridLabelRenderer().setLabelsSpace(10);
        }else if(dateArray.size() == 1){
          String oneDay = dateArray.get(0).toString().substring(5,10);
//            Intent i = new Intent();
//            i.putExtra("Date",date);
//            i.setClass(ProgressDuringPeriodActivity.this, NextViewActivity.class);
//            startActivity(i);
            Toast.makeText(ProgressDuringPeriodActivity.this, "Only has record on "+ oneDay, Toast.LENGTH_SHORT).show();
        }else if(dateArray.size() == 0){
            Toast.makeText(ProgressDuringPeriodActivity.this, "No record", Toast.LENGTH_SHORT).show();
        }
    }
}
