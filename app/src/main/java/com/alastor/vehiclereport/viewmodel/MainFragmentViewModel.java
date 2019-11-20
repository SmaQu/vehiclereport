package com.alastor.vehiclereport.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alastor.vehiclereport.repository.CategoryRepository;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Category;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainFragmentViewModel extends AndroidViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();
    private CategoryRepository categoryRepository;

    //liveadata
    private MutableLiveData<Response<List<Category>>> categories = new MutableLiveData<>();

    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
    }

    public LiveData<Response<List<Category>>> getCategories() {
        final Observable<List<Category>> observable = categoryRepository.getCategory();

        observable
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Category>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                        categories.postValue(Response.loading());
                    }

                    @Override
                    public void onNext(List<Category> newCategories) {
                        categories.postValue(Response.success(newCategories));
                    }

                    @Override
                    public void onError(Throwable e) {
                        categories.postValue(Response.error(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return categories;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}