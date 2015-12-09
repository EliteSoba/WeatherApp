package com.soba.soba.sandbox;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    DayFragment day;
    WeekFragment week;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String json = getIntent().getStringExtra(MyActivity.resultJson);
        String units = getIntent().getStringExtra(MyActivity.degreeType);
        String city = getIntent().getStringExtra(MyActivity.cityName);

        ((TextView)findViewById(R.id.details_title)).setText("More Details for " + city);

        Bundle args = new Bundle();
        args.putString(MyActivity.resultJson, json);
        args.putString(MyActivity.degreeType, units);

        day = new DayFragment();
        week = new WeekFragment();
        day.setArguments(args);
        week.setArguments(args);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, day)
                .commit();
    }

    public void selectDay(View view) {
        ((Button)findViewById(R.id.nextday)).setEnabled(false);
        ((Button)findViewById(R.id.nextweek)).setEnabled(true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, day);
        transaction.addToBackStack(null);

        transaction.commit();

    }
    public void selectWeek(View view) {
        ((Button)findViewById(R.id.nextday)).setEnabled(true);
        ((Button)findViewById(R.id.nextweek)).setEnabled(false);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, week);
        transaction.addToBackStack(null);

        transaction.commit();

    }

}
