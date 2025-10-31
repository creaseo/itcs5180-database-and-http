package edu.charlotte.assignment12;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.charlotte.assignment12.databinding.FragmentVisualizeBinding;

public class VisualizeFragment extends Fragment {


    AppDatabase db;
    FragmentVisualizeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = Room.databaseBuilder(getContext(), AppDatabase.class, "logEntry.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        binding = FragmentVisualizeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<LogEntry> logs = db.logEntryDao().getAll();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.getDefault());

        Collections.sort(logs, new Comparator<LogEntry>() {
            @Override
            public int compare(LogEntry l1, LogEntry l2) {
                try {
                    long t1 = sdf.parse(l1.date + " " + l1.time).getTime();
                    long t2 = sdf.parse(l2.date + " " + l2.time).getTime();
                    return Long.compare(t1, t2); // ascending (oldest first)
                } catch (ParseException e) {
                    return 0;
                }
            }
        });

        setupChart(binding.chartSleep, logs, "Sleep Hours");
        setupChart(binding.chartExercise, logs, "Exercise Hours");
        setupChart(binding.chartWeight, logs, "Weight");
        setupChart(binding.chartQuality, logs, "Quality");

    }

    private void setupChart(LineChart chart, List<LogEntry> logs, String label) {
        ArrayList<Entry> entries = new ArrayList<>();

        for (int i = 0; i < logs.size(); i++) {
            LogEntry log = logs.get(i);
            int xValue = i;

            float yValue = 0;
            switch (label) {
                case "Sleep Hours":
                    yValue = (float) log.sleep;
                    break;
                case "Exercise Hours":
                    yValue = (float) log.exercise;
                    break;
                case "Weight":
                    yValue = (float) log.weight;
                    break;
                case "Quality":
                    yValue = (float) log.scale;
                    break;
            }

            entries.add(new Entry(xValue, yValue));
        }

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index >= 0 && index < logs.size()) {
                    LogEntry log = logs.get(index);
                    return log.date;
                    // return log.date + " " + log.time;
                }
                return "";
            }
        });

        LineDataSet dataSet = new LineDataSet(entries, label);
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(3f);
        chart.setData(new LineData(dataSet));
    }
}