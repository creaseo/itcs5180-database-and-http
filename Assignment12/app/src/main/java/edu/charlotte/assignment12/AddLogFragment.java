package edu.charlotte.assignment12;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.charlotte.assignment12.databinding.FragmentAddLogBinding;

public class AddLogFragment extends Fragment {

    FragmentAddLogBinding binding;
    String date = null;
    String time = null;
    Double sleep = null;
    int scale = -1;
    String scaleStr = null;
    Double exercise = null;
    Double weight = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddLogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (date != null) {
            binding.textViewAddDate.setText("Date: " + date);
        }
        if (time != null) {
            binding.textViewAddTime.setText("Time: " + time);
        }
        if (sleep != null) {
            binding.textViewAddSleep.setText("Hours slept: " + sleep);
        }
        if (scale > -1) {
            binding.textViewAddScale.setText("Quality: " + scaleStr);
        }
        if (exercise != null) {
            binding.textViewAddExercise.setText("Hours exercised: " + exercise);
        }

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date == null) {
                    Toast.makeText(getContext(), "Please select valid Date and time!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (sleep == null) {
                    Toast.makeText(getContext(), "Please select valid sleep hours!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (scale < 0) {
                    Toast.makeText(getContext(), "Please select valid Quality!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (exercise == null) {
                    Toast.makeText(getContext(), "Please select valid exercise hours!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (binding.editTextWeight.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter a valid weight!", Toast.LENGTH_SHORT).show();
                    return;
                } else try {
                    weight = Double.parseDouble(binding.editTextWeight.getText().toString());
                    if (weight <= 0) {
                        Toast.makeText(getContext(), "Please enter a valid positive weight!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Invalid number, please try again!", Toast.LENGTH_SHORT).show();
                    return;
                }
                LogEntry le = new LogEntry(date, time, sleep, scale, exercise, weight);
                mListener.submitLog(le);
            }
        });

        binding.buttonSelectDatetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToSelectDate();
            }
        });
        binding.buttonSelectSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToSelectSleep();
            }
        });
        binding.buttonSelectScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToSelectScale();
            }
        });
        binding.buttonSelectExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToSelectExercise();
            }
        });
    }
    AddLogListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddLogListener) context;
    }

    public interface AddLogListener {
        void goToSelectDate();
        void goToSelectSleep();
        void goToSelectScale();
        void goToSelectExercise();
        void submitLog(LogEntry le);
    }
}