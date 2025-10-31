package edu.charlotte.assignment12;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import edu.charlotte.assignment12.databinding.FragmentSelectScaleBinding;

public class SelectScaleFragment extends Fragment {

    FragmentSelectScaleBinding binding;
    String[] scales = {"5 - Excellent","4 - Very Good","3 - Good","2 - Fair","1 - Poor"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectScaleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = binding.listView;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, scales);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = scales[position];
                int val = s.charAt(0) - '0';
                Log.d("demo", "Selected scale: " + val);
                mListener.submitScale(val, s);
            }
        });
    }

    SelectScaleListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectScaleListener) context;
    }

    public interface SelectScaleListener {
        void submitScale(int scale, String s);
    }
}