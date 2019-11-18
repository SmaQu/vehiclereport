package com.alastor.vehiclereport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alastor.vehiclereport.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
