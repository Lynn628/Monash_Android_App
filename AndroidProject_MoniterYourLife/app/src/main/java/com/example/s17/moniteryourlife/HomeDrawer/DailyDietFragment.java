package com.example.s17.moniteryourlife.HomeDrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.s17.moniteryourlife.FoodOfACategoryActivity;
import com.example.s17.moniteryourlife.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Object;

/**
 * Created by s17 on 4/16/2016.
 */
public class DailyDietFragment extends android.support.v4.app.Fragment {
    private ListView foodCategoryList;

    public static DailyDietFragment newInstance() {
        DailyDietFragment fragment = new DailyDietFragment();
        return fragment;
    }

    public DailyDietFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.food, container, false);
        foodCategoryList = (ListView) rootView.findViewById(R.id.foodCategoryList);
        fillFoodCategoryList();
        foodCategoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                i.putExtra("position",position);
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>"+position);
                i.setClass(getActivity(), FoodOfACategoryActivity.class);
                startActivity(i);
            }
        });
        return rootView;
    }

    public void fillFoodCategoryList() {
        ArrayList<HashMap<String, Object>> foodlist = new ArrayList<HashMap<String, Object>>();
        String[] categoryArray = new String[]{"Dirnks", "Meal", "Meat", "Snacks", "Breads", "Cakes",
                "Fruits", "Veggies", "Other"};
        Integer[] pictureArray = new Integer[]{R.drawable.food1,R.drawable.food2,
                R.drawable.food3,R.drawable.food4,R.drawable.food5,R.drawable.food6,
                R.drawable.food7,R.drawable.food8,R.drawable.food9};
        for (int i = 0; i < 9; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("Picture",pictureArray[i]);
            map.put("Text",categoryArray[i]);
            foodlist.add(map);
        }
        SimpleAdapter mShcedule = new SimpleAdapter(getActivity(),foodlist,
                R.layout.food_category_item,new String[]{"Picture","Text"},
                new int[]{R.id.picture,R.id.category});
        foodCategoryList.setAdapter(mShcedule);
    }
}