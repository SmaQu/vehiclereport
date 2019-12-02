package com.alastor.vehiclereport;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.alastor.vehiclereport.fragment.MainFragment;
import com.alastor.vehiclereport.fragment.ReportFragment;
import com.alastor.vehiclereport.viewmodel.BottomBar;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements BottomBar, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private BottomAppBar mBottomAppBar;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomAppBar = findViewById(R.id.bar_bottom);
        setSupportActionBar(mBottomAppBar);
        mDrawerLayout = findViewById(R.id.drawer_root);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.label_open, R.string.label_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.navigation_drawer);
        mNavigationView.setNavigationItemSelectedListener(this);

        mBottomAppBar.setNavigationOnClickListener(v ->
                mDrawerLayout.openDrawer(GravityCompat.START));

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

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.e(TAG, "onNavigationItemSelected: ");
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
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
    public void hideFloatingButton() {
        mFloatingActionButton.hide();
    }

    @Override
    public void showFloatingButton() {
        mFloatingActionButton.show();
    }

    @Override
    public void showBottomAppBar() {
        mBottomAppBar.performShow();
    }
}