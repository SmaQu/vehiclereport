package com.alastor.vehiclereport.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alastor.vehiclereport.R;
import com.alastor.vehiclereport.adapter.AutoCompleteCategoryAdapter;
import com.alastor.vehiclereport.repository.Response;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Category;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Report;
import com.alastor.vehiclereport.viewmodel.BottomBar;
import com.alastor.vehiclereport.viewmodel.ReportViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReportFragment extends Fragment {

    private static final String TAG = ReportFragment.class.getSimpleName();
    private static final String ARG_REPORT_ID = "argReportId";

    private ReportViewModel mReportViewModel;

    //ui
    private AutoCompleteTextView categoryActv;
    private TextInputEditText titleTiet;
    private TextInputEditText descriptionTiet;
    private TextInputEditText dateTiet;
    private TextInputEditText costTiet;
    private MaterialButton saveMbt;
    private ScrollView allViewsScroll;
    private ProgressBar loadingPb;

    public static ReportFragment create(long reportId) {
        final ReportFragment reportFragment = create();
        final Bundle bundle = new Bundle();
        bundle.putLong(ARG_REPORT_ID, reportId);
        reportFragment.setArguments(bundle);
        return reportFragment;
    }

    public static ReportFragment create() {
        return new ReportFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);

        if (savedInstanceState != null) {
            long reportId = savedInstanceState.getLong(ARG_REPORT_ID, -1);
            if (reportId > 0) {
                mReportViewModel
                        .getReport(reportId)
                        .observe(getViewLifecycleOwner(), getReportObserver());
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_report, container, false);
        bindView(view);
        renderDataState();

        dateTiet.setOnClickListener(v -> {
            openDataPicker();
        });

        saveMbt.setOnClickListener(v -> {
            if (validViews()) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getContext() instanceof BottomBar) {
            ((BottomBar) getContext()).hideFloatingButton();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getContext() != null && getContext() instanceof BottomBar) {
            ((BottomBar) getContext()).showFloatingButton();
        }
    }

    private void bindView(final View view) {
        categoryActv = view.findViewById(R.id.category_dropdown);
        titleTiet = view.findViewById(R.id.tiet_title);
        descriptionTiet = view.findViewById(R.id.tiet_description);
        dateTiet = view.findViewById(R.id.tiet_date);
        costTiet = view.findViewById(R.id.tiet_cost);
        loadingPb = view.findViewById(R.id.progress_loading);
        allViewsScroll = view.findViewById(R.id.scroll_views);
    }

    private void renderLoadingState() {
        allViewsScroll.setVisibility(View.GONE);
        loadingPb.setVisibility(View.VISIBLE);
    }

    private void renderDataState() {
        final Report report = mReportViewModel.getCurrentReport();
        loadingPb.setVisibility(View.GONE);
        allViewsScroll.setVisibility(View.VISIBLE);

        setUpCategories();
        titleTiet.setText(report.getTitle());
        descriptionTiet.setText(report.getDescription());
        setUpDate();
        costTiet.setText(String.valueOf(report.getCost()));
    }

    private void renderErrorState(Throwable throwable) {

    }

    private boolean validViews() {
        if (TextUtils.isEmpty(categoryActv.getText())) {
            categoryActv.setError("Error");
            return false;
        }

        if (TextUtils.isEmpty(titleTiet.getText())) {
            titleTiet.setError("Error");
            return false;
        }

        if (TextUtils.isEmpty(descriptionTiet.getText())) {
            descriptionTiet.setError("Error2");
            return false;
        }

        if (TextUtils.isEmpty(dateTiet.getText())) {
            dateTiet.setError("Error3");
            return false;
        }
        return true;
    }

    private void setUpCategories() {
        final Category.CategoryId[] CATEGORIES = Category.CategoryId.values();
        AutoCompleteCategoryAdapter adapter =
                new AutoCompleteCategoryAdapter(
                        requireContext(),
                        R.layout.item_dropdown_category,
                        CATEGORIES);

        categoryActv.setAdapter(adapter);
        //TODO Fix it later
//        categoryActv.setOnItemClickListener((parent, view, position, id) -> {
//            final Category.CategoryId categoryId =
//                    (Category.CategoryId) parent.getItemAtPosition(position);
//            categoryActv.setText(categoryId.getTranslation(getContext()));
//        });
    }

    private void setUpDate() {
        final SimpleDateFormat formatter =
                new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mReportViewModel.getCurrentReport().getExecutionTimestamp());
        dateTiet.setText(formatter.format(calendar.getTime()));
    }

    private Observer<Response<Report>> getReportObserver() {
        return reportResponse -> {
            switch (reportResponse.status) {
                case LOADING:
                    renderLoadingState();
                    break;

                case SUCCESS:
                    renderDataState();
                    break;

                case ERROR:
                    renderErrorState();
                    break;
            }
        };
    }

    private void openDataPicker() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                getDateListener(),
                mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener getDateListener() {
        return (view1, year, month, dayOfMonth) -> {
            mReportViewModel
                    .getCurrentReport()
                    .setExecutionTimestamp(getTimeInMilliseconds(year, month, dayOfMonth));
        };
    }

    private long getTimeInMilliseconds(int year, int month, int day) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTimeInMillis();
    }
}