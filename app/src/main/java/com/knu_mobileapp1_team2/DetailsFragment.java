package com.knu_mobileapp1_team2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

public class DetailsFragment extends Fragment {
    SharedPreferences sp;

    TextView tvwDetailsTreePoints;
    TextView tvwDetailsTotalSteps;
    TextView tvwDetailsExtraPoints;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        // for fragment, use this function instead of onCreate
        // use view.findViewById to get views

        tvwDetailsTreePoints = view.findViewById(R.id.tvwDetailsTreePoints);
        tvwDetailsTotalSteps = view.findViewById(R.id.tvwDetailsTotalSteps);
        tvwDetailsExtraPoints = view.findViewById(R.id.tvwDetailsExtraPoints);

        sp = getContext().getSharedPreferences("com.knu_mobileapp1_team2.pref", Activity.MODE_PRIVATE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        int savedSteps = sp.getInt("saved_steps", 0);

        tvwDetailsTotalSteps.setText(String.format(Locale.getDefault(), "%d", savedSteps));
        tvwDetailsExtraPoints.setText("0");
        tvwDetailsTreePoints.setText(String.format(Locale.getDefault(), "%d", savedSteps));
    }
}
