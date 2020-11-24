package com.example.bsaweatherapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String RESULT_STRING = "result";
    private Random mRandom = new Random();
    private ListAdapter mAdapter;
    private List<WeatherData> weatherData = new LinkedList<>();
    private String language;
    private String baseURL = "https://api.openweathermap.org/data/2.5/forecast?q=Wien&units=metric&appid=231b88bacecc85e7d47891a53667356f&lang=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ListAdapter(weatherData);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickListener(new ListAdapter.ListItemClickListener() {
            @Override
            public void onListItemClick(WeatherData item) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.VALUE_KEY, item);
                startActivity(intent);
            }
        });

        String tmp = Locale.getDefault().getLanguage();
        String lang = "en";
        if(tmp.equals("en") || tmp.equals("de")) {
            lang = tmp;
            language = lang;
        }
//        Button generateButton = findViewById(R.id.btn_generate);
//        generateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadWebResult(lang);
//            }
//        });

        //Get language of device; only de and en supported

        loadWebResult(lang);
    }

    private void loadWebResult(String lang) {
        try {
            String sUrl = baseURL + lang;
            URL url = new URL(sUrl);
            new LoadWebContentTask().execute(url);
        } catch (MalformedURLException e) {
//            outputTextView.setText(R.string.url_error);
            Log.e(LOG_TAG, "URL Error", e);
        }
    }

    private String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setConnectTimeout(5000);
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput)
                return scanner.next();
            else
                return null;
        } finally {
            urlConnection.disconnect();
        }
    }

    private class LoadWebContentTask extends AsyncTask<URL, Void, List<WeatherData>> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected List<WeatherData> doInBackground(URL... urls) {
            URL url = urls[0];
            String resultString = "";
            try {
                resultString = getResponseFromHttpUrl(url);
            } catch (IOException e) {
                Log.e(LOG_TAG,  "IO Error", e);
            }
            if (resultString != null && resultString.length() != 0) {
                try {
                    List<WeatherData> weatherDataAll = new LinkedList<>();
                    JSONObject jsonRoot = new JSONObject(resultString);
                    JSONArray items = jsonRoot.getJSONArray("list");
                    for (int i = 0; i < items.length(); i++) {
                        // TODO: Surround everything with try catch
                        JSONObject item = items.getJSONObject(i);
                        int dt = item.getInt("dt");
                        double temp = item.getJSONObject("main").getDouble("temp");
                        String icon = item.getJSONArray("weather").getJSONObject(0).getString("icon");
                        String condition = item.getJSONArray("weather").getJSONObject(0).getString("description");
                        String pressure = item.getJSONObject("main").getString("pressure") + "%";
                        String humidity = item.getJSONObject("main").getString("humidity") + "%";
                        String cloudCover = item.getJSONObject("clouds").getString("all") + "%";
                        String windSpeed = item.getJSONObject("wind").getString("speed") + "kph";
                        String windDirection = item.getJSONObject("wind").getString("deg") + "°";

                        String rain = getResources().getString(R.string.noRain);
                        String snow = getResources().getString(R.string.noSnow);
                        try {
                            rain = item.getJSONObject("rain").getString("rain.3h") + "mm";
                            snow = item.getJSONObject("snow").getString("snow.3h") + "mm";
                        }
                        catch (JSONException ex) {
                            Log.e(LOG_TAG, "JSON Error");
                        }

                        WeatherData weatherData = new WeatherData(dt, icon, temp, condition,
                                pressure, humidity, cloudCover, windSpeed, windDirection, rain, snow, language);
                        weatherDataAll.add(weatherData);
                    }
                    return weatherDataAll;
                } catch (JSONException ex) {
                    Log.e(LOG_TAG, "JSON Error", ex);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<WeatherData> result) {
            super.onPostExecute(result);
            if (result != null && result.size() > 0) {
                weatherData.clear();
                weatherData.addAll(result);
                mAdapter.swapData(result);
            }
            else {
                Log.e(LOG_TAG,  "Result empty");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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