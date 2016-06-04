package com.ashish.jiraissuetracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;

import com.ashish.jiraissuetracker.R;
import com.ashish.jiraissuetracker.application.AppApplication;
import com.ashish.jiraissuetracker.preferences.ZPreferences;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity_layout);

        if (ZPreferences.isUserLogIn(this)) {

        } else {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    switchToLoginActivity();
                }
            }, 1000);
        }
    }

    private void switchToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

}
