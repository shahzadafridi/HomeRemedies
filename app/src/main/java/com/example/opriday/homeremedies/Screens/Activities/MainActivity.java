package com.example.opriday.homeremedies.Screens.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Utility.ActivityManager;
import com.example.opriday.homeremedies.Utility.Constant;
import com.example.opriday.homeremedies.Utility.SharedPrefManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CardView remedie, yoga, health_tips, favorite_remedie, reviews, pending_remedie;
    String getUserType = "user";
    TextView logout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntializeUI();
    }


    private void IntializeUI() {
        remedie = (CardView) findViewById(R.id.remedie);
        yoga = (CardView) findViewById(R.id.Yoga);
        health_tips = (CardView) findViewById(R.id.health_tips);
        favorite_remedie = (CardView) findViewById(R.id.favorite_remedie);
//        reviews = (CardView) findViewById(R.id.reviews);
        pending_remedie = (CardView) findViewById(R.id.pending);
        remedie.setOnClickListener(this);
        yoga.setOnClickListener(this);
        health_tips.setOnClickListener(this);
        favorite_remedie.setOnClickListener(this);
//        reviews.setOnClickListener(this);
        pending_remedie.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.pbHeaderProgress);
        logout = (TextView) findViewById(R.id.logout);
        logout.setOnClickListener(this);

        if (Constant.isAdmin(MainActivity.this)) {
            pending_remedie.setVisibility(View.VISIBLE);
        } else {
            pending_remedie.setVisibility(View.GONE);
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.remedie) {
            Intent homeRemedies = new Intent(MainActivity.this, HomeRemedieActivity.class);
            homeRemedies.putExtra("user_type", getUserType.toLowerCase());
            startActivity(homeRemedies);
        } else if (v.getId() == R.id.Yoga) {
            Intent yogaVideos = new Intent(MainActivity.this, YogaChannelViedosActivity.class);
            startActivity(yogaVideos);
        } else if (v.getId() == R.id.favorite_remedie) {
            Intent favoriteActivity = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(favoriteActivity);
        } else if (v.getId() == R.id.pending) {
            Intent favoriteActivity = new Intent(MainActivity.this, PendingRemediesActivity.class);
            startActivity(favoriteActivity);
        }else if (v.getId() == R.id.health_tips){
            Intent favoriteActivity = new Intent(MainActivity.this, TipActivity.class);
            startActivity(favoriteActivity);
        }else if (v.getId() == R.id.logout){
            setLogout();
        }
    }

    public void setLogout(){
        progressBar.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                SharedPreferences.Editor editor = SharedPrefManager.getCustomSharedPreferencesEditor(MainActivity.this, Constant.LOGIN_SESSION, MODE_PRIVATE);
                editor.putBoolean(Constant.IS_LOGIN,false);
                editor.commit();
                ActivityManager.goToStartActivity(MainActivity.this);
            }
        },2000);
    }
}

