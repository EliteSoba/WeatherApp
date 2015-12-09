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
public class WeekFragment extends Fragment {

    public static HashMap<String, Integer> views = new HashMap<String, Integer>();
    String days[] = {"Nothing","Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    String months[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        views.put("icon0", R.id.icon0);
        views.put("icon1", R.id.icon1);
        views.put("icon2", R.id.icon2);
        views.put("icon3", R.id.icon3);
        views.put("icon4", R.id.icon4);
        views.put("icon5", R.id.icon5);
        views.put("icon6", R.id.icon6);
        views.put("lowhigh0", R.id.lowhigh0);
        views.put("lowhigh1", R.id.lowhigh1);
        views.put("lowhigh2", R.id.lowhigh2);
        views.put("lowhigh3", R.id.lowhigh3);
        views.put("lowhigh4", R.id.lowhigh4);
        views.put("lowhigh5", R.id.lowhigh5);
        views.put("lowhigh6", R.id.lowhigh6);
        views.put("day0", R.id.day0);
        views.put("day1", R.id.day1);
        views.put("day2", R.id.day2);
        views.put("day3", R.id.day3);
        views.put("day4", R.id.day4);
        views.put("day5", R.id.day5);
        views.put("day6", R.id.day6);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        String json = args.getString(MyActivity.resultJson);
        String units = args.getString(MyActivity.degreeType);
       // ((TextView)getActivity().findViewById(R.id.week_text)).setText(json);

        try {
            JSONObject results = new JSONObject(json);
            JSONArray weekly = results.getJSONObject("daily").getJSONArray("data");

            for (int i = 0; i < 7; i++) {
                JSONObject week = weekly.optJSONObject(i);
                if (week == null) {
                    return;
                }

                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getDefault());
                cal.setTimeInMillis(Long.parseLong(week.getString("time")) * 1000);

                String h = days[cal.get(Calendar.DAY_OF_WEEK)] + ", "
                        + months[cal.get(Calendar.MONTH)]
                        + (cal.get(Calendar.DAY_OF_MONTH) < 10 ? " 0" : " ")
                        + cal.get(Calendar.DAY_OF_MONTH);
                System.out.println(h);

                ((TextView)getActivity().findViewById(views.get("day"+i))).setText(h);


                String icon = week.getString("icon");
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

                String lowhigh = "Min: " + ((int)Float.parseFloat(week.getString("temperatureMin")))
                        + units + " | Max: " + ((int)Float.parseFloat(week.getString("temperatureMax")))
                        + units;
                ((TextView)getActivity().findViewById(views.get("lowhigh"+i))).setText(lowhigh);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
