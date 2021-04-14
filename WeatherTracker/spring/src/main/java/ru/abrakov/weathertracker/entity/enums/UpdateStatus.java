package ru.abrakov.weathertracker.entity.enums;

public enum UpdateStatus {

    ERROR("Ошибка"),
    OK("Успешно");

    private String status;


    UpdateStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
