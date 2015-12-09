package com.soba.soba.sandbox;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hamweather.aeris.communication.AerisEngine;

public class MapActivity extends AppCompatActivity {

    public final static String AERIS_CLIENT_ID = "com.soba.soba.clientid";
    public final static String AERIS_CLIENT_SECRET = "com.soba.soba.clientsecret";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Bundle args = new Bundle();
        args.putString(AERIS_CLIENT_ID, this.getString(R.string.aeris_client_id));
        args.putString(AERIS_CLIENT_SECRET, this.getString(R.string.aeris_client_secret));
        MapFragment mapFragment = new MapFragment();
        mapFragment.setArguments(args);


    }

}
