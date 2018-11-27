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

import java.util.Random;

public class HomeFragment extends Fragment {
    SharedPreferences sp;
    TextView tvwMainSpeech;

    String[] treeQuotes;
    Random r;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // for fragment, use this function instead of onCreate
        // use view.findViewById to get views

        sp = getContext().getSharedPreferences("com.knu_mobileapp1_team2.pref", Activity.MODE_PRIVATE);
        tvwMainSpeech = view.findViewById(R.id.tvwMainSpeech);

        treeQuotes = getResources().getStringArray(R.array.tree_quotes);
        r = new Random();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        tvwMainSpeech.setText(treeQuotes[r.nextInt(treeQuotes.length)]);
    }
}
