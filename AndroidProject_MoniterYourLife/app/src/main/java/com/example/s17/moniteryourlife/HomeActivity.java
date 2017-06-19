package com.example.s17.moniteryourlife;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.s17.moniteryourlife.HomeDrawer.CalorieGoalFragment;
import com.example.s17.moniteryourlife.HomeDrawer.DailyDietFragment;
import com.example.s17.moniteryourlife.HomeDrawer.HomeFragment;
import com.example.s17.moniteryourlife.HomeDrawer.MapFragment;
import com.example.s17.moniteryourlife.HomeDrawer.ProgerssReportFragment;
import com.example.s17.moniteryourlife.HomeDrawer.StepFragment;
import com.example.s17.moniteryourlife.HomeDrawer.TrackCalorieFragment;

public class HomeActivity extends ActionBarActivity {
    private HomeFragmentNavigationDrawer dlDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
// Get the drawer view
        dlDrawer = (HomeFragmentNavigationDrawer) findViewById(R.id.drawer_layout);
// Setup drawer view
        dlDrawer.setupDrawerConfiguration((ListView) findViewById(R.id.home_left_drawer),
                R.layout.drawer_nav_item, R.id.home_content);

        //Add drawer item
        dlDrawer.addNavItem("Home", "Home Page", HomeFragment.class);
        dlDrawer.addNavItem("Calorie Goal", "Set Calorie Goal", CalorieGoalFragment.class);
        dlDrawer.addNavItem("My Daily Diet", "My Daily Diet", DailyDietFragment.class);
        dlDrawer.addNavItem("Steps", "Steps Record", StepFragment.class);
        dlDrawer.addNavItem("Track Calorie", "Track Calorie", TrackCalorieFragment.class);
        dlDrawer.addNavItem("Progress Report","Progress Report", ProgerssReportFragment.class );
        dlDrawer.addNavItem("Map","Map",MapFragment.class );
        //Pass the user information  received from login to home fragment
        Intent i = getIntent();
        String userName = i.getStringExtra("username");
        Class<? extends Fragment> fragment = dlDrawer.getDrawerFragment(0);
        Bundle bundle = new Bundle();
        bundle.putString("username",userName);
       // fragment.(bundle);
                // Select default
        if (savedInstanceState == null) {
            dlDrawer.selectDrawerItem(0);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
// If the drawer is open, hide action items related to the content
        if (dlDrawer.isDrawerOpen()) {
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// The action bar home/up action should open or close the drawer.
// ActionBarDrawerToggle will take care of this.
        if (dlDrawer.getDrawerToggle().onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
// Sync the toggle state after onRestoreInstanceState has occurred.
    dlDrawer.getDrawerToggle().syncState();

}
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
// Pass any configuration change to the drawer toggles
        dlDrawer.getDrawerToggle().onConfigurationChanged(newConfig);
    }
}

