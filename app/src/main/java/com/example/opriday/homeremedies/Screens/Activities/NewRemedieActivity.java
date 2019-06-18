package com.example.opriday.homeremedies.Screens.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opriday.homeremedies.Model.Respond;
import com.example.opriday.homeremedies.Network.IRetrofitRemedie;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Network.RetrofitConstant;
import com.example.opriday.homeremedies.Utility.Constant;
import com.example.opriday.homeremedies.Utility.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRemedieActivity extends AppCompatActivity implements View.OnClickListener {

    EditText title,category,description;
    Button post;
    IRetrofitRemedie remedieClient;
    String role="user",user_name,user_id;
    CoordinatorLayout coordinatorLayout;
    ProgressBar progressBar;
    String TAG = "NewRemedieActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_remedie);
        title = (EditText) findViewById(R.id.newRemedie_title);
        category = (EditText) findViewById(R.id.newRemedie_category);
        description = (EditText)findViewById(R.id.newRemedie_description);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_newRemedie);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_newRemedie);
        post = (Button) findViewById(R.id.newRemedie_post);
        post.setOnClickListener(this);
        remedieClient = RetrofitConstant.getRetrofitRemedieClient();
        SharedPreferences preferences = SharedPrefManager.getCustomSharedPreferences(NewRemedieActivity.this, Constant.LOGIN_SESSION,MODE_PRIVATE);
        user_id = Constant.getUserDetail(NewRemedieActivity.this,"id");
        user_name = Constant.getUserDetail(NewRemedieActivity.this,"name");
    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        post.setVisibility(View.GONE);
        String getTitle = title.getText().toString();
        String getCategory = category.getText().toString();
        String getDescription = description.getText().toString();
        if (TextUtils.isEmpty(getTitle) && TextUtils.isEmpty(getCategory) && TextUtils.isEmpty(getDescription)){
            Toast.makeText(NewRemedieActivity.this,"Enter complete detail!",Toast.LENGTH_SHORT).show();
        }else {
            remedieClient.insertRemedie(user_id,user_name,getTitle,getDescription,getCategory,"pending","").enqueue(new Callback<Respond>() {
                @Override
                public void onResponse(Call<Respond> call, Response<Respond> response) {
                    Respond res = response.body();
                    if (res != null) {
                        Log.e(TAG,res.getMessage());
                        if (res.getStatus().contains("success")) {
                            Log.e(TAG,res.getStatus());
                            onSnackBar(res.getMessage(), Color.GREEN);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(NewRemedieActivity.this, HomeRemedieActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }, 2000);
                        }
                    }else {
                        Log.e(TAG,"Api, failed to create new remede.");
                    }
                }

                @Override
                public void onFailure(Call<Respond> call, Throwable t) {

                }
            });
        }
    }

    public void onSnackBar(String message, int color){
        progressBar.setVisibility(View.GONE);
        post.setVisibility(View.VISIBLE);
        Snackbar snackbar  = Snackbar.make(coordinatorLayout,message,Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(color);
        snackbar.show();
    }
}
