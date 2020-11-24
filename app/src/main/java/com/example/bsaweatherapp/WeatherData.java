package com.example.bsaweatherapp;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static java.lang.String.valueOf;

public class WeatherData implements Serializable {

    private String dateAndTime;
    private String icon;
    private double temperature;
    private String convertedTemperature;
    private String condition;
    private String pressure;
    private String humidity;
    private String cloudCover;
    private String windSpeed;
    private String windDirection;
    private String rain;
    private String snow;

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getConvertedTemperature() {
        return convertedTemperature;
    }

    public void setConvertedTemperature(String convertedTemperature) {
        this.convertedTemperature = convertedTemperature;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(String cloudCover) {
        this.cloudCover = cloudCover;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getSnow() {
        return snow;
    }

    public void setSnow(String snow) {
        this.snow = snow;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public WeatherData(int dt, String icon, double temperature, String condition, String pressure,
                       String humidity, String cloudCover, String windSpeed, String windDirection,
                       String rain, String snow, String lang) {
        SimpleDateFormat sdf;
        if (lang.equals("de")) {
            sdf = new SimpleDateFormat("EEEE, MMMM , d, yyyy  h:mm a", Locale.GERMAN);
        }
        else {
            sdf = new SimpleDateFormat("EEEE, MMMM , d, yyyy  h:mm a", Locale.ENGLISH);
        }
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Instant instant = Instant.ofEpochSecond(dt);
        Date date = Date.from(instant);
        sdf.setTimeZone(TimeZone.getDefault());
        this.dateAndTime = sdf.format(date);
        Log.d("myLog", this.dateAndTime);
        this.icon = icon;
        this.temperature = temperature;
        this.condition = condition;
        this.pressure = pressure;
        this.humidity = humidity;
        this.cloudCover = cloudCover;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.rain = rain;
        this.snow = snow;
        this.convertedTemperature = valueOf(temperature) + "°C";
    }

    @Override
    @NonNull
    public String toString() {
        return this.dateAndTime  + "\n" + this.convertedTemperature + "\n";
    }
    public String toStringDetailed(){
        return this.dateAndTime;
    }
}
