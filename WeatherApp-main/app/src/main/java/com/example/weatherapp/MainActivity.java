package com.example.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.weatherapp.viewmodel.WeatherViewModel;

public class MainActivity extends AppCompatActivity {

    private WeatherViewModel weatherViewModel;
    private RelativeLayout weatherCard;
    private RelativeLayout errorCard;
    private TextView weatherInfo;
    private TextView errorInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        // Initialize views
        SearchView searchView = findViewById(R.id.searchView);
        weatherInfo = findViewById(R.id.weatherInfo);
        errorInfo = findViewById(R.id.errorInfo);
        weatherCard = findViewById(R.id.weatherCard);
        errorCard = findViewById(R.id.errorCard);

        // Observe weather data
        weatherViewModel.getWeather().observe(this, weatherModel -> {
            if (weatherModel != null) {
                String mainWeather = weatherModel.getWeather()[0].getMain();
                String description = weatherModel.getWeather()[0].getDescription();
                double temp = weatherModel.getMain().getTemp();

                weatherInfo.setText(String.format("Temperature: %.2f°C\nMain: %s\nDescription: %s",
                        temp, mainWeather, description));
                weatherCard.setVisibility(View.VISIBLE); // Show weatherCard
                errorCard.setVisibility(View.GONE); // Hide errorCard
            }
        });

        // Observe error message
        weatherViewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                errorInfo.setText(error);
                weatherCard.setVisibility(View.GONE); // Hide weatherCard
                errorCard.setVisibility(View.VISIBLE); // Show errorCard
            }
        });

        // SearchView query listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                weatherViewModel.fetchWeather(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Optionally handle text changes if needed
                return false;
            }
        });
    }
}
