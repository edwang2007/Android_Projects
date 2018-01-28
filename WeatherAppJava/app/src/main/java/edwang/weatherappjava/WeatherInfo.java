package edwang.weatherappjava;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import edwang.weatherappjava.adapter.CityRecyclerAdapter;
import edwang.weatherappjava.data.weather.MoneyResult;
import edwang.weatherappjava.retrofit.WeatherAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherInfo extends AppCompatActivity {

    private TextView tvWeatherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);

        tvWeatherInfo = (TextView) findViewById(R.id.tvWeatherInfo);
        final ImageView ivIcon = (ImageView) findViewById(R.id.ivIcon);
        final TextView tvName = (TextView) findViewById(R.id.tvCityName);
        final TextView tvTemp = (TextView) findViewById(R.id.tvTemp);

        if (getIntent().hasExtra(CityRecyclerAdapter.CITY_KEY)) {
            final String cityName = getIntent().getStringExtra(CityRecyclerAdapter.CITY_KEY);


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.openweathermap.org")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);

            Call<MoneyResult> temperature = weatherAPI.getTemperature(cityName, "metric", "21e64580facde9f508d319a08c2f1206");


            temperature.enqueue(new Callback<MoneyResult>() {
                @Override
                public void onResponse(Call<MoneyResult> call, Response<MoneyResult> response) {
                    MoneyResult moneyResult = response.body();

                    int timestampSunrise = moneyResult.getSys().sunrise * 1000;
                    String dateSunRise =
                            new java.text.SimpleDateFormat(
                                    "HH:mm:ss").format(
                                    new java.util.Date(timestampSunrise));
                    int timestampSunSet = moneyResult.getSys().sunset * 1000;
                    String dateSunSet =
                            new java.text.SimpleDateFormat(
                                    "HH:mm:ss").format(
                                    new java.util.Date(timestampSunSet));

                    tvName.setText(cityName);
                    tvTemp.setText(moneyResult.getMain().temp + "*C");
                    tvWeatherInfo.setText(
                                    getString(R.string.min_temp) + moneyResult.getMain().getTempMin()
                                    + "\n" + getString(R.string.max_temp) + moneyResult.getMain().getTempMax()
                                    + "\n" + getString(R.string.humidity) + moneyResult.getMain().humidity
                                    + "\n" + getString(R.string.wind) + moneyResult.getWind().speed
                                    + "\n" + getString(R.string.decription) + moneyResult.weather.get(0).description
                                    + "\n" + getString(R.string.sunrise) + dateSunRise
                                    + "\n" + getString(R.string.sunset) + dateSunSet
                    );
                    String iconURL = "http://openweathermap.org/img/w/" + moneyResult.weather.get(0).icon + ".png";
                    Glide.with(WeatherInfo.this).load(iconURL).into(ivIcon);
                }

                @Override
                public void onFailure(Call<MoneyResult> call, Throwable t) {
                    tvWeatherInfo.setText(getString(R.string.error) + t.getMessage());

                }
            });

            Button btnBack = (Button) findViewById(R.id.btnBack);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(WeatherInfo.this, MainActivity.class));
                }
            });
        }
    }
}
