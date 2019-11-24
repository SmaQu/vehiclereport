package com.alastor.vehiclereport;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alastor.vehiclereport.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final boolean mainFragmentExisted = FragmentAdministrator
                .isFragmentPresent(getSupportFragmentManager(),
                        R.id.fragment_container,
                        MainFragment.class.getSimpleName());

        if (savedInstanceState == null) {
            FragmentAdministrator.
                    addFragment(getSupportFragmentManager(),
                            R.id.fragment_container,
                            new MainFragment());
        }
    }
}
