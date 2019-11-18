package com.alastor.vehiclereport.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.alastor.vehiclereport.repository.CategoryRepository;

public class MainFragmentViewModel extends AndroidViewModel {

    private CategoryRepository categoryRepository;

    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
    }
}