package com.alastor.vehiclereport.repository.roomdatabase.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alastor.vehiclereport.repository.roomdatabase.entity.Report;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface ReportDao {

    @Query("SELECT * FROM report WHERE category_id LIKE :categoryId")
    Observable<List<Report>> getReports(String categoryId);

    @Insert
    Completable insertReport(final Report report);

    @Query("SELECT * FROM report WHERE id LIKE :id")
    Single<Report> getReport(long id);

    @Update
    Completable updateReport(Report report);

    @Delete
    Completable deleteReport(Report report);
}
