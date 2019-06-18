package com.example.opriday.homeremedies.Screens.Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Utility.ActivityManager;
import com.example.opriday.homeremedies.Utility.Constant;
import com.example.opriday.homeremedies.Utility.SharedPrefManager;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (!checkSession())
        ActivityManager.goToLoginActivity(SplashScreenActivity.this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (checkSession()){
            ActivityManager.goToMainActivity(SplashScreenActivity.this);
        }
    }

    private boolean checkSession(){
        SharedPreferences sharedPreferences = SharedPrefManager.getCustomSharedPreferences(SplashScreenActivity.this, Constant.LOGIN_SESSION, MODE_PRIVATE);
        boolean isUserLogin = sharedPreferences.getBoolean(Constant.IS_LOGIN,false);
        return isUserLogin;
    }
}
