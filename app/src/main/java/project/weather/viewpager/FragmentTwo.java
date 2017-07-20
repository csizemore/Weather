package project.weather.viewpager;

/**
 * Created by Crystal on 11/30/2016.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import project.weather.R;
import project.weather.model.WeatherResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTwo extends Fragment {

    private Call<WeatherResult> weatherCall;

    private TextView minTemp;
    private TextView maxTemp;
    private TextView humidity;
    private TextView windSpeed;
    private TextView pressure;
    private TextView visibility;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_two, null);

        weatherCall = ((ViewActivity) getContext()).weatherCall;

        minTemp = (TextView) rootView.findViewById(R.id.minTemp);
        maxTemp = (TextView) rootView.findViewById(R.id.maxTemp);
        humidity = (TextView) rootView.findViewById(R.id.humidity);
        pressure = (TextView) rootView.findViewById(R.id.pressure);
        windSpeed = (TextView) rootView.findViewById(R.id.windSpeed);
        visibility = (TextView) rootView.findViewById(R.id.visibility);

        getWeather();
        return rootView;
    }

    private void getWeather() {
        weatherCall.clone().enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                String tempMinS = (response.body().getMain().getTempMin()).toString();
                minTemp.setText("Temp Min.: " + tempMinS);

                String tempMaxS = (response.body().getMain().getTempMax()).toString();
                maxTemp.setText("Temp Max.: " + tempMaxS);

                String humidityS = (response.body().getMain().getHumidity()).toString();
                humidity.setText("Humidity: " + humidityS);

                String windSpeedS = (response.body().getWind().getSpeed()).toString();
                windSpeed.setText("Windspeed: " + windSpeedS);

                String pressureS = (response.body().getMain().getPressure()).toString();
                pressure.setText("Pressure: " + pressureS);

                Double visibilityD = response.body().getVisibility()/1000.00;
                String visibilityS = visibilityD.toString();
                visibility.setText("Visibility: " + visibilityS);
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
                Toast.makeText(getContext(), R.string.error,
                        Toast.LENGTH_SHORT).show();
                Log.d("TAG_API", t.toString());
            }
        });
    }
}