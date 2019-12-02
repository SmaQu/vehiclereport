package com.alastor.vehiclereport.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alastor.vehiclereport.repository.ReportRepository;
import com.alastor.vehiclereport.repository.Response;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Report;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ReportViewModel extends AndroidViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();
    private ReportRepository reportRepository;

    //fields
    private Report currentReport = new Report();
    private boolean isReportEdit = false;

    //live-data
    private MutableLiveData<Response<Report>> report = new MutableLiveData<>();
    private MutableLiveData<Response<Boolean>> insertReport = new MutableLiveData<>();
    private MutableLiveData<Response<Boolean>> updateReport = new MutableLiveData<>();
    private MutableLiveData<Response<Boolean>> deleteReport = new MutableLiveData<>();

    public ReportViewModel(@NonNull Application application) {
        super(application);
        reportRepository = new ReportRepository(application);
    }

    public LiveData<Response<Report>> getReport(long id) {
        final Single<Report> observable = reportRepository.getReport(id);

        observable
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleObserver<Report>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                        report.postValue(Response.loading());
                    }

                    @Override
                    public void onSuccess(Report newReport) {
                        currentReport = newReport;
                        isReportEdit = true;
                        report.postValue(Response.success(newReport));
                    }

                    @Override
                    public void onError(Throwable e) {
                        report.postValue(Response.error(e));
                    }
                });

        return report;
    }

    public LiveData<Response<Boolean>> insertOrUpdateReport() {
        final Completable insertCompletable = reportRepository.insertReport(currentReport);
        final Completable updateCompletable = reportRepository.updateReport(currentReport);

        final CompletableObserver completableObserver = new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable.add(d);
                updateReport.postValue(Response.loading());
            }

            @Override
            public void onComplete() {
                updateReport.postValue(Response.success(true));
            }

            @Override
            public void onError(Throwable e) {
                updateReport.postValue(Response.error(e));
            }
        };

        if (isReportEdit) {
            updateCompletable.subscribeOn(Schedulers.io())
                    .subscribe(completableObserver);
        } else {
            insertCompletable.subscribeOn(Schedulers.io())
                    .subscribe(completableObserver);
        }

        return updateReport;
    }

    public LiveData<Response<Boolean>> deleteReport(final long reportId) {
        final Completable completable = reportRepository.deleteReport(reportId);

        completable.subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                        deleteReport.postValue(Response.loading());
                    }

                    @Override
                    public void onComplete() {
                        deleteReport.postValue(Response.success(true));
                    }

                    @Override
                    public void onError(Throwable e) {
                        deleteReport.postValue(Response.error(e));
                    }
                });


        return deleteReport;
    }

    public Report getCurrentReport() {
        return currentReport;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
