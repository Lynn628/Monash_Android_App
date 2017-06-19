package com.example.s17.moniteryourlife.HomeDrawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.s17.moniteryourlife.Config;
import com.example.s17.moniteryourlife.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by s17 on 4/17/2016.
 */
public class HomeFragment extends android.support.v4.app.Fragment {
    private TextView userNameTxt;
    private TextView dateTxt;
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {
    }
    public void setArgument(Bundle bundle){
        super.setArguments(bundle);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home, container, false);
        TextView userNameTxt = (TextView)rootView.findViewById(R.id.userNameEditV);
        TextView dateTxt = (TextView)rootView.findViewById(R.id.dateEditV);
        String userName = Config.getUserName(getActivity());
        userNameTxt.setText(userName);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        String date = dateFormat.format(new java.util.Date());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+date);
        dateTxt.setText(date);
        return rootView;
    }
}