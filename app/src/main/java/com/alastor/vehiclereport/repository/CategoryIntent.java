package com.alastor.vehiclereport.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.alastor.vehiclereport.repository.CategoryIntent.Status.COMPLETE;
import static com.alastor.vehiclereport.repository.CategoryIntent.Status.ERROR;
import static com.alastor.vehiclereport.repository.CategoryIntent.Status.LOADING;
import static com.alastor.vehiclereport.repository.CategoryIntent.Status.MAIN_PART;
import static com.alastor.vehiclereport.repository.CategoryIntent.Status.ON_NEXT_PART;

public class CategoryIntent<T, R> {

    public final Status status;

    @Nullable
    public final T mainData;

    @Nullable
    public final R nextData;

    @Nullable
    public final Throwable error;

    private CategoryIntent(Status status, @Nullable T mainData, @Nullable R nextData, @Nullable Throwable error) {
        this.status = status;
        this.mainData = mainData;
        this.nextData = nextData;
        this.error = error;
    }

    public static <T, R> CategoryIntent<T, R> loading() {
        return new CategoryIntent<T, R>(LOADING, null, null, null);
    }

    public static <T, R> CategoryIntent<T, R> onMainData(@NonNull T mainData) {
        return new CategoryIntent<T, R>(MAIN_PART, mainData, null, null);
    }

    public static <T, R> CategoryIntent<T, R> onNext(@NonNull R nextData) {
        return new CategoryIntent<T, R>(ON_NEXT_PART, null, nextData, null);
    }

    public static <T, R> CategoryIntent<T, R> complete() {
        return new CategoryIntent<T, R>(COMPLETE, null, null, null);
    }

    public static <T, R> CategoryIntent<T, R> error(@NonNull Throwable error) {
        return new CategoryIntent<T, R>(ERROR, null, null, error);
    }

    public enum Status {
        LOADING,
        MAIN_PART,
        ON_NEXT_PART,
        COMPLETE,
        ERROR
    }
}
