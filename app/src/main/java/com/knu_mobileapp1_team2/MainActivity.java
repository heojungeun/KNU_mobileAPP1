package com.knu_mobileapp1_team2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SensorEventListener {
    Fragment fragHome;
    Fragment fragDetails;
    Fragment fragStore;
    Fragment fragSettings;

    Fragment fragCurrent;

    SharedPreferences sp;

    Sensor stepDetector = null;

    int steps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getSharedPreferences("com.knu_mobileapp1_team2.pref", Activity.MODE_PRIVATE);
        if (!sp.getBoolean("app_enabled", false)) {
            Toast.makeText(this, R.string.app_invalid_access, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Intent si = new Intent(this, StepCounterService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(si);
        } else {
            startService(si);
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragHome = new HomeFragment();
        fragDetails = new DetailsFragment();
        fragSettings = new SettingsFragment();
        fragStore = new StoreFragment();

        replaceFragment(fragHome, getString(R.string.drawer_home));
    }

    @Override
    protected void onPause() {
        unregisterSensor();
        steps = 0;
        super.onPause();
    }

    @Override
    protected void onResume() {
        registerSensor();
        super.onResume();
    }

    private void registerSensor() {
        if (stepDetector != null) return;
        SensorManager sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        stepDetector = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (stepDetector != null) sm.registerListener(this, stepDetector, SensorManager.SENSOR_DELAY_NORMAL, 5000000);     // batch for 5sec.
    }

    private void unregisterSensor() {
        if (stepDetector == null) return;
        SensorManager sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sm.unregisterListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            replaceFragment(fragHome, getString(R.string.drawer_home));
        } else if (id == R.id.nav_details) {
            replaceFragment(fragDetails, getString(R.string.drawer_details));
        } else if (id == R.id.nav_settings) {
            replaceFragment(fragSettings, getString(R.string.drawer_settings));
        } else if (id == R.id.nav_store) {
            replaceFragment(fragStore, getString(R.string.drawer_store));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment newFragment, String title) {
        setTitle(title);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_fragment_holder, newFragment).commit();

        fragCurrent = newFragment;

        if (fragCurrent instanceof HomeFragment) {
            ((HomeFragment)fragCurrent).appSteps = steps;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        steps++;

        if (steps == 6) {
            Toast.makeText(this, R.string.main_walk_warning, Toast.LENGTH_LONG).show();

            if (fragCurrent instanceof HomeFragment) {
                ((HomeFragment)fragCurrent).dangerTree();
            }
        } else if (steps > 18) {
            long totalRunningTime = sp.getLong("total_running_time", 0);
            int killedTrees = sp.getInt("killed_trees", 0);

            killedTrees++;

            sp.edit().putLong("last_dead_time", totalRunningTime)
                     .putInt("killed_trees", killedTrees).apply();

            Toast.makeText(this, R.string.main_walk_dead, Toast.LENGTH_LONG).show();

            if (fragCurrent instanceof HomeFragment) {
                ((HomeFragment)fragCurrent).killTree();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
