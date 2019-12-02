package com.alastor.vehiclereport.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alastor.vehiclereport.DataUtils;
import com.alastor.vehiclereport.FragmentAdministrator;
import com.alastor.vehiclereport.R;
import com.alastor.vehiclereport.adapter.AutoCompleteCategoryAdapter;
import com.alastor.vehiclereport.repository.Response;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Category;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Report;
import com.alastor.vehiclereport.view.CategoryAutoCompleteTextView;
import com.alastor.vehiclereport.viewmodel.BottomBar;
import com.alastor.vehiclereport.viewmodel.ReportViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class ReportFragment extends Fragment {

    private static final String TAG = ReportFragment.class.getSimpleName();
    private static final String ARG_REPORT_ID = "argReportId";

    private ReportViewModel mReportViewModel;
    private AutoCompleteCategoryAdapter adapter;

    //ui
    private CategoryAutoCompleteTextView categoryCactv;
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
            if (validReport()) {
                mReportViewModel
                        .insertOrUpdateReport()
                        .observe(getViewLifecycleOwner(), getReportOperationsObserver());
            }
            hideSoftKeyboard(view);
        });

        titleTiet.addTextChangedListener(getTitleTextWatcher());
        descriptionTiet.addTextChangedListener(getDescriptionTextWatcher());
        costTiet.addTextChangedListener(getCoastTextWatcher());

        categoryCactv.setOnItemClickListener((parent, view1, position, id) -> {
            mReportViewModel.getCurrentReport().setCategoryId(categoryCactv.getCategoryId().name());
        });

        final Bundle bundle = getArguments();
        if (bundle != null) {
            long reportId = bundle.getLong(ARG_REPORT_ID, -1);
            if (reportId > 0) {
                setHasOptionsMenu(true);
                mReportViewModel
                        .getReport(reportId)
                        .observe(getViewLifecycleOwner(), getReportObserver());
            }
        }

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
        final Context context = getContext();
        if (context instanceof BottomBar) {
            ((BottomBar) context).showFloatingButton();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_bottomappbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.app_bar_remove) {
            final long reportId = mReportViewModel.getCurrentReport().getId();
            mReportViewModel
                    .deleteReport(reportId)
                    .observe(getViewLifecycleOwner(), getReportOperationsObserver());
        }
        return super.onOptionsItemSelected(item);
    }

    private void bindView(final View view) {
        categoryCactv = view.findViewById(R.id.category_dropdown);
        titleTiet = view.findViewById(R.id.tiet_title);
        descriptionTiet = view.findViewById(R.id.tiet_description);
        dateTiet = view.findViewById(R.id.tiet_date);
        costTiet = view.findViewById(R.id.tiet_cost);
        saveMbt = view.findViewById(R.id.save_button);
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

    private boolean validReport() {
        boolean valid = true;
        if (TextUtils.isEmpty(mReportViewModel.getCurrentReport().getCategoryId())) {
            categoryCactv.setError("Error");
            valid = false;
        }

        if (TextUtils.isEmpty(mReportViewModel.getCurrentReport().getTitle())) {
            titleTiet.setError("Error");
            valid = false;
        }

        if (TextUtils.isEmpty(mReportViewModel.getCurrentReport().getDescription())) {
            descriptionTiet.setError("Error2");
            valid = false;
        }
        return valid;
    }

    private void setUpCategories() {
        final Category.CategoryId[] CATEGORIES = Category.CategoryId.values();
        adapter = new AutoCompleteCategoryAdapter(
                requireContext(),
                R.layout.item_dropdown_category,
                CATEGORIES);

        categoryCactv.setAdapter(adapter);
        final String categoryId = mReportViewModel.getCurrentReport().getCategoryId();
        if (!TextUtils.isEmpty(categoryId)) {
            categoryCactv.setCategoryId(Category.CategoryId.valueOf(categoryId));
        }
    }

    private void setUpDate() {
        final String displayDate = DataUtils.getData(
                mReportViewModel
                        .getCurrentReport()
                        .getExecutionTimestamp());
        dateTiet.setText(displayDate);
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
                    renderErrorState(reportResponse.error);
                    break;
            }
        };
    }

    private Observer<Response<Boolean>> getReportOperationsObserver() {
        return reportResponse -> {
            switch (reportResponse.status) {
                case LOADING:
                    renderLoadingState();
                    break;

                case SUCCESS:
                    FragmentAdministrator.popBackStack(getParentFragmentManager());
                    break;

                case ERROR:
                    renderErrorState(reportResponse.error);
                    break;
            }
        };
    }

    private TextWatcher getTitleTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mReportViewModel.getCurrentReport().setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    private TextWatcher getDescriptionTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mReportViewModel.getCurrentReport().setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    private TextWatcher getCoastTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if (text.endsWith(".") || text.endsWith(",")) {
                    text = text.replaceAll("[$,.]", "");
                }

                if (TextUtils.isEmpty(text)) {
                    text = "0";
                }

                double cost = Double.valueOf(text);
                mReportViewModel.getCurrentReport().setCost(cost);
            }

            @Override
            public void afterTextChanged(Editable s) {

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
            setUpDate();
        };
    }

    private long getTimeInMilliseconds(int year, int month, int day) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTimeInMillis();
    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}