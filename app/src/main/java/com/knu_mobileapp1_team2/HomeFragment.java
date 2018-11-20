package com.knu_mobileapp1_team2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {
    View view;

    SharedPreferences sp;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        // for fragment, use this function instead of onCreate
        // use view.findViewById to get views

        sp = getContext().getSharedPreferences("com.knu_mobileapp1_team2.pref", Activity.MODE_PRIVATE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        int savedSteps = sp.getInt("saved_steps", 0);

        TextView tvwMainSpeech = view.findViewById(R.id.tvwMainSpeech);
        tvwMainSpeech.setText(String.format(getString(R.string.main_score), savedSteps));
    }
}
