package edu.charlotte.assignment12;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

public class MainActivity extends AppCompatActivity implements LogsFragment.LogsListener, AddLogFragment.AddLogListener
    , SelectDateFragment.SelectDateListener, SelectSleepFragment.SelectSleepListener, SelectScaleFragment.SelectScaleListener
    , SelectExerciseFragment.SelectExerciseListener
{

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = Room.databaseBuilder(this, AppDatabase.class, "logEntry.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new LogsFragment(), "logs-fragment")
                .commit();
    }

    @Override
    public void goToAddLog() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new AddLogFragment(), "add-log-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToVisualize() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new VisualizeFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToSelectDate() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectDateFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToSelectSleep() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectSleepFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToSelectScale() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectScaleFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToSelectExercise() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectExerciseFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void submitLog(LogEntry le) {
        db.logEntryDao().insertAll(le);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void submitDate(String date, String time) {
        AddLogFragment fragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("add-log-fragment");
        fragment.date = date;
        fragment.time = time;
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void submitSleep(Double hours) {
        AddLogFragment fragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("add-log-fragment");
        fragment.sleep = hours;
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void submitScale(int scale, String s) {
        AddLogFragment fragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("add-log-fragment");
        fragment.scale = scale;
        fragment.scaleStr = s;
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void submitExercise(double hours) {
        AddLogFragment fragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("add-log-fragment");
        fragment.exercise = hours;
        getSupportFragmentManager().popBackStack();
    }
}