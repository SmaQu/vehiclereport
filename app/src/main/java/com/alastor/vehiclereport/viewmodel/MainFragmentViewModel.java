package com.alastor.vehiclereport.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.alastor.vehiclereport.repository.CategoryRepository;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Category;

import java.util.List;

public class MainFragmentViewModel extends AndroidViewModel {

    private CategoryRepository categoryRepository;

    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
    }

    public LiveData<List<Category>> getCategory() {
        return categoryRepository.getCategory();
    }
}