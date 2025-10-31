package edu.charlotte.assignment12;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import edu.charlotte.assignment12.LogEntry;

@Dao
public interface LogEntryDao {
    @Query("SELECT * FROM logs")
    List<LogEntry> getAll();

    @Query("SELECT * FROM logs WHERE id = :id limit 1")
    LogEntry findById(long id);

    @Query("DELETE FROM logs")
    void deleteAll();

//    @Query("SELECT * FROM logs ORDER BY (insert param) ASC")
//    List<LogEntry> getAllSortedAsc();
//
//    @Query("SELECT * FROM logs ORDER BY (insert param) DESC")
//    List<LogEntry> getAllSortedDesc();
    @Update
    void updateTask(LogEntry log);

    @Insert
    void insertAll(LogEntry... logs);

    @Delete
    void delete(LogEntry log);
}
