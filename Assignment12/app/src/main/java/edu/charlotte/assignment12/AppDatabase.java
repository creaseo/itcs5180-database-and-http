package edu.charlotte.assignment12;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import edu.charlotte.assignment12.LogEntry;

@Database(entities = {LogEntry.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LogEntryDao logEntryDao();
}