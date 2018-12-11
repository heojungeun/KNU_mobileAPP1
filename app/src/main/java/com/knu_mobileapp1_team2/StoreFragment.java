package com.knu_mobileapp1_team2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StoreFragment extends Fragment {
    SharedPreferences sp;

    int totalPoints;
    int usedPoints;

    TextView tvwStorePoints;

    FrameLayout flStoreButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);

        sp = getContext().getSharedPreferences("com.knu_mobileapp1_team2.pref", Activity.MODE_PRIVATE);

        totalPoints = sp.getInt("saved_points", 0);
        usedPoints = sp.getInt("used_points", 0);

        tvwStorePoints = view.findViewById(R.id.tvwStorePoints);

        flStoreButton = view.findViewById(R.id.flStoreButton);



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        tvwStorePoints.setText(String.format(getString(R.string.store_points), totalPoints - usedPoints));
    }
}
