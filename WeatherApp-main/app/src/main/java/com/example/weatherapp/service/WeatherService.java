package com.example.weatherapp.service;

import com.example.weatherapp.model.WeatherModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface WeatherService {
    @GET("weather")
    Call<WeatherModel> getWeather(@Query("q") String city, @Query("appid") String apiKey, @Query("units") String units);

    class Factory {
        private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

        public static WeatherService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            return retrofit.create(WeatherService.class);
        }
    }
}