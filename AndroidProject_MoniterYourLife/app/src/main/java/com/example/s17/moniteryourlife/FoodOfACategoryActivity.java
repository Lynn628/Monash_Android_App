package com.example.s17.moniteryourlife;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s17.moniteryourlife.Network.RestClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FoodOfACategoryActivity extends AppCompatActivity {
    private TextView showPosition;
    private ListView foodOfACategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_of_acategory);
       //showPosition =(TextView)findViewById(R.id.inCategory);
        Intent i = getIntent();
        int position = i.getIntExtra("position",0)+1;
        System.out.println(">>>>>>>>>>>>>>position"+position);
        fillTheCategoryList(position);

    }

    public void fillTheCategoryList(int category){
        new AsyncTask<Integer, Void, String>() {
            @Override
            protected String doInBackground(Integer... params) {
                return RestClient.findFoodByCategory(params[0]);
            }
            @Override
            protected void onPostExecute(String s) {
                try{
                    final List<String> foodName = new ArrayList<String>();
                    final List<Integer> foodId = new ArrayList<Integer>();
                    JSONArray foodArray = new JSONArray(s);
                    for(int i = 0;i < foodArray.length(); i++){
                        JSONObject foodItem = foodArray.getJSONObject(i);
                        System.out.println(">>>>>>>>>>"+foodItem.toString());
                        foodName.add(foodItem.getString("foodName"));
                        foodId.add(foodItem.getInt("foodId"));
                    }
                    ArrayList<HashMap<String, Object>> foodlist = new ArrayList<HashMap<String, Object>>();
                    for (int i = 0; i < foodName.size(); i++) {
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("foodItem",foodName.get(i));
                        map.put("picture",R.drawable.food_item);
                        //map.put("Text",categoryArray[i]);
                        foodlist.add(map);
                    }
                    foodOfACategory = (ListView)findViewById(R.id.foodOfACategory);
                    SimpleAdapter mShcedule = new SimpleAdapter(FoodOfACategoryActivity.this,foodlist,
                            R.layout.food_item,new String[]{"foodItem","picture"},
                            new int[]{R.id.foodItem, R.id.foodListPicture});
                    //Set adapter
                    foodOfACategory.setAdapter(mShcedule);
                    //Listen the action of choose a food item
                    foodOfACategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i = new Intent();
                            String selectFoodName = foodName.get(position);
                            Integer selectFoodId = foodId.get(position);
                            //System.out.println(">>>>>>>>>>>>>>>>show choose food name"+selectFoodName);
                            i.putExtra("FoodName",selectFoodName);
                            i.putExtra("FoodId",selectFoodId);
                            i.setClass(FoodOfACategoryActivity.this, FoodDetailActivity.class);
                            startActivity(i);
                        }
                    });
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }.execute(category);
      }
    }

