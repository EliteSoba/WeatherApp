package com.soba.soba.sandbox;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.hamweather.aeris.communication.AerisEngine;

public class MapActivity extends AppCompatActivity {

    public static final String lat = "com.soba.soba.sandbox.LATITUDE";
    public static final String lon = "com.soba.soba.sandbox.LONGITUDE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        AerisEngine.initWithKeys(getString(R.string.aeris_client_id),
                getString(R.string.aeris_client_secret), this);

        Fragment fragment = new MapFragment();

        Bundle args = new Bundle();
        args.putDouble(lat, getIntent().getDoubleExtra(lat, 0));
        args.putDouble(lon, getIntent().getDoubleExtra(lon, 0));
        fragment.setArguments(args);

        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.llLegendHolder, fragment).commit();


    }

}
