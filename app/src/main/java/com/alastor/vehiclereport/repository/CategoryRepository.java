package com.alastor.vehiclereport.repository;

import android.app.Application;

import com.alastor.vehiclereport.repository.roomdatabase.VehicleReportDatabase;
import com.alastor.vehiclereport.repository.roomdatabase.dao.CategoryDao;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Category;

import java.util.List;

import io.reactivex.Observable;

public class CategoryRepository {

    private CategoryDao categoryDao;

    public CategoryRepository(final Application application) {
        final VehicleReportDatabase vehicleReportDatabase
                = VehicleReportDatabase.getInstance(application);
        categoryDao = vehicleReportDatabase.categoryDao();
    }

    public Observable<List<Category>> getCategory() {
        return categoryDao.getCategories();
    }
}
