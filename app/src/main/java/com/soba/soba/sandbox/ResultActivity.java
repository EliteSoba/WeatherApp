package com.soba.soba.sandbox;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class ResultActivity extends AppCompatActivity {

    String json;
    JSONObject results;
    /** True if us, False if si */
    boolean units;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    String title;
    String desc;
    String icon;
    String cityState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

            public void onSuccess(Sharer.Result result) {
                System.out.println(":)");
                Toast.makeText(getApplicationContext(), "Post Successful", Toast.LENGTH_SHORT).show();

            }

            public void onCancel() {
                System.out.println(":|");
                Toast.makeText(getApplicationContext(), "Post Canceled", Toast.LENGTH_SHORT).show();
            }

            public void onError(FacebookException error) {
                System.out.println(":(");
                Toast.makeText(getApplicationContext(), "Error Posting", Toast.LENGTH_SHORT).show();
            }
        });

        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        json = intent.getStringExtra(MyActivity.resultJson);
        if ("us".equals(intent.getStringExtra(MyActivity.degreeType))) {
            units = true;
        }
        else {
            units = false;
        }

        try {
            results = new JSONObject(json);
            JSONObject currently = results.getJSONObject("currently");
            JSONObject daily = results.getJSONObject("daily");
            JSONArray dailies = daily.getJSONArray("data");

            String icon = currently.getString("icon");
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

            ((ImageView)findViewById(R.id.weatherIcon)).setImageResource(which);
            this.icon = icon.replace("-", "_");

            cityState = intent.getStringExtra(MyActivity.cityName);
            String forecast = currently.getString("summary") + " in "
                    + cityState;
            System.out.println(forecast);
            ((TextView)findViewById(R.id.forecast)).setText(forecast);
            title = "Current Weather in " + cityState;

            String temp = currently.getString("temperature") + (units ? "°F" : "°C");
            System.out.println(temp);
            desc = currently.getString("summary") + ", " + temp;
            ((TextView)findViewById(R.id.temp)).setText(temp);
            String lowHigh = "L:" + ((int)Float.parseFloat(dailies.getJSONObject(0).getString("temperatureMin"))) + "°"
                    + "| H:" + ((int)Float.parseFloat(dailies.getJSONObject(0).getString("temperatureMax"))) + "°";
            System.out.println(lowHigh);
            ((TextView)findViewById(R.id.low_high)).setText(lowHigh);

            float precip = Float.parseFloat(currently.getString("precipIntensity"));
            String precipitation;
            if (units) {
                if (precip <= 0) {
                    precipitation = "None";
                }
                else if (precip <= 0.002) {
                    precipitation = "Very Light";
                }
                else if (precip <= 0.017) {
                    precipitation = "Light";
                }
                else if (precip <= 0.1) {
                    precipitation = "Moderate";
                }
                else {
                    precipitation = "Heavy";
                }
            }
            else {
                if (precip <= 0) {
                    precipitation = "None";
                }
                else if (precip <= 0.0508) {
                    precipitation = "Very Light";
                }
                else if (precip <= 0.432) {
                    precipitation = "Light";
                }
                else if (precip <= 2.54) {
                    precipitation = "Moderate";
                }
                else {
                    precipitation = "Heavy";
                }
            }
            System.out.println(precipitation);
            ((TextView)findViewById(R.id.precip)).setText(precipitation);

            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            String rain = df.format(Float.parseFloat(currently.getString("precipProbability"))*100) + "%";
            System.out.println(rain);
            ((TextView)findViewById(R.id.rain)).setText(rain);

            String wind = df.format(Float.parseFloat(currently.getString("windSpeed")))
                    + (units ? " mph" : " mps");
            System.out.println(wind);
            ((TextView)findViewById(R.id.wind)).setText(wind);

            String dew = ((int)Float.parseFloat(currently.getString("dewPoint")))
                    + (units ? "°F" : "°C");
            System.out.println(dew);
            ((TextView)findViewById(R.id.dew)).setText(dew);

            String humid = df.format(Float.parseFloat(currently.getString("humidity"))*100) + "%";
            System.out.println(humid);
            ((TextView)findViewById(R.id.humid)).setText(humid);

            String vis = df.format(Float.parseFloat(currently.getString("visibility")))
                    + (units ? " mi" : " km");
            System.out.println(vis);
            ((TextView)findViewById(R.id.vis)).setText(vis);


            Calendar cal = Calendar.getInstance();
            cal.setTimeZone(TimeZone.getDefault());
            cal.setTimeInMillis(Long.parseLong(dailies.getJSONObject(0).getString("sunriseTime")) *1000);

            String rise = cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE)
                    + (cal.get(Calendar.HOUR_OF_DAY) >= 12 ? "PM" : "AM");
            System.out.println(rise);
            ((TextView)findViewById(R.id.rise)).setText(rise);

            Calendar cal2 = Calendar.getInstance();
            cal2.setTimeZone(TimeZone.getDefault());
            cal2.setTimeInMillis(Long.parseLong(dailies.getJSONObject(0).getString("sunsetTime")) *1000);

            String set = cal2.get(Calendar.HOUR) + ":" + cal2.get(Calendar.MINUTE)
                    + (cal2.get(Calendar.HOUR_OF_DAY) >= 12 ? "PM" : "AM");
            System.out.println(set);
            ((TextView)findViewById(R.id.set)).setText(set);



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void details(View view) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(MyActivity.resultJson, json);
        intent.putExtra(MyActivity.degreeType, (units ? "°F" : "°C"));
        intent.putExtra(MyActivity.cityName, cityState);
        startActivity(intent);
    }

    public void loadMap(View view) {
        //Intent intent = new Intent(this, MapActivity.class);
       // startActivity(intent);

    }

    public void facebookShare(View view) {
        System.out.println("https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-284122822131/" + icon + ".png");


        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(title)
                    .setContentDescription(desc)
                    .setContentUrl(Uri.parse("http://forecast.io"))
                    .setImageUrl(Uri.parse("https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-284122822131/" + icon + ".png"))
                    .build();

            shareDialog.show(linkContent);
        }
    }

    @Override
     protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
