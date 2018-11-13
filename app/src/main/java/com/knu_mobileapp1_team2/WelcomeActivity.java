package com.knu_mobileapp1_team2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class WelcomeActivity extends AppCompatActivity {
    SharedPreferences sp;
    Button btnWelcomeOk;
    ImageView ivwWelcomeSeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        sp = getSharedPreferences("com.knu_mobileapp1_team2.pref", Activity.MODE_PRIVATE);
        if (sp.getBoolean("app_enabled", false)) {
            loadMainActivity();
            return;
        }

        ivwWelcomeSeed = (ImageView)findViewById(R.id.ivwWelcomeSeed);
        ivwWelcomeSeed.startAnimation(AnimationUtils.loadAnimation(this, R.anim.tilt));

        btnWelcomeOk = (Button)findViewById(R.id.btnWelcomeOK);
        btnWelcomeOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().putBoolean("app_enabled", true).apply();
                loadMainActivity();
            }
        });
    }

    private void loadMainActivity() {
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }
}
