package com.alastor.vehiclereport.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.alastor.vehiclereport.FragmentAdministrator;
import com.alastor.vehiclereport.R;
import com.alastor.vehiclereport.adapter.ReportsAdapter;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Category;
import com.alastor.vehiclereport.viewmodel.BottomBar;
import com.alastor.vehiclereport.viewmodel.ReportsViewModel;

public class ReportsFragment extends Fragment {

    private static final String TAG = ReportsFragment.class.getSimpleName();
    private static final String ARG_CATEGORY_ID = "argCategoryId";

    //fields
    private ReportsViewModel mReportsViewModel;
    private ReportsAdapter mReportsAdapter;

    //ui
    private RecyclerView mRecyclerView;
    private TextView mCategoryTitleTv;

    public static ReportsFragment create(final String categoryId) {
        final ReportsFragment reportsFragment = new ReportsFragment();
        final Bundle bundle = new Bundle();
        bundle.putString(ARG_CATEGORY_ID, categoryId);
        reportsFragment.setArguments(bundle);
        return reportsFragment;
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
        mCategoryTitleTv = view.findViewById(R.id.text_category);
        mRecyclerView = view.findViewById(R.id.recycler_reports);
        mRecyclerView.setAdapter(mReportsAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Bundle bundle = getArguments();
        if (bundle == null || TextUtils.isEmpty(bundle.getString(ARG_CATEGORY_ID))) {
            throw new IllegalArgumentException("Arguments can not be null");
        }

        String categoryId = bundle.getString(ARG_CATEGORY_ID);
        mCategoryTitleTv.setText(Category.CategoryId.valueOf(categoryId).getTranslation(requireContext()));
        mReportsViewModel
                .getReports(categoryId)
                .observe(getViewLifecycleOwner(), listResponse -> {
                    switch (listResponse.status) {
                        case LOADING:
                            break;
                        case SUCCESS:
                            mReportsAdapter.setReports(listResponse.data);
                            break;
                        case ERROR:
                            Toast.makeText(requireContext(), R.string.error_reports_unreachable, Toast.LENGTH_SHORT).show();
                            break;
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        final Context context = getContext();
        if (context instanceof BottomBar) {
            ((BottomBar) context).showBottomAppBar();
        }
    }

    private ReportsAdapter.OnReportListener getReportListener() {
        return reportId -> {
            FragmentAdministrator.replaceFragment(getParentFragmentManager(),
                    R.id.fragment_container,
                    ReportFragment.create(reportId),
                    true);
        };
    }
}
