package edu.charlotte.assignment13.models;

public class FWeather {
    Double temp;
    Double temp_max;
    Double temp_min;
    int humidity;

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Double temp_max) {
        this.temp_max = temp_max;
    }

    public Double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(Double temp_min) {
        this.temp_min = temp_min;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "FWeather{" +
                "temp=" + temp +
                ", temp_max=" + temp_max +
                ", temp_min=" + temp_min +
                ", humidity=" + humidity +
                '}';
    }
}
