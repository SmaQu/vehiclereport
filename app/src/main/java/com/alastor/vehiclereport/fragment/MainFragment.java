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
import com.alastor.vehiclereport.repository.roomdatabase.entity.Report;
import com.alastor.vehiclereport.viewmodel.ReportsViewModel;

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

//        MainFragmentViewModel mainFragmentViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);
//        mainFragmentViewModel
//                .getCategories()
//                .observe(getViewLifecycleOwner(), listResponse -> {
//                    switch (listResponse.status) {
//                        case LOADING:
//
//                            break;
//
//                        case SUCCESS:
//                            if (listResponse.data != null) {
//                                for (Category category : listResponse.data) {
//                                    Log.e(TAG, "onCreateView: " + category.getId());
//                                }
//                            }
//                            break;
//
//                        case ERROR:
//
//                            break;
//                    }
//                });

        ReportsViewModel reportsViewModel = new ViewModelProvider(this).get(ReportsViewModel.class);

        view.findViewById(R.id.checkDatabase).setOnClickListener(v -> {

            Report report = new Report();
            report.setCategoryId("BFC");
            report.setCost(59.59f);
            report.setTitle("First");
            report.setDescription("Second text");
            report.setCreateTimestamp(System.currentTimeMillis());
            report.setEditTimestamp(System.currentTimeMillis());

            reportsViewModel.insertReport(report).observe(getViewLifecycleOwner(), booleanResponse -> {
                switch (booleanResponse.status) {
                    case LOADING:
                        Log.e(TAG, "onCreateView: Loading");
                        break;

                    case SUCCESS:
                        if (booleanResponse.data != null) {
                            Log.e(TAG, "onCreateView not null response: " + booleanResponse.data);
                        }
                        Log.e(TAG, "onCreateView: Success");

                        break;

                    case ERROR:
                        Log.e(TAG, "onCreateView: Error" + booleanResponse.error);

                        break;
                }
            });
        });

        reportsViewModel
                .getReports("BFC")
                .observe(getViewLifecycleOwner(), listResponse -> {
                    switch (listResponse.status) {
                        case LOADING:

                            break;

                        case SUCCESS:
                            if (listResponse.data != null) {
                                for (Report report : listResponse.data) {
                                    Log.e(TAG, "onCreateView getId: " + report.getId());
                                    Log.e(TAG, "onCreateView getCategoryId(): " + report.getCategoryId());
                                    Log.e(TAG, "onCreateView getTitle: " + report.getTitle());
                                    Log.e(TAG, "onCreateView getDescription: " + report.getDescription());
                                    Log.e(TAG, "onCreateView getCost: " + report.getCost());
                                    Log.e(TAG, "onCreateView getCreateTimestamp: " + report.getCreateTimestamp());
                                    Log.e(TAG, "onCreateView getEditTimestamp: " + report.getEditTimestamp());
                                }
                            }
                            break;

                        case ERROR:

                            break;
                    }
                });
        return view;
    }
}
