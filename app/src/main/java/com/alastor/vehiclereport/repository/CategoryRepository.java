package com.alastor.vehiclereport.repository;

import android.app.Application;

import com.alastor.vehiclereport.repository.roomdatabase.CategoryDao;
import com.alastor.vehiclereport.repository.roomdatabase.VehicleReportDatabase;

public class CategoryRepository {

    private CategoryDao categoryDao;

    public CategoryRepository(final Application application) {
        final VehicleReportDatabase vehicleReportDatabase = VehicleReportDatabase.getInstance(application);
        categoryDao = vehicleReportDatabase.categoryDao();
    }
}
