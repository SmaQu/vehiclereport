package com.alastor.vehiclereport.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alastor.vehiclereport.R;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Category;
import com.alastor.vehiclereport.viewmodel.MainFragmentViewModel;

import io.reactivex.disposables.CompositeDisposable;

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();

    private CompositeDisposable disposable = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        MainFragmentViewModel mainFragmentViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);
        mainFragmentViewModel
                .getCategories()
                .observe(getViewLifecycleOwner(), listResponse -> {
                    switch (listResponse.status) {
                        case LOADING:

                            break;

                        case SUCCESS:
                            if (listResponse.data != null) {
                                for (Category category : listResponse.data) {
                                    Log.e(TAG, "onCreateView: " + category.getId());
                                }
                            }
                            break;

                        case ERROR:

                            break;
                    }
                });
        return view;
    }


    @Override
    public void onStop() {
        super.onStop();
        disposable.clear();
    }
}
