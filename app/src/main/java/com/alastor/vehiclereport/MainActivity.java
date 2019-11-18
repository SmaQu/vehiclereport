package com.alastor.vehiclereport;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.alastor.vehiclereport.fragment.MainFragment;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Category;
import com.alastor.vehiclereport.viewmodel.MainFragmentViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragmentViewModel mainFragmentViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);
        mainFragmentViewModel.getCategory().observe(this, categories -> {
            if (categories != null) {
                for (Category category : categories) {
                    Log.e(TAG, "onCreate: " + category.getId());
                }
            }
        });

        final boolean mainFragmentExisted = FragmentAdministrator
                .isFragmentVisible(getSupportFragmentManager(),
                        MainFragment.class.getSimpleName());

        if (!mainFragmentExisted) {
            FragmentAdministrator.
                    addFragment(getSupportFragmentManager(),
                            R.id.fragment_container,
                            new MainFragment());
        }
    }
}
