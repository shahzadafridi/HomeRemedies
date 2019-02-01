package com.example.opriday.homeremedies;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.opriday.homeremedies.Remedies.HomeRemedie;
import com.example.opriday.homeremedies.Remedies.PendingRemedies;
import com.example.opriday.homeremedies.Remedies.YogaChannels;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView iv1,iv2,iv3,iv4,iv5,iv6;
    String getUserType = "user";
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntializeUI();
    }

    private void IntializeUI() {
        iv1 = (ImageView) findViewById(R.id.image1);
        iv2 = (ImageView) findViewById(R.id.image2);
        iv3 = (ImageView) findViewById(R.id.image3);
        iv4 = (ImageView) findViewById(R.id.image4);
        iv5 = (ImageView) findViewById(R.id.image5);
        iv6 = (ImageView) findViewById(R.id.image6);
        title = (TextView) findViewById(R.id.title);
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
        iv5.setOnClickListener(this);
        iv6.setOnClickListener(this);

        if(!TextUtils.isEmpty(getIntent().getStringExtra("user_type"))) {
            getUserType = getIntent().getStringExtra("user_type");
            if (getUserType.contentEquals("admin")){
                iv6.setVisibility(View.VISIBLE);
            }else {
                iv6.setVisibility(View.GONE);
            }
        }
        title.setText(getUserType);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.image1){
            Intent homeRemedies = new Intent(MainActivity.this,HomeRemedie.class);
            homeRemedies.putExtra("user_type",getUserType.toLowerCase());
            startActivity(homeRemedies);
        }else if (v.getId() == R.id.image2){
            Intent yogaVideos = new Intent(MainActivity.this,YogaChannels.class);
            startActivity(yogaVideos);
        }else if (v.getId() == R.id.image3){
            Intent homeRemedies = new Intent(MainActivity.this,HomeRemedie.class);
            startActivity(homeRemedies);
        }else if (v.getId() == R.id.image4){
            Intent homeRemedies = new Intent(MainActivity.this,HomeRemedie.class);
            startActivity(homeRemedies);
        }else if (v.getId() == R.id.image5){
            Intent homeRemedies = new Intent(MainActivity.this,HomeRemedie.class);
            startActivity(homeRemedies);
        }else if (v.getId() == R.id.image6){
            Intent pendingRemedies = new Intent(MainActivity.this,PendingRemedies.class);
            startActivity(pendingRemedies);
        }
    }
}

