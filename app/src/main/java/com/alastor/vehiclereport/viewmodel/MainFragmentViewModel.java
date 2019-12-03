package com.alastor.vehiclereport.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alastor.vehiclereport.repository.CategoryIntent;
import com.alastor.vehiclereport.repository.CategoryRepository;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Category;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainFragmentViewModel extends AndroidViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();
    private CategoryRepository categoryRepository;

    //live-data
    private MutableLiveData<CategoryIntent<List<Category>, Category>> categories = new MutableLiveData<>();

    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
    }

    public LiveData<CategoryIntent<List<Category>, Category>> getCategories() {
        final Observable<List<Category>> observable = categoryRepository.getCategory();

        observable
                .subscribeOn(Schedulers.io())
                .flatMap(categories1 -> {
                    categories.postValue(CategoryIntent.onMainData(categories1));
                    return Observable.fromIterable(categories1)
                            .subscribeOn(Schedulers.io());
                }).flatMap(category -> categoryRepository.getCategoryItemsCount(category.getId())
                .map(integer -> {
                    category.setAmountOfElements(integer);
                    return category;
                }).subscribeOn(Schedulers.io())
                .flatMap(category1 -> categoryRepository.getCategoryItemExecutionTimestamp(category1.getId())
                        .map(aLong -> {
                            category1.setExecutionTimestamp(aLong);
                            return category1;
                        })
                        .subscribeOn(Schedulers.io())))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Category>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        categories.setValue(CategoryIntent.loading());
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(Category category) {
                        Log.e("Main", "onNext: " + category.getId() + "," + category.getAmountOfElements() + "," + category.getExecutionTimestamp() );
                        categories.setValue(CategoryIntent.onNext(category));
                    }

                    @Override
                    public void onError(Throwable e) {
                        categories.setValue(CategoryIntent.error(e));
                    }

                    @Override
                    public void onComplete() {
                        categories.setValue(CategoryIntent.complete());
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