package com.alastor.vehiclereport.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.alastor.vehiclereport.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AboutFragment extends Fragment {

    private static final String TAG = AboutFragment.class.getSimpleName();

    public static AboutFragment create() {
        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_about, container, false);
        final LinearLayout linearLayout = view.findViewById(R.id.root_about_layout);

        for (Pair<Integer, String> icAndDes : iconsAndDescriptions()) {
            final View itemView = inflater.inflate(R.layout.item_about, null);
            final ImageView icon = itemView.findViewById(R.id.image_app_icon);
            final TextView description = itemView.findViewById(R.id.text_icon_description);

            icon.setImageResource(icAndDes.first);
            description.setText(icAndDes.second);
            linearLayout.addView(itemView);
        }

        return view;
    }

    private List<Pair<Integer, String>> iconsAndDescriptions() {
        return new ArrayList<>(Arrays.asList(
                new Pair<>(R.drawable.ic_car_door, "Icon made by Eucalyp from www.flaticon.com"),
                new Pair<>(R.drawable.ic_break, "Icon made by Smashicons from www.flaticon.com"),
                new Pair<>(R.drawable.ic_windshield, "Icon made by Smashicons from www.flaticon.com"),
                new Pair<>(R.drawable.ic_radiator, "Icon made by Smashicons from www.flaticon.com"),
                new Pair<>(R.drawable.ic_gears, "Icon made by Eucalyp from www.flaticon.com"),
                new Pair<>(R.drawable.ic_headlights, "Icon made by Eucalyp from www.flaticon.com"),
                new Pair<>(R.drawable.ic_engine, "Icon made by Smashicons from www.flaticon.com"),
                new Pair<>(R.drawable.ic_tools, "Icon made by Freepik from www.flaticon.com"),
                new Pair<>(R.drawable.ic_exhaust, "Icon made by Smashicons from www.flaticon.com"),
                new Pair<>(R.drawable.ic_air_filter, "Icon made by Eucalyp from www.flaticon.com"),
                new Pair<>(R.drawable.ic_gas_station, "Icon made by Smashicons from www.flaticon.com"),
                new Pair<>(R.drawable.ic_air_conditioner, "Icon made by Freepik from www.flaticon.com"),
                new Pair<>(R.drawable.ic_spark_plug, "Icon made by Smashicons from www.flaticon.com"),
                new Pair<>(R.drawable.ic_starter, "Icon made by Eucalyp from www.flaticon.com"),
                new Pair<>(R.drawable.ic_steering_wheel, "Icon made by Eucalyp from www.flaticon.com"),
                new Pair<>(R.drawable.ic_damper, "Icon made by Smashicons from www.flaticon.com")));
    }
}
