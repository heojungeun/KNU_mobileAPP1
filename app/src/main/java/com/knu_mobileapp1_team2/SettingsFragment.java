package com.knu_mobileapp1_team2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class SettingsFragment extends Fragment {
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // for fragment, use this function instead of onCreate
        // use view.findViewById to get views

        /*

        SharedPreferences sp = getActivity().getSharedPreferences("com.example.pprtd.pref", Activity.MODE_PRIVATE);
        sp.edit().putBoolean("user_skip_welcome", false).apply();

        Toast.makeText(getActivity(), "Welcome skip reset", Toast.LENGTH_SHORT).show();

        */

        return view;
    }
}
