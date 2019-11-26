package com.alastor.vehiclereport.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alastor.vehiclereport.repository.ReportRepository;
import com.alastor.vehiclereport.repository.Response;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Report;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ReportsViewModel extends AndroidViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();
    private ReportRepository reportRepository;

    //live-data
    private MutableLiveData<Response<List<Report>>> reports = new MutableLiveData<>();
    private MutableLiveData<Response<Boolean>> insertReport = new MutableLiveData<>();

    public ReportsViewModel(@NonNull Application application) {
        super(application);
        reportRepository = new ReportRepository(application);
    }

    public LiveData<Response<List<Report>>> getReports(String categoryId) {
        //TODO remember this is trigger twice when live-data is available.
        // Coz this return old-value and new after repository return
        final Observable<List<Report>> observable = reportRepository.getReports(categoryId);

        observable
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Report>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                        reports.postValue(Response.loading());
                    }

                    @Override
                    public void onNext(List<Report> newReports) {
                        reports.postValue(Response.success(newReports));
                    }

                    @Override
                    public void onError(Throwable e) {
                        reports.postValue(Response.error(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return reports;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
