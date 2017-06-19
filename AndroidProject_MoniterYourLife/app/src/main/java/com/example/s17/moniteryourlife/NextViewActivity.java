package com.example.s17.moniteryourlife;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.s17.moniteryourlife.Network.RestClient;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONObject;

import java.util.ArrayList;

public class NextViewActivity extends AppCompatActivity {
    private GraphView graph;
    private TextView warnText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_view);
        graph = (GraphView) findViewById(R.id.graph);
        warnText = (TextView)findViewById(R.id.warningTxt);
        graph.setVisibility(View.INVISIBLE);
        Intent i = getIntent();
        String date = i.getStringExtra("Date");
        String userName = Config.getUserName(NextViewActivity.this);
        drawProgressGraph(userName, date);
    }

    public void drawProgressGraph(final String userName, final String date) {
        final ArrayList<Object> reportData = new ArrayList<Object>();
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return RestClient.getReport(params[0], params[1]);
            }

            @Override
            protected void onPostExecute(String result) {
                if (!result.isEmpty()) {
                    try {
                        JSONObject reportJSON = new JSONObject(result);
                        if (reportJSON.optInt("reporttotalSteps", 0) == 0) {
                            reportData.add(0.0);
                        } else {
                            Integer totalSteps = reportJSON.optInt("reporttotalSteps");
                            reportData.add(totalSteps);
                        }
                        if (reportJSON.optDouble("reportGoal", 0) == 0) {
                            reportData.add(0.0);
                        } else {
                            Double goal = reportJSON.optDouble("reportGoal");
                            reportData.add(goal);
                        }
                        if (reportJSON.optDouble("reportcarlorieBurned", 0) == 0) {
                            reportData.add(0.0);
                        } else {
                            Double calorieBurned = reportJSON.optDouble("reportcarlorieBurned");
                            reportData.add(calorieBurned);
                        }
                        if (reportJSON.optDouble("reportcarlorieConsumed", 0) == 0) {

                            reportData.add(0.0);
                        } else {
                            Double calorieConsumed = reportJSON.optDouble("reportcarlorieConsumed");
                            reportData.add(calorieConsumed);
                        }
                        for (int i = 0; i < reportData.size(); i++) {
                            System.out.println(">>>>>>>>>>>>>>>>in dataPoint" + reportData.get(i));
                        }
                        warnText.setText("My Calorie Report");
                        graph.setVisibility(View.VISIBLE);
                        //draw graph
                        CreateBarGraph(reportData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    warnText.setText("No report on this day");
                }
            }
            }.execute(userName, date);

    }





    public void CreateBarGraph(ArrayList<Object> reportData) {
        graph.setTitle("Daily Report Graph");
        graph.setTitleTextSize(40);
        Double totalStepDouble = Double.parseDouble(reportData.get(0).toString());
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>();
        series.appendData(new DataPoint(0, totalStepDouble), false, 4);
        for(int i = 1; i<4;i++){
            series.appendData(new DataPoint(i,(Double)reportData.get(i)),false,4);
        }
        series.setSpacing(20);
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
            }
        });
        // draw values on top
        series.setDrawValuesOnTop(true);
        series.isDrawValuesOnTop();
        series.setValuesOnTopColor(Color.WHITE);
        graph.addSeries(series);
        //StaticLabelsFormatter formatter = new StaticLabelsFormatter(graph);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[]{"Steps", "Goal","Burned","Consumed"});
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        graph.getGridLabelRenderer().setLabelsSpace(10);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getGridLabelRenderer().setLabelsSpace(10);
    }
}
