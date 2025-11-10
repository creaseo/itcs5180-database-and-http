package edu.charlotte.assignment13;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import edu.charlotte.assignment13.databinding.FragmentCurrentWeatherBinding;
import edu.charlotte.assignment13.models.DataService;
import edu.charlotte.assignment13.models.Weather;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CurrentWeatherFragment extends Fragment {
    private static final String KEY = "c06a2eec3625366cbdb82a7c2d321d1b";
    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private DataService.City mCity;
    FragmentCurrentWeatherBinding binding;
    Weather weatherObject = new Weather();
    String iconId;

    String imageUrl;

    void setupUI() {
        imageUrl = "https://openweathermap.org/img/w/" + iconId + ".png";
        Log.d("demo", "imageurl: " + imageUrl);
        Picasso.get()
                .load(imageUrl)
                .into(binding.imageView);
        binding.textViewCity.setText(mCity.getCity());
        binding.textViewDescriptionResult.setText(weatherObject.getDescription());
        binding.textViewTemperatureResult.setText(weatherObject.getTemp() + " F");
        binding.textViewTemperatureMaxResult.setText(weatherObject.getTempMax() + " F");
        binding.textViewTemperatureMinResult.setText(weatherObject.getTempMin() + " F");
        binding.textViewCloudinessResult.setText(weatherObject.getCloudiness() + "%");
        binding.textViewHumidityResult.setText(weatherObject.getHumidity() + "%");
        binding.textViewWindSpeedResult.setText(weatherObject.getWindSpeed() + " miles/hr");
        binding.textViewWindDegreeResult.setText(weatherObject.getWindDegree() + "degrees");
    }

    private final OkHttpClient client = new OkHttpClient();

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }

    public static CurrentWeatherFragment newInstance(DataService.City city) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (DataService.City) getArguments().getSerializable(ARG_PARAM_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Current Weather");

        Double lat = mCity.getLatitude();
        Double lon = mCity.getLongitude();

        HttpUrl url = HttpUrl.parse("https://api.openweathermap.org/data/2.5/weather").newBuilder()
                .addQueryParameter("lat", Double.toString(lat))
                .addQueryParameter("lon", Double.toString(lon))
                .addQueryParameter("appid", KEY)
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
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray weather = jsonObject.optJSONArray("weather");
                        JSONObject desc = weather.getJSONObject(0);
                        JSONObject main = jsonObject.optJSONObject("main");
                        JSONObject wind = jsonObject.optJSONObject("wind");
                        JSONObject clouds = jsonObject.optJSONObject("clouds");

                        Log.d("demo", "Weather description: " + desc.optString("description"));
                        iconId = desc.optString("icon");
                        weatherObject.setName(mCity.getCity());
                        weatherObject.setDescription(desc.optString("description"));
                        weatherObject.setCloudiness(clouds.optInt("all"));
                        weatherObject.setHumidity(main.optInt("humidity"));
                        weatherObject.setTemp(main.optDouble("temp"));
                        weatherObject.setTempMax(main.optDouble("temp_max"));
                        weatherObject.setTempMin(main.optDouble("temp_min"));
                        weatherObject.setWindSpeed(wind.optDouble("speed"));
                        weatherObject.setWindDegree(wind.optDouble("deg"));

                        Log.d("demo", "Weather Object: "+ weatherObject.toString());
                        requireActivity().runOnUiThread(() -> setupUI());

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        binding.buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToForecast(mCity);
            }
        });
    }

    CurrentWeatherListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CurrentWeatherListener) context;
    }

    public interface CurrentWeatherListener {
        void goToForecast(DataService.City city);
    }
}