package edu.charlotte.assignment11.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.charlotte.assignment11.AppDatabase;
import edu.charlotte.assignment11.MainActivity;
import edu.charlotte.assignment11.R;
import edu.charlotte.assignment11.databinding.FragmentTasksBinding;
import edu.charlotte.assignment11.databinding.TaskListItemBinding;
import edu.charlotte.assignment11.models.Data;
import edu.charlotte.assignment11.models.Priority;
import edu.charlotte.assignment11.models.Task;

public class TasksFragment extends Fragment {
    public TasksFragment() {
        // Required empty public constructor
    }

    FragmentTasksBinding binding;
    ArrayList<Task> tasks = new ArrayList<>();
    AppDatabase db;

    void clearAll() {
        db.taskDao().deleteAll();
        tasks.clear();
    }
    public void deleteTask(Task task) {
        db.taskDao().delete(task);
        tasks.remove(task);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = Room.databaseBuilder(getActivity(), AppDatabase.class, "task.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        tasks.clear();
        tasks.addAll(db.taskDao().getALl());
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public TaskRecyclerViewAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TaskRecyclerViewAdapter(tasks);
        recyclerView.setAdapter(adapter);

        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddTask();
            }
        });

        binding.buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll();
                adapter.notifyDataSetChanged();
            }
        });

        binding.imageViewSortAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textViewSortIndicator.setText("Sort by Priority (ASC)");
                tasks.clear();
                tasks.addAll(db.taskDao().getAllSortedAsc());
                adapter.notifyDataSetChanged();
            }
        });

        binding.imageViewSortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textViewSortIndicator.setText("Sort by Priority (DESC)");
                tasks.clear();
                tasks.addAll(db.taskDao().getAllSortedDesc());
                adapter.notifyDataSetChanged();
            }
        });
    }

    class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskViewHolder> {

        ArrayList<Task> mTasks;
        TaskListItemBinding binding;
        public TaskRecyclerViewAdapter(ArrayList<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            binding = TaskListItemBinding.inflate(getLayoutInflater(), parent, false);
            return new TaskViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            Task t = mTasks.get(position);
            holder.task = t;
            holder.mBinding.textViewName.setText(t.name);
            holder.mBinding.textViewCategory.setText(t.category);
            holder.mBinding.textViewPriority.setText(t.priority);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }

        class TaskViewHolder extends RecyclerView.ViewHolder {

            Task task;
            TaskListItemBinding mBinding;
            public TaskViewHolder(@NonNull TaskListItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
                mBinding.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteTask(task);
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoTaskDetails(task);
                    }
                });
            }
        }
    }

    TasksListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TasksListener) {
            mListener = (TasksListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement TasksListener");
        }
    }

    public interface TasksListener{
        void gotoAddTask();
        void gotoTaskDetails(Task task);
        void clearAll();
    }
}