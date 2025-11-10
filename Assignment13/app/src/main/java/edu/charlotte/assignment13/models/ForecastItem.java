package edu.charlotte.assignment13.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ForecastItem {
    FWeather main;
    ArrayList<Main> weather;
    Clouds clouds;
    Wind wind;
    @SerializedName("dt_txt")
    private String dtTxt;

    public String getDt() {
        return dtTxt;
    }

    public void setDt(String dttxt) {
        this.dtTxt = dttxt;
    }

    public FWeather getMain() {
        return main;
    }

    public void setMain(FWeather main) {
        this.main = main;
    }

    public ArrayList<Main> getWeather() {
        return weather;
    }

    public void setWeather(ArrayList<Main> weather) {
        this.weather = weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        return "ForecastItem{" +
                "main=" + main +
                ", weather=" + weather +
                ", clouds=" + clouds +
                ", wind=" + wind +
                '}';
    }
}
