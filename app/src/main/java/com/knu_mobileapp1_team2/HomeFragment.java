package com.knu_mobileapp1_team2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class HomeFragment extends Fragment {
    SharedPreferences sp;
    TextView tvwMainSpeech;

    ImageView ivwMainTree1;
    ImageView ivwMainTree2;
    ImageView ivwMainTree3;
    ImageView ivwMainTree4;
    ImageView ivwMainTree5;
    ImageView ivwMainTree6;
    ImageView ivwMainTree7;

    int appSteps = 0;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // for fragment, use this function instead of onCreate
        // use view.findViewById to get views

        sp = getContext().getSharedPreferences("com.knu_mobileapp1_team2.pref", Activity.MODE_PRIVATE);
        tvwMainSpeech = view.findViewById(R.id.tvwMainSpeech);

        tvwMainSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
                ab.setTitle(R.string.main_change_quote);

                final EditText editText = new EditText(getContext());
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editText.setSingleLine(true);
                ab.setView(editText);

                ab.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sp.edit().putString("tree_speech", editText.getText().toString()).apply();
                        tvwMainSpeech.setText(editText.getText().toString());
                    }
                });

                ab.setNegativeButton(android.R.string.no, null);

                ab.show();
            }
        });

        ivwMainTree1 = view.findViewById(R.id.ivwMainTree1);
        ivwMainTree2 = view.findViewById(R.id.ivwMainTree2);
        ivwMainTree3 = view.findViewById(R.id.ivwMainTree3);
        ivwMainTree4 = view.findViewById(R.id.ivwMainTree4);
        ivwMainTree5 = view.findViewById(R.id.ivwMainTree5);
        ivwMainTree6 = view.findViewById(R.id.ivwMainTree6);
        ivwMainTree7 = view.findViewById(R.id.ivwMainTree7);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        tvwMainSpeech.setText(sp.getString("tree_speech", getString(R.string.main_default_quote)));

        long totalTime = sp.getLong("total_running_time", 0);
        long lastDeadTime = sp.getLong("last_dead_time", 0);
        long totalRunningTime = totalTime - lastDeadTime;
        long totalRunningHour = totalRunningTime / (1000 * 60 * 60);

        if (totalRunningHour < 5) {
            setTreeVisible(1);
        } else if (totalRunningHour < 20) {
            setTreeVisible(2);
        } else if (totalRunningHour < 50) {
            setTreeVisible(3);
        } else if (totalRunningHour < 100) {
            setTreeVisible(4);
        } else {
            setTreeVisible(5);
        }

        if (appSteps > 18) {
            killTree();
        } else if (appSteps >= 6) {
            dangerTree();
        }
    }

    public void setTreeVisible(int which) {
        ivwMainTree1.setVisibility((which == 1) ? View.VISIBLE : View.INVISIBLE);
        ivwMainTree2.setVisibility((which == 2) ? View.VISIBLE : View.INVISIBLE);
        ivwMainTree3.setVisibility((which == 3) ? View.VISIBLE : View.INVISIBLE);
        ivwMainTree4.setVisibility((which == 4) ? View.VISIBLE : View.INVISIBLE);
        ivwMainTree5.setVisibility((which == 5) ? View.VISIBLE : View.INVISIBLE);
        ivwMainTree6.setVisibility((which == 6) ? View.VISIBLE : View.INVISIBLE);
        ivwMainTree7.setVisibility((which == 7) ? View.VISIBLE : View.INVISIBLE);
    }

    public void dangerTree() {
        tvwMainSpeech.setText(getString(R.string.main_hurt_quote));
        setTreeVisible(6);
    }

    public void killTree() {
        tvwMainSpeech.setText(getString(R.string.main_dead_quote));
        setTreeVisible(7);
    }
}
