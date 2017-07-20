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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import project.weather.R;
import project.weather.model.WeatherResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentOne extends Fragment {

    private TextView tvTemp;
    private TextView tvDesc;
    private ImageView image;


    private Call<WeatherResult> weatherCall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_one, null);

        weatherCall = ((ViewActivity) getContext()).weatherCall;

        tvTemp = (TextView) rootView.findViewById(R.id.tvTemp);
        image = (ImageView) rootView.findViewById(R.id.image);
        tvDesc = (TextView) rootView.findViewById(R.id.tvDesc);

        getWeather();
        return rootView;
    }

    private void getWeather() {
        weatherCall.clone().enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {

                String idIcon = response.body().getWeather().get(0).getIcon();
                String url = "http://openweathermap.org/img/w/" + idIcon + ".png";
                Glide.with(getActivity()).load(url).into(image);

                String temp = (response.body().getMain().getTemp()).toString();
                tvTemp.setText("Current Temperature: \n" + temp);

                String desc = response.body().getWeather().get(0).getDescription();
                tvDesc.setText("\n\nConditions: " +  Character.toUpperCase(desc.charAt(0)) + desc.substring(1));
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
                Toast.makeText(getContext(), "error",
                        Toast.LENGTH_SHORT).show();
                Log.d("TAG_API", t.toString());
            }
        });
    }
}