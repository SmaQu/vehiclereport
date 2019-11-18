package com.alastor.vehiclereport;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentAdministrator {
    private static final String TAG = FragmentAdministrator.class.getSimpleName();

    public static boolean isFragmentVisible(final FragmentManager fragmentManager,
                                            final String simpleClassName) {
        Fragment currentFragment = fragmentManager
                .findFragmentByTag(simpleClassName);
        if (currentFragment != null && currentFragment.getTag() != null) {
            return currentFragment.getTag().equals(simpleClassName);
        }
        return false;
    }

    public static void addFragment(final FragmentManager fragmentManager,
                                   final int fragmentContainer,
                                   final Fragment fragment) {
        fragmentManager.beginTransaction()
                .add(fragmentContainer, fragment, fragment.getClass().getSimpleName())
                .commit();
    }
}
