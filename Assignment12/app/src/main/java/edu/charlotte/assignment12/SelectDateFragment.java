package edu.charlotte.assignment12;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import edu.charlotte.assignment12.databinding.FragmentSelectDateBinding;

public class SelectDateFragment extends Fragment {

    FragmentSelectDateBinding binding;
    Calendar selectedDateTime = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
    SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm a", Locale.getDefault());

    String dateStr = null;
    String timeStr = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectDateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonDateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        binding.buttonTimeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
        binding.buttonSubmitDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateStr == null) {
                    Toast.makeText(getContext(), "Please select a valid date!", Toast.LENGTH_SHORT).show();
                } else if (timeStr == null) {
                    Toast.makeText(getContext(), "Please select a valid time!", Toast.LENGTH_SHORT).show();
                } else {
                    mListener.submitDate(dateStr, timeStr);
                }
            }
        });
    }

    private void showDatePicker() {
        int year = selectedDateTime.get(Calendar.YEAR);
        int month = selectedDateTime.get(Calendar.MONTH);
        int day = selectedDateTime.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year1, month1, dayOfMonth) -> {
                    selectedDateTime.set(Calendar.YEAR, year1);
                    selectedDateTime.set(Calendar.MONTH, month1);
                    selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    dateStr = sdf.format(selectedDateTime.getTime());
                    binding.textViewDateShow.setText("Date: " + dateStr);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void showTimePicker() {
        int hour = selectedDateTime.get(Calendar.HOUR_OF_DAY);
        int minute = selectedDateTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                (view, hourOfDay, minute1) -> {
                    selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    selectedDateTime.set(Calendar.MINUTE, minute1);
                    timeStr = sdfTime.format(selectedDateTime.getTime());
                    binding.textViewTimeShow.setText("Time: " + timeStr);
                },
                hour, minute, false
        );
        timePickerDialog.show();
    }
    SelectDateListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectDateListener) context;
    }

    public interface SelectDateListener {
        void submitDate(String date, String time);
    }
}
