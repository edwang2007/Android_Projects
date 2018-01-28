package edwang.weatherappjava.retrofit;

import edwang.weatherappjava.data.weather.MoneyResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by student on 29/06/2017.
 */

public interface WeatherAPI {


// http://api.openweathermap.org/data/2.5/weather
// ?q=Budapest,hu&units=metric&appid=f3d694bc3e1d44c1ed5a97bd1120e8fe
    @GET("/data/2.5/weather")
    Call<MoneyResult> getTemperature(
            @Query("q") String location,
            @Query("units") String unit,
            @Query("appid") String appid);
}
