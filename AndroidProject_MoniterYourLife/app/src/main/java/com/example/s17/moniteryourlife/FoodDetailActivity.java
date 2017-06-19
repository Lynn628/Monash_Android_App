package com.example.s17.moniteryourlife;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s17.moniteryourlife.Network.BingSearchImage;
import com.example.s17.moniteryourlife.Network.BingSearchWeb;
import com.example.s17.moniteryourlife.Network.RestClient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FoodDetailActivity extends AppCompatActivity {
    private Button addTodiet;
    private TextView showFoodName;
    private TextView nuritionInfo;
    private EditText amountEdit;
    private TextView bingInfo;
    private ImageView bingImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        Intent i = getIntent();
        final String foodName = i.getStringExtra("FoodName");
        final String foodNameLine = foodName.replace(" ", "-");
        final Integer foodId = i.getIntExtra("FoodId", 0);
        showFoodName = (TextView) findViewById(R.id.showFoodName);
        showFoodName.setText("Food Name: " + foodName);
        addTodiet = (Button) findViewById(R.id.foodItemAdd);
        nuritionInfo = (TextView) findViewById(R.id.nuritionInfo);
        amountEdit = (EditText) findViewById(R.id.amountEdit);
        bingInfo = (TextView) findViewById(R.id.bingInfo);
        bingImage = (ImageView) findViewById(R.id.bingImage);

        final String userName = Config.getUserName(this);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        final String dietDate = dateFormat.format(new java.util.Date());

        String foodIdStr = "";
        if (foodId < 10000) {
            foodIdStr = "0" + String.valueOf(foodId);
        } else {
            foodIdStr = String.valueOf(foodId);
        }
        //Call method to show image
        getBingImageInfo(foodName);
        //Call method to get food information from bing
        getBingWebInfo(foodName);
        //Call method to get food nutrition detail
        getFoodNutrientDetail(foodIdStr);

        //Add selected food to the server's diet table
        addTodiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodAmount = amountEdit.getText().toString();
                RestClient.addFoodToDiet(userName, dietDate, foodNameLine, foodAmount);
                 Toast.makeText(FoodDetailActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getFoodNutrientDetail(String foodIdStr) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return RestClient.requsetNurientDetial(params[0]);
            }

            @Override
            protected void onPostExecute(String result) {

                try {
                    processingNutrientsJSON(result);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.execute(foodIdStr);
    }

    public void processingNutrientsJSON(String result) {
        try {
            JSONObject rootJOSN = new JSONObject(result);
            JSONObject report = rootJOSN.getJSONObject("report");
            JSONObject food = report.getJSONObject("food");
            String foodGroup = food.getString("fg").toString();
            String proteinFactor = food.getString("pf").toString();
            String fatFactor = food.getString("ff").toString();
            //System.out.println(">>>>>>>>>>>>>foodName"+foodName);
            JSONArray neutrients = food.getJSONArray("nutrients");
            ArrayList<String> infoBlock = new ArrayList<>();
            String textInfo = "Nutrtion Information:\n" + "Food Group: " + foodGroup + "\n" +
                    "Protein Factor:" + proteinFactor + " " +" "+ "Fat Factor:" + fatFactor + "\n";
            for (int i = 0; i < 5; i++) {
                JSONObject neurientsItem = neutrients.getJSONObject(i);
                String name = neurientsItem.getString("name").toString();
                //nameList.add(name);
                String value = neurientsItem.getString("value").toString();
                //valueList.add(value);
                String unit = neurientsItem.getString("unit").toString();
                //unitList.add(unit);
                String informationBlock = "Name: " + name + " " +" "+ value + unit;
                //  infoBlock.add(informationBlock);
                textInfo = textInfo + String.valueOf(informationBlock) + "\n";
            }
            nuritionInfo.setText(textInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getBingWebInfo(String foodName) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return BingSearchWeb.bingSearchWeb(params[0]);
            }

            @Override
            protected void onPostExecute(String result) {

                try {
                    processingBingWebJSON(result);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.execute(foodName);
    }

    public void processingBingWebJSON(String result) {
        try {
            JSONObject webResult = new JSONObject(result);
            JSONObject rootDir = webResult.getJSONObject("d");
           // JSONObject resultDir = rootDir.getJSONObject("results");
            JSONArray resultsArray = rootDir.getJSONArray("results");
            JSONObject metaDataItem = resultsArray.getJSONObject(0);
            JSONObject metaDataFirst = metaDataItem.getJSONObject("__metadata");
            String descriptionStr = metaDataItem.getString("Description");
            //String descriptionStr = metaDataFirst.toString();
            bingInfo.setText(descriptionStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getBingImageInfo(String foodName) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return BingSearchImage.bingSearchImage(params[0]);
            }

            @Override
            protected void onPostExecute(String result) {

                try {
                    processingBingImageJSON(result);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.execute(foodName);
    }

    public void processingBingImageJSON(String result) {
        try {
            JSONObject webResult = new JSONObject(result);
            JSONObject rootDir = webResult.getJSONObject("d");
            // JSONObject resultDir = rootDir.getJSONObject("results");
            JSONArray resultsArray = rootDir.getJSONArray("results");
            JSONObject metaDataItem = resultsArray.getJSONObject(0);
            JSONObject metaDataFirst = metaDataItem.getJSONObject("__metadata");
           // String descriptionStr = metaDataItem.getString("Description");
            //String descriptionStr = metaDataFirst.toString();
            JSONObject thumbnail = metaDataItem.getJSONObject("Thumbnail");
            String mediaUrl = thumbnail.getString("MediaUrl");
            Picasso.with(FoodDetailActivity.this).load(mediaUrl).into(bingImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}