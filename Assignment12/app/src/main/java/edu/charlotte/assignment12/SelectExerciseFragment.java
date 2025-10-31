package edu.charlotte.assignment12;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.charlotte.assignment12.databinding.FragmentSelectExerciseBinding;

public class SelectExerciseFragment extends Fragment {

    FragmentSelectExerciseBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectExerciseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayList<Double> hours = new ArrayList<>(List.of(
            0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0,
            5.5, 6.0, 6.5, 7.0, 7.5, 8.0, 8.5, 9.0, 9.5, 10.0,
            10.5, 11.0, 11.5, 12.0, 12.5, 13.0, 13.5, 14.0, 14.5, 15.0
    ));

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = binding.listView;
        ArrayAdapter<Double> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, hours);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Double time = hours.get(position);
                mListener.submitExercise(time);
            }
        });
    }

    SelectExerciseListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectExerciseListener) context;
    }

    public interface SelectExerciseListener {
        void submitExercise(double hours);
    }
}