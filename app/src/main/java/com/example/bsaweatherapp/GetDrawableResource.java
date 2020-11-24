package com.example.bsaweatherapp;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class GetDrawableResource {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static int get(WeatherData data, String name) {
        Map<String, Integer> icons = new HashMap<>();
        icons.put("01n", R.drawable._01n);
        icons.put("02n", R.drawable._02n);
        icons.put("03n", R.drawable._03n);
        //TODO: ICONS downloaden und einfügen https://openweathermap.org/weather-conditions
        int id = R.drawable._01n;
        try {
            id = icons.get(data.getIcon());
        }
        catch (NullPointerException e){
            Log.e(LOG_TAG, "Error");
        }
        return id;
    }
}
