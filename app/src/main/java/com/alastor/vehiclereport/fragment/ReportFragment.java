package com.alastor.vehiclereport.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alastor.vehiclereport.MainActivity;
import com.alastor.vehiclereport.R;
import com.alastor.vehiclereport.viewmodel.BottomBar;
import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.Calendar;

public class ReportFragment extends Fragment {

    private static final String TAG = ReportFragment.class.getSimpleName();

    public static ReportFragment create(long reportId) {
        return new ReportFragment();
    }

    public static ReportFragment create() {
        return new ReportFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BottomBar) {
            ((BottomBar) context).hideBar();
        }
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_report, container, false);

        String[] COUNTRIES = new String[]{"Item 1", "Item 2", "Item 3", "Item 4"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        requireContext(),
                        R.layout.item_dropdown_category,
                        COUNTRIES);

        AutoCompleteTextView editTextFilledExposedDropdown =
                view.findViewById(R.id.category_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter);

        view.findViewById(R.id.tiet_date).setOnClickListener(v -> {
            int mYear;
            int mMonth;
            int mDay;

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    (view1, year, month, dayOfMonth) -> {
                        Log.e(TAG, "onDateSet: " + year + " , " + month + " , " + dayOfMonth);
                    }, mYear, mMonth, mDay);

            datePickerDialog.show();

        });

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        if (getContext() != null && getContext() instanceof BottomBar) {
            ((BottomBar) getContext()).showBar();
        }
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

    }
}