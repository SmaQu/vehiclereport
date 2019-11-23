package com.alastor.vehiclereport.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.alastor.vehiclereport.FragmentAdministrator;
import com.alastor.vehiclereport.R;
import com.alastor.vehiclereport.adapter.ReportsAdapter;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Report;
import com.alastor.vehiclereport.viewmodel.ReportViewModel;
import com.alastor.vehiclereport.viewmodel.ReportsViewModel;

public class ReportsFragment extends Fragment {

    private static final String TAG = ReportsFragment.class.getSimpleName();

    //fields
    private ReportsViewModel mReportsViewModel;
    private ReportsAdapter mReportsAdapter;

    //ui
    private RecyclerView mRecyclerView;

    public static ReportsFragment create(final String categoryId) {
        return new ReportsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReportsAdapter = new ReportsAdapter(getReportListener());
        mReportsViewModel = new ViewModelProvider(this).get(ReportsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_reports, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_reports);
        mRecyclerView.setAdapter(mReportsAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mReportsViewModel
                .getReports("BFC")
                .observe(getViewLifecycleOwner(), listResponse -> {
                    switch (listResponse.status) {
                        case LOADING:

                            break;

                        case SUCCESS:
                            if (listResponse.data != null) {
                                mReportsAdapter.setReports(listResponse.data);
                            }
                            break;

                        case ERROR:

                            break;
                    }
                });
    }

    private ReportsAdapter.OnReportListener getReportListener() {
        return reportId -> {
            FragmentAdministrator.replaceFragment(getParentFragmentManager()
                    , R.id.fragment_container,
                    ReportFragment.create(reportId),
                    true);
        };
    }
}
