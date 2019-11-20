package com.alastor.vehiclereport.repository;

import android.app.Application;

import com.alastor.vehiclereport.repository.roomdatabase.VehicleReportDatabase;
import com.alastor.vehiclereport.repository.roomdatabase.dao.ReportDao;

public class ReportRepository {

    private ReportDao reportDao;

    private ReportRepository(final Application application) {
        final VehicleReportDatabase vehicleReportDatabase
                = VehicleReportDatabase.getInstance(application);
        reportDao = vehicleReportDatabase.reportsDao();
    }
}
