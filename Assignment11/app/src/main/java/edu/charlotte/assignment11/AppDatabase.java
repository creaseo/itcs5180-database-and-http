package edu.charlotte.assignment11;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import edu.charlotte.assignment11.models.Task;

@Database(entities = {Task.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
