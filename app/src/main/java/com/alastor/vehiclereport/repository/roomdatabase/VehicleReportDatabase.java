package com.alastor.vehiclereport.repository.roomdatabase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.alastor.vehiclereport.repository.roomdatabase.entity.Category;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Reports;

import java.util.concurrent.Executors;

@Database(entities = {Category.class, Reports.class}, version = 1, exportSchema = false)
public abstract class VehicleReportDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract ReportsDao reportsDao();

    private static VehicleReportDatabase INSTANCE;

    public static VehicleReportDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }

        return INSTANCE;
    }

    private static VehicleReportDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                VehicleReportDatabase.class,
                "vehicle_report_database.db")
                .addCallback(getPopulateOnCreateCallback(context))
                .build();
    }

    private static Callback getPopulateOnCreateCallback(Context context) {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Executors.newSingleThreadScheduledExecutor().execute(() ->
                        getInstance(context)
                                .categoryDao()
                                .insertAll(Category.populateData()));
            }
        };
    }
}