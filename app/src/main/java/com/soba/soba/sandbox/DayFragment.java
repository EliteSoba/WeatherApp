package com.soba.soba.sandbox;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends Fragment {


    public static HashMap<String, Integer> views = new HashMap<String, Integer>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        views.put("hour0", R.id.hour0);
        views.put("hour1", R.id.hour1);
        views.put("hour2", R.id.hour2);
        views.put("hour3", R.id.hour3);
        views.put("hour4", R.id.hour4);
        views.put("hour5", R.id.hour5);
        views.put("hour6", R.id.hour6);
        views.put("hour7", R.id.hour7);
        views.put("hour8", R.id.hour8);
        views.put("hour9", R.id.hour9);
        views.put("hour10", R.id.hour10);
        views.put("hour11", R.id.hour11);
        views.put("hour12", R.id.hour12);
        views.put("hour13", R.id.hour13);
        views.put("hour14", R.id.hour14);
        views.put("hour15", R.id.hour15);
        views.put("hour16", R.id.hour16);
        views.put("hour17", R.id.hour17);
        views.put("hour18", R.id.hour18);
        views.put("hour19", R.id.hour19);
        views.put("hour20", R.id.hour20);
        views.put("hour21", R.id.hour21);
        views.put("hour22", R.id.hour22);
        views.put("hour23", R.id.hour23);
        views.put("icon0", R.id.icon0);
        views.put("icon1", R.id.icon1);
        views.put("icon2", R.id.icon2);
        views.put("icon3", R.id.icon3);
        views.put("icon4", R.id.icon4);
        views.put("icon5", R.id.icon5);
        views.put("icon6", R.id.icon6);
        views.put("icon7", R.id.icon7);
        views.put("icon8", R.id.icon8);
        views.put("icon9", R.id.icon9);
        views.put("icon10", R.id.icon10);
        views.put("icon11", R.id.icon11);
        views.put("icon12", R.id.icon12);
        views.put("icon13", R.id.icon13);
        views.put("icon14", R.id.icon14);
        views.put("icon15", R.id.icon15);
        views.put("icon16", R.id.icon16);
        views.put("icon17", R.id.icon17);
        views.put("icon18", R.id.icon18);
        views.put("icon19", R.id.icon19);
        views.put("icon20", R.id.icon20);
        views.put("icon21", R.id.icon21);
        views.put("icon22", R.id.icon22);
        views.put("icon23", R.id.icon23);
        views.put("temp0", R.id.temp0);
        views.put("temp1", R.id.temp1);
        views.put("temp2", R.id.temp2);
        views.put("temp3", R.id.temp3);
        views.put("temp4", R.id.temp4);
        views.put("temp5", R.id.temp5);
        views.put("temp6", R.id.temp6);
        views.put("temp7", R.id.temp7);
        views.put("temp8", R.id.temp8);
        views.put("temp9", R.id.temp9);
        views.put("temp10", R.id.temp10);
        views.put("temp11", R.id.temp11);
        views.put("temp12", R.id.temp12);
        views.put("temp13", R.id.temp13);
        views.put("temp14", R.id.temp14);
        views.put("temp15", R.id.temp15);
        views.put("temp16", R.id.temp16);
        views.put("temp17", R.id.temp17);
        views.put("temp18", R.id.temp18);
        views.put("temp19", R.id.temp19);
        views.put("temp20", R.id.temp20);
        views.put("temp21", R.id.temp21);
        views.put("temp22", R.id.temp22);
        views.put("temp23", R.id.temp23);
        return inflater.inflate(R.layout.fragment_day, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        String json = args.getString(MyActivity.resultJson);
        String units = args.getString(MyActivity.degreeType);
        ((TextView)getActivity().findViewById(R.id.temp_text)).setText("Temp(" + units+")");

        try {
            JSONObject results = new JSONObject(json);
            JSONArray hourly = results.getJSONObject("hourly").getJSONArray("data");

            for (int i = 0; i < 24; i++) {
                JSONObject hour = hourly.optJSONObject(i);
                if (hour == null) {
                    return;
                }

                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getDefault());
                cal.setTimeInMillis(Long.parseLong(hour.getString("time")) * 1000);

                String h = cal.get(Calendar.HOUR) + ":00 "
                        + (cal.get(Calendar.HOUR_OF_DAY) >= 12 ? "PM" : "AM");
                System.out.println(h);

                ((TextView)getActivity().findViewById(views.get("hour"+i))).setText(h);


                String icon = hour.getString("icon");
                int which = 0;
                switch(icon) {
                    case "clear-day": which = R.drawable.clear_day; break;
                    case "clear-night": which = R.drawable.clear_night; break;
                    case "cloudy": which = R.drawable.cloudy; break;
                    case "rain": which = R.drawable.rain; break;
                    case "snow": which = R.drawable.snow; break;
                    case "sleet": which = R.drawable.sleet; break;
                    case "wind": which = R.drawable.wind; break;
                    case "fog": which = R.drawable.fog; break;
                    case "partly-cloudy-day": which = R.drawable.partly_cloudy_day; break;
                    case "partly-cloudy-night": which = R.drawable.partly_cloudy_night; break;
                }

                ((ImageView)getActivity().findViewById(views.get("icon"+i))).setImageResource(which);

                String temp = ""+((int)Float.parseFloat(hour.getString("temperature")));

                ((TextView)getActivity().findViewById(views.get("temp"+i))).setText(temp);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
