package com.alastor.vehiclereport.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alastor.vehiclereport.repository.roomdatabase.CategoryDao;
import com.alastor.vehiclereport.repository.roomdatabase.VehicleReportDatabase;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Category;

import java.util.List;

public class CategoryRepository {

    private CategoryDao categoryDao;

    public CategoryRepository(final Application application) {
        final VehicleReportDatabase vehicleReportDatabase = VehicleReportDatabase.getInstance(application);
        categoryDao = vehicleReportDatabase.categoryDao();
    }

    public LiveData<List<Category>> getCategory() {
        return categoryDao.getCategories();
    }
}
