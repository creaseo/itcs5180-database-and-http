package edu.charlotte.assignment12;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.charlotte.assignment12.databinding.FragmentLogsBinding;
import edu.charlotte.assignment12.databinding.ListItemLogBinding;

public class LogsFragment extends Fragment {

    public LogsFragment() {
        // Required empty public constructor
    }

    public AppDatabase db;
    public ArrayList<LogEntry> logs = new ArrayList<>();
    LogsRecyclerViewAdapter adapter;
    FragmentLogsBinding binding;

    public void deleteLog(LogEntry log) {
        db.logEntryDao().delete(log);
        logs.remove(log);
        adapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = Room.databaseBuilder(getContext(), AppDatabase.class, "logEntry.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        if (db.logEntryDao().getAll().isEmpty()) {
            db.logEntryDao().insertAll(
                    new LogEntry("1/1/1111", "12:00", 8.0, 4, 1.0, 120.0),
                    new LogEntry("2/2/2222", "1:00", 8.5, 5, 2.0, 155.0),
                    new LogEntry("3/3/3333", "1:44", 6.0, 3, 1.5, 127.2)
            );
        }
        logs.clear();
        logs.addAll(db.logEntryDao().getAll());
        binding = FragmentLogsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LogsRecyclerViewAdapter(logs);
        recyclerView.setAdapter(adapter);

        binding.buttonAddLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToAddLog();
            }
        });
        binding.buttonVisualize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToVisualize();
            }
        });
    }
    class LogsRecyclerViewAdapter extends RecyclerView.Adapter<LogsRecyclerViewAdapter.LogsViewHolder> {

        ListItemLogBinding mBinding;
        ArrayList<LogEntry> mLogs;

        public LogsRecyclerViewAdapter(ArrayList<LogEntry> mLogs) {
            this.mLogs = mLogs;
        }

        @NonNull
        @Override
        public LogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mBinding = ListItemLogBinding.inflate(getLayoutInflater(), parent, false);
            return new LogsViewHolder(mBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull LogsViewHolder holder, int position) {
            LogEntry log = mLogs.get(position);
            holder.mLog = log;
            holder.mmBinding.textViewDate.setText(log.date);
            holder.mmBinding.textViewTime.setText(log.time);
            holder.mmBinding.textViewSleep.setText("Hours slept: " + log.sleep);
            holder.mmBinding.textViewExercise.setText("Hours exercised: " + log.exercise);
            holder.mmBinding.textViewWeight.setText("Recorded weight: " + log.weight);
        }

        @Override
        public int getItemCount() {
            return mLogs.size();
        }

        class LogsViewHolder extends RecyclerView.ViewHolder {
            ListItemLogBinding mmBinding;
            LogEntry mLog;
            public LogsViewHolder(@NonNull ListItemLogBinding mBinding) {
                super(mBinding.getRoot());
                mmBinding = mBinding;
                mmBinding.buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteLog(mLog);
                    }
                });
            }
        }
    }

    LogsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (LogsListener) context;
    }

    public interface LogsListener {
        void goToAddLog();
        void goToVisualize();
    }
}