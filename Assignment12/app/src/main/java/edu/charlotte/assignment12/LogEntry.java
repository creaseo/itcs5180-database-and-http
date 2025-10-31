package edu.charlotte.assignment12;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "logs")
public class LogEntry implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String date;

    @ColumnInfo
    public String time;

    @ColumnInfo
    public double sleep;

    @ColumnInfo int scale;

    @ColumnInfo
    public double exercise;

    @ColumnInfo
    public double weight;

    public LogEntry(long id, String date, String time, double sleep, int scale, double exercise, double weight) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.sleep = sleep;
        this.scale = scale;
        this.exercise = exercise;
        this.weight = weight;
    }

    public LogEntry(String date, String time, double sleep, int scale, double exercise, double weight) {
        this.date = date;
        this.time = time;
        this.sleep = sleep;
        this.scale = scale;
        this.exercise = exercise;
        this.weight = weight;
    }

    public LogEntry() {
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", sleep=" + sleep +
                ", exercise=" + exercise +
                ", weight=" + weight +
                '}';
    }
}
