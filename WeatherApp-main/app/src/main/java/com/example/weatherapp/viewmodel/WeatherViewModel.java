package com.example.weatherapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.model.WeatherModel;
import com.example.weatherapp.service.WeatherService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherViewModel extends ViewModel {
    private final WeatherService weatherService = WeatherService.Factory.create();
    private final MutableLiveData<WeatherModel> weather = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<WeatherModel> getWeather() {
        return weather;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void fetchWeather(String city) {
        weatherService.getWeather(city, "a7bdec6c024aeaf0654719a0c4a106c9", "metric").enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    weather.setValue(response.body());
                    errorMessage.setValue(null);
                } else {
                    errorMessage.setValue("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                errorMessage.setValue(t.getMessage());
            }
        });
    }
}