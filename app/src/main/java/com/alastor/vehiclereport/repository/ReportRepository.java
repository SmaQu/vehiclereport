package com.alastor.vehiclereport.repository;

import android.app.Application;

import com.alastor.vehiclereport.repository.roomdatabase.VehicleReportDatabase;
import com.alastor.vehiclereport.repository.roomdatabase.dao.ReportDao;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Report;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class ReportRepository {

    private ReportDao reportDao;

    public ReportRepository(final Application application) {
        final VehicleReportDatabase vehicleReportDatabase
                = VehicleReportDatabase.getInstance(application);
        reportDao = vehicleReportDatabase.reportsDao();
    }

    public Observable<List<Report>> getReports(String categoryId) {
        return reportDao.getReports(categoryId);
    }

    public Single<Report> getReport(long id) {
        return reportDao.getReport(id);
    }

    public Completable insertReport(final Report report) {
        return reportDao.insertReport(report);
    }

    public Completable updateReport(Report report) {
        return reportDao.updateReport(report);
    }

    public Completable deleteReport(long reportId) {
        return reportDao.deleteReport(reportId);
    }
}
