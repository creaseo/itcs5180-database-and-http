package edu.charlotte.assignment13;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import edu.charlotte.assignment13.databinding.FragmentWeatherForecastBinding;
import edu.charlotte.assignment13.databinding.ListItemForecastBinding;
import edu.charlotte.assignment13.models.Clouds;
import edu.charlotte.assignment13.models.DataService;
import edu.charlotte.assignment13.models.FWeather;
import edu.charlotte.assignment13.models.Forecast;
import edu.charlotte.assignment13.models.ForecastItem;
import edu.charlotte.assignment13.models.Main;
import edu.charlotte.assignment13.models.Weather;
import edu.charlotte.assignment13.models.Wind;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherForecastFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private Forecast forecast;
    private static final String KEY = "c06a2eec3625366cbdb82a7c2d321d1b";
    private final OkHttpClient client = new OkHttpClient();

    // TODO: Rename and change types of parameters
    private DataService.City mCity;
    FragmentWeatherForecastBinding binding;

    public WeatherForecastFragment() {
        // Required empty public constructor
    }
    void setupRecyclerView() {
        ArrayList<ForecastItem> items = forecast.getList();
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ForecastRecyclerViewAdapter adapter = new ForecastRecyclerViewAdapter(items);
        recyclerView.setAdapter(adapter);
    }

    public static WeatherForecastFragment newInstance(DataService.City param1) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (DataService.City) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherForecastBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textViewCity.setText(mCity.getCity());

        Double lat = mCity.getLatitude();
        Double lon = mCity.getLongitude();

        HttpUrl url = HttpUrl.parse("https://api.openweathermap.org/data/2.5/forecast").newBuilder()
                .addQueryParameter("lat", Double.toString(lat))
                .addQueryParameter("lon", Double.toString(lon))
                .addQueryParameter("appid", KEY)
                .addQueryParameter("cnt", "5")
                .addQueryParameter("units", "imperial")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    forecast = gson.fromJson(response.body().charStream(), Forecast.class);
                    Log.d("demo", "Forecast: " + forecast);

                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setupRecyclerView();
                        }
                    });
                }
            }
        });
    }
    class ForecastRecyclerViewAdapter extends RecyclerView.Adapter<ForecastRecyclerViewAdapter.ForecastViewHolder> {

        ListItemForecastBinding binding;
        ArrayList<ForecastItem> forecastItems;

        public ForecastRecyclerViewAdapter(ArrayList<ForecastItem> forecastItems) {
            this.forecastItems = forecastItems;
        }

        @NonNull
        @Override
        public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            binding = ListItemForecastBinding.inflate(getLayoutInflater(), parent, false);
            return new ForecastViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
            holder.forecastItem = forecastItems.get(position);
            holder.setupUI();
        }

        @Override
        public int getItemCount() {
            return forecastItems.size();
        }

        class ForecastViewHolder extends RecyclerView.ViewHolder {
            ForecastItem forecastItem;
            ListItemForecastBinding mBinding;
            public ForecastViewHolder(@NonNull ListItemForecastBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            void setupUI() {
                // terribly named objects but i was too lazy to swap them (FWeather and Main)
                FWeather weather = forecastItem.getMain();
                ArrayList<Main> object = forecastItem.getWeather();
                Main main = object.get(0);
                // didn't realize that these aren't needed but good practice anyway
                Wind wind = forecastItem.getWind();
                Clouds clouds = forecastItem.getClouds();

                String imgUrl = "https://openweathermap.org/img/w/" + main.getIcon() + ".png";
                Picasso.get()
                        .load(imgUrl)
                        .into(mBinding.imageView);
                mBinding.textViewDt.setText(forecastItem.getDt());
                mBinding.textViewTemp.setText(weather.getTemp() + " F");
                mBinding.textViewTempMax.setText(weather.getTemp_max() + " F");
                mBinding.textViewTempMin.setText(weather.getTemp_min() + " F");
                mBinding.textViewHumidity.setText(weather.getHumidity() + "%");
                mBinding.textViewDescription.setText(main.getDescription());
            }
        }
    }
}