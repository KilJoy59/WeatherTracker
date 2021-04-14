package ru.abrakov.weathertracker.util;

import java.util.function.Consumer;

public interface Updatable {

    default <T> void setIfNotNull(final Consumer<T> setter, final T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
