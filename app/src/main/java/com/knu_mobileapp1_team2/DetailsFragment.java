package com.knu_mobileapp1_team2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailsFragment extends Fragment {
    SharedPreferences sp;

    TextView tvwDetailsTreePoints;
    TextView tvwDetailsTotalSteps;
    TextView tvwDetailsExtraPoints;
    TextView tvwDetailsTotalRunningTime;
    TextView tvwDetailsLongestStep;
    TextView tvwDetailsLongestStepDate;
    TextView tvwDetailsKilledTrees;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        // for fragment, use this function instead of onCreate
        // use view.findViewById to get views

        tvwDetailsTreePoints = view.findViewById(R.id.tvwDetailsTreePoints);
        tvwDetailsTotalSteps = view.findViewById(R.id.tvwDetailsTotalSteps);
        tvwDetailsExtraPoints = view.findViewById(R.id.tvwDetailsExtraPoints);
        tvwDetailsTotalRunningTime = view.findViewById(R.id.tvwDetailsTotalRunningTime);
        tvwDetailsLongestStep = view.findViewById(R.id.tvwDetailsLongestStep);
        tvwDetailsLongestStepDate = view.findViewById(R.id.tvwDetailsLongestStepDate);
        tvwDetailsKilledTrees = view.findViewById(R.id.tvwDetailsKilledTrees);

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

        long totalRunningTime = sp.getLong("total_running_time", 0);
        long second = (totalRunningTime / 1000) % 60;
        long minute = (totalRunningTime / (1000 * 60)) % 60;
        long hour = (totalRunningTime / (1000 * 60 * 60)) % 24;
        long day = (totalRunningTime / (1000 * 60 * 60 * 24));

        tvwDetailsTotalRunningTime.setText(String.format(getString(R.string.details_total_running_time_format), day, hour, minute, second));

        tvwDetailsLongestStep.setText(sp.getInt("longest_step", 0) + "");

        long longestStepDate = sp.getLong("longest_step_date", 0);
        if (longestStepDate == 0) {
            tvwDetailsLongestStepDate.setText(getString(R.string.details_no_record));
        } else {
            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
            tvwDetailsLongestStepDate.setText(df.format(new Date(longestStepDate)));
        }

        tvwDetailsKilledTrees.setText(sp.getInt("killed_trees", 0) + "");
    }
}
