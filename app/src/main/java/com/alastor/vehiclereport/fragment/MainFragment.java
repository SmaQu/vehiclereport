package com.alastor.vehiclereport.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.alastor.vehiclereport.FragmentAdministrator;
import com.alastor.vehiclereport.R;
import com.alastor.vehiclereport.adapter.CategoryAdapter;
import com.alastor.vehiclereport.repository.Response;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Category;
import com.alastor.vehiclereport.viewmodel.MainFragmentViewModel;

import java.util.List;

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();

    //fields
    private MainFragmentViewModel mMainFragmentViewModel;
    private CategoryAdapter mCategoryAdapter;

    //ui
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryAdapter = new CategoryAdapter(getCategoryListener());
        mMainFragmentViewModel = new ViewModelProvider(this).get(MainFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_category);
        mRecyclerView.setAdapter(mCategoryAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMainFragmentViewModel
                .getCategories()
                .observe(getViewLifecycleOwner(), getCategoryObserver());
    }

    private Observer<Response<List<Category>>> getCategoryObserver() {
        return listResponse -> {
            switch (listResponse.status) {
                case LOADING:

                    break;

                case SUCCESS:
                    if (listResponse.data != null) {
                        mCategoryAdapter.setCategories(listResponse.data);
                    }
                    break;

                case ERROR:

                    break;
            }
        };
    }

    private CategoryAdapter.OnCategoryListener getCategoryListener() {
        return categoryId -> {
            FragmentAdministrator.replaceFragment(getParentFragmentManager(),
                    R.id.fragment_container,
                    ReportsFragment.create(categoryId),
                    true);
        };
    }
}
