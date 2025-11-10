package edu.charlotte.assignment13.models;

import java.util.ArrayList;

public class Forecast {
    private ArrayList<ForecastItem> list;

    public ArrayList<ForecastItem> getList() {
        return list;
    }

    public void setList(ArrayList<ForecastItem> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "list=" + list +
                '}';
    }
}
