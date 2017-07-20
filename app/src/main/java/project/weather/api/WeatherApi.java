package project.weather.api;

import project.weather.model.WeatherResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Crystal on 12/1/2016.
 */
public interface WeatherApi {

    @GET("data/2.5/weather")
    Call<WeatherResult> getWeather(@Query("q") String base,
                                   @Query("units") String units,
                                   @Query("APPID") String appid);
}