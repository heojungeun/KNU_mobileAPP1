package com.knu_mobileapp1_team2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class SettingsFragment extends Fragment {
    SharedPreferences sp;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // for fragment, use this function instead of onCreate
        // use view.findViewById to get views

        sp = getContext().getSharedPreferences("com.knu_mobileapp1_team2.pref", Activity.MODE_PRIVATE);

        Button btnSettingsReset = view.findViewById(R.id.btnSettingsReset);
        btnSettingsReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(SettingsFragment.this.getContext());
                b.setMessage(getString(R.string.settings_reset_confirm))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sp.edit().clear().apply();
                                Intent si = new Intent(getContext(), StepCounterService.class);
                                getContext().stopService(si);
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null);
                b.show();
            }
        });

        return view;
    }
}
