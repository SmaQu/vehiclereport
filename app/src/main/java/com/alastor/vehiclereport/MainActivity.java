package com.alastor.vehiclereport;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.alastor.vehiclereport.fragment.MainFragment;
import com.alastor.vehiclereport.fragment.ReportFragment;
import com.alastor.vehiclereport.viewmodel.BottomBar;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements BottomBar {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomAppBar mBottomAppBar;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomAppBar = findViewById(R.id.bar_bottom);
        mFloatingActionButton = findViewById(R.id.fab_add);
        mFloatingActionButton.setOnClickListener(getOnFABListener());

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

    private View.OnClickListener getOnFABListener() {
        return v -> {
            FragmentAdministrator.replaceFragment(getSupportFragmentManager(),
                    R.id.fragment_container,
                    ReportFragment.create(),
                    false);
        };
    }

    @Override
    public void hideBar() {
        mBottomAppBar.performHide();
        mFloatingActionButton.hide();
    }

    @Override
    public void showBar() {
        mBottomAppBar.performShow();
        mFloatingActionButton.show();
    }
}