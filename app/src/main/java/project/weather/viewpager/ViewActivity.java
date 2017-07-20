package project.weather.viewpager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import project.weather.R;
import project.weather.api.WeatherApi;
import project.weather.model.WeatherResult;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Crystal on 11/30/2016.
 */
public class ViewActivity extends AppCompatActivity {

    final public String id = "76dfacc23e8835a4b3419380dc0c401b";
    public Call<WeatherResult> weatherCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        String cityName = getIntent().getStringExtra("cityName");

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(getString(R.string.url)).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        final WeatherApi weatherApi = retrofit.create(WeatherApi.class);
        weatherCall = weatherApi.getWeather(cityName, getString(R.string.metric), id);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        ViewAdapter adapter = new ViewAdapter(getSupportFragmentManager());
        adapter.cityName = cityName;
        pager.setAdapter(adapter);
    }

}
