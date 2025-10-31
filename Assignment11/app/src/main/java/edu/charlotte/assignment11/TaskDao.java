package edu.charlotte.assignment11;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.charlotte.assignment11.models.Task;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM tasks")
    List<Task> getALl();

    @Query("SELECT * FROM tasks WHERE id = :id limit 1")
    Task findById(long id);

    @Query("DELETE FROM tasks")
    void deleteAll();

    @Query("SELECT * FROM tasks ORDER BY level ASC")
    List<Task> getAllSortedAsc();

    @Query("SELECT * FROM tasks ORDER BY level DESC")
    List<Task> getAllSortedDesc();
    @Update
    void updateTask(Task task);

    @Insert
    void insertAll(Task... tasks);

    @Delete
    void delete(Task task);
}
