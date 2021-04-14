package ru.abrakov.weathertracker.entity.enums;

public enum WeatherParam {

    TEMPERATURE("temperature"),
    FEELS_LIKE("feels_like"),
    DESCRIPTION("description"),
    STATUS("status"),
    WIND("wind");

    private String param;

    WeatherParam(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }
}
