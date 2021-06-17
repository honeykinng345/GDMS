package com.GDMS.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.GDMS.R;
import com.GDMS.Utilities.Helper;
import com.GDMS.Utilities.Utility;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        prefs = getSharedPreferences("login", MODE_PRIVATE);
        Helper.is_login = prefs.getInt("is_login", 2);

        if (Helper.is_login!=1) {
            iniit();
        }else {
            Utility.launchActivity(SplashActivity.this, MainActivity.class,true);

        }
    }

    private void iniit() {
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Utility.launchActivity(SplashActivity.this,LoginActivity.class,true);

            }
        }, 4000);

    }

}
