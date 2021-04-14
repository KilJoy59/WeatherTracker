package ru.abrakov.weathertracker.entity.weather;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WeatherUrl {

    private String apiKey = "ac8202174d44d5907d95d3c2ddfa7782";

    private String url;

    public WeatherUrl(String location) {
        this.url = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + apiKey + "&units=metric&lang=ru";
    }

    public String getUrl() {
        return url;
    }
}
