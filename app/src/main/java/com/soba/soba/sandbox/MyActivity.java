package com.soba.soba.sandbox;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;


public class MyActivity extends AppCompatActivity {

    private class DownloadFilesTask extends AsyncTask<URL, Integer, String> {

        AppCompatActivity app;
        String jsonOutput;
        String cityState;
        String degree;
        DownloadFilesTask(AppCompatActivity app, String cs, String deg) {
            this.app = app;
            jsonOutput = "";
            cityState = cs;
            degree = deg;
        }

        @Override
        protected String doInBackground(URL... params) {
            URL url = params[0];
                try {
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    BufferedReader read = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = read.readLine()) != null) {
                        result.append(line);
                    }
                    jsonOutput = result.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//            System.out.println(jsonOutput);
            return jsonOutput;
        }

        protected void onPostExecute(String result) {
            Intent intent = new Intent(app, ResultActivity.class);
            intent.putExtra(resultJson, result);
            intent.putExtra(cityName, cityState);
            intent.putExtra(degreeType, degree);

            startActivity(intent);
        }
    }



    public final static String forecastURL = "com.soba.soba.sandbox.forecast";
    public final static String resultJson = "com.soba.soba.sandbox.result";
    public final static String cityName = "com.soba.soba.sandbox.city";
    public final static String degreeType = "com.soba.soba.sandbox.degree";
    public static HashMap<String, String> STATE_CODES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.states_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        STATE_CODES = new HashMap<String, String>();
        STATE_CODES.put("Alaska", "AK");
        STATE_CODES.put("Alabama", "AL");
        STATE_CODES.put("Arkansas", "AR");
        STATE_CODES.put("Arizona", "AZ");
        STATE_CODES.put("California", "CA");
        STATE_CODES.put("Colorado", "CO");
        STATE_CODES.put("Connecticut", "CT");
        STATE_CODES.put("Delaware", "DE");
        STATE_CODES.put("District of Columbia", "DC");
        STATE_CODES.put("Florida", "FL");
        STATE_CODES.put("Georgia", "GA");
        STATE_CODES.put("Hawaii", "HI");
        STATE_CODES.put("Idaho", "ID");
        STATE_CODES.put("Illinois", "IL");
        STATE_CODES.put("Indiana", "IN");
        STATE_CODES.put("Iowa", "IA");
        STATE_CODES.put("Kansas", "KS");
        STATE_CODES.put("Kentucky", "KY");
        STATE_CODES.put("Louisiana", "LA");
        STATE_CODES.put("Maine", "ME");
        STATE_CODES.put("Maryland", "MD");
        STATE_CODES.put("Massachusetts", "MA");
        STATE_CODES.put("Michigan", "MI");
        STATE_CODES.put("Minnesota", "MN");
        STATE_CODES.put("Mississippi", "MS");
        STATE_CODES.put("Missouri", "MO");
        STATE_CODES.put("Montana", "MT");
        STATE_CODES.put("Nebraska", "NE");
        STATE_CODES.put("Nevada", "NV");
        STATE_CODES.put("New Hampshire", "NH");
        STATE_CODES.put("New Jersey", "NJ");
        STATE_CODES.put("New Mexico", "NM");
        STATE_CODES.put("New York", "NY");
        STATE_CODES.put("North Carolina", "NC");
        STATE_CODES.put("North Dakota", "ND");
        STATE_CODES.put("Ohio", "OH");
        STATE_CODES.put("Oklahoma", "OK");
        STATE_CODES.put("Oregon", "OR");
        STATE_CODES.put("Pennsylvania", "PA");
        STATE_CODES.put("Rhode Island", "RI");
        STATE_CODES.put("South Carolina", "SC");
        STATE_CODES.put("South Dakota", "SD");
        STATE_CODES.put("Tennessee", "TN");
        STATE_CODES.put("Texas", "TX");
        STATE_CODES.put("Utah", "UT");
        STATE_CODES.put("Vermont", "VT");
        STATE_CODES.put("Virginia", "VA");
        STATE_CODES.put("Washington", "WA");
        STATE_CODES.put("West Virginia", "WV");
        STATE_CODES.put("Wisconsin", "WI");
        STATE_CODES.put("Wyoming", "WY");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    public void validateAndSend(View view) {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText)findViewById(R.id.edit_message);
        EditText street = (EditText)findViewById(R.id.street);
        EditText city = (EditText)findViewById(R.id.city);
        Spinner state = (Spinner)findViewById(R.id.spinner);

        String message = "";
        boolean pass = true;

        String st = street.getText().toString();
        String ci = city.getText().toString();
        String sta = state.getSelectedItem().toString();

        if (st.trim().equals("") || st == null) {
            message += "Please enter a Street Address.\n";
            pass = false;
        }
        if (ci.trim().equals("") || ci == null) {
            message += "Please enter a City.\n";
            pass = false;
        }
        if (sta.equals("Select")) {
            message += "Please select a State.";
            pass = false;
        }

        if (!pass) {
            TextView error = (TextView)findViewById(R.id.error);
            error.setText(message);
        }
        else {
            String degree = "si";
            RadioButton f = (RadioButton)findViewById(R.id.fahrenheit);
            if (f.isChecked()) {
                degree = "us";
            }

            String url = "http://elasticsobanet-env.elasticbeanstalk.com/forecastapi.php?"
                    + "street=" + st
                    + "&city=" + ci
                    + "&state=" + STATE_CODES.get(sta)
                    + "&degree=" + degree;
//            intent.putExtra(resultJson, url);
            try {
                String cityState = ci + ", " + STATE_CODES.get(sta);

                URL myUrl = new URL(url.replace(" ", "%20"));
                new DownloadFilesTask(this, cityState, degree).execute(myUrl);
            } catch (MalformedURLException e) {
            }
//            intent.putExtra(forecastURL, url);


            //startActivity(intent);
        }
    }

    public void clearSearch(View view) {
        EditText street = (EditText)findViewById(R.id.street);
        EditText city = (EditText)findViewById(R.id.city);
        Spinner state = (Spinner)findViewById(R.id.spinner);
        TextView error = (TextView)findViewById(R.id.error);

        street.setText("");
        city.setText("");
        state.setSelection(0);
        error.setText("");
    }

    public void about(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void openForecast(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://forecast.io"));
        startActivity(browserIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
