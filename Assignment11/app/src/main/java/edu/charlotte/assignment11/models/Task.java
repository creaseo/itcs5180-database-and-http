package edu.charlotte.assignment11.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tasks")
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String category;

    @ColumnInfo
    public String priority;

    @ColumnInfo
    public int level;

    public Task(long id, String name, String category, String priority, int level) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.priority = priority;
        this.level = level;
    }

    public Task(String name, String category, String priority, int level) {
        this.name = name;
        this.category = category;
        this.priority = priority;
        this.level = level;
    }

    public Task() {
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
