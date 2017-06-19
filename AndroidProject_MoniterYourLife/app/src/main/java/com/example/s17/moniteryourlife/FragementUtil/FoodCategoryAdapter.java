package com.example.s17.moniteryourlife.FragementUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.s17.moniteryourlife.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by s17 on 4/24/2016.
 */
public class FoodCategoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, Object>> categoryList;
    public FoodCategoryAdapter(Context context, ArrayList<HashMap<String, Object>> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.food_category_item, null); // List layout here
        }


        return null;
    }

//    // Grab the TextViews in our custom layout
//        TextView monsterName = (TextView)view.findViewById(R.id.monsterNameText);
//        TextView attackPower = (TextView)view.findViewById(R.id.attackPowerText);
//// Assign values to the TextViews using the Monster object
//        monsterName.setText(monsters.get(i).getName());
//        attackPower.setText("Attack Power: " + monsters.get(i).getAttackPower());
//// Change the colour depending on the monster type
//        String monsterType = monsters.get(i).getType();
//        if (monsterType.equals("Good"))
//            attackPower.setTextColor(Color.parseColor("#00FF00")); // Green
//        if (monsterType.equals("Neutral"))
//            attackPower.setTextColor(Color.parseColor("#0000FF")); // Blue
//        if (monsterType.equals("Evil"))
//            attackPower.setTextColor(Color.parseColor("#FF0000")); // Red
//// Return the completed View of the row being processed
//        return view;
//    }
//}
}
