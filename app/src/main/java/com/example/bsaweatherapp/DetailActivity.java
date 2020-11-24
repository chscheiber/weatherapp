package com.example.bsaweatherapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    public static final String VALUE_KEY = "value";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_weather);
        WeatherData wd = (WeatherData) getIntent().getSerializableExtra(VALUE_KEY);


        ImageView mItemImageView = findViewById(R.id.wdd_image);
        int id = GetDrawableResource.get(wd, wd.getIcon());
        mItemImageView.setImageResource(id);

        TextView conditionTextView = findViewById(R.id.condition);
        conditionTextView.setText(String.format(getString(R.string.condition),wd.getCondition()));
        TextView dtTextView = findViewById(R.id.dateAndTime);
        dtTextView.setText(String.format(getString(R.string.dateAndTime),wd.getDateAndTime()));
        TextView tempTextView = findViewById(R.id.temperature);
        tempTextView.setText(String.format(getString(R.string.temperature), "" + wd.getTemperature()));
        TextView pressureTextView = findViewById(R.id.pressure);
        pressureTextView.setText(String.format(getString(R.string.pressure),wd.getPressure()));
        TextView humidityTextView = findViewById(R.id.humidity);
        humidityTextView.setText(String.format(getString(R.string.humidity), wd.getHumidity()));
        TextView ccTextView = findViewById(R.id.cloudCover);
        ccTextView.setText(String.format(getString(R.string.cloudCover), wd.getCloudCover()));
        TextView wsTextView = findViewById(R.id.windSpeed);
        wsTextView.setText(String.format(getString(R.string.windSpeed), wd.getWindSpeed()));
        TextView wdTextView = findViewById(R.id.windDirection);
        wdTextView.setText(String.format(getString(R.string.windDirection), wd.getWindDirection()));
        TextView rainTextView = findViewById(R.id.rain);
        rainTextView.setText(String.format(getString(R.string.rain), wd.getRain()));
        TextView snowTextView = findViewById(R.id.snow);
        snowTextView.setText(String.format(getString(R.string.snow), wd.getSnow()));
    }
}
