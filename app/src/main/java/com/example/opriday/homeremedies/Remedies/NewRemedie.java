package com.example.opriday.homeremedies.Remedies;

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
import com.example.opriday.homeremedies.Network.IRetrofitClient;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Utility.Constant;
import com.example.opriday.homeremedies.Utility.CustomSharedPrefence;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRemedie extends AppCompatActivity implements View.OnClickListener {

    EditText name,type,detial;
    Button post;
    IRetrofitClient service;
    String getUserName="user";
    CoordinatorLayout coordinatorLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_remedie);
        name = (EditText) findViewById(R.id.name_newRemedie);
        type = (EditText) findViewById(R.id.type_newRemedie);
        detial = (EditText)findViewById(R.id.detail_newRemedie);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_newRemedie);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_newRemedie);
        post = (Button) findViewById(R.id.post);
        post.setOnClickListener(this);
        service = Constant.getService();
        SharedPreferences preferences = CustomSharedPrefence.getCustomSharedPreferences(NewRemedie.this,Constant.LOGIN_SESSION,MODE_PRIVATE);
        getUserName = preferences.getString(Constant.USER_NAME,"user");
    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        post.setVisibility(View.GONE);
        String getName = name.getText().toString();
        String getType = type.getText().toString();
        String getDetail = detial.getText().toString();
        if (TextUtils.isEmpty(getName) && TextUtils.isEmpty(getType) && TextUtils.isEmpty(getDetail)){
            Toast.makeText(NewRemedie.this,"Enter complete detail!",Toast.LENGTH_SHORT).show();
        }else {
            service.addRemedie("add_remedie",getName,getType,getDetail,getUserName).enqueue(new Callback<Respond>() {
                @Override
                public void onResponse(Call<Respond> call, Response<Respond> response) {
                    Respond res = response.body();
                    Log.e("response",res.getMessage());
                    Log.e("response",res.getSuccess());
                    if (res.getSuccess().contains("1")){
                        onSnackBar(res.getMessage(), Color.GREEN);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(NewRemedie.this,HomeRemedie.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        },2000);
                       }else if (res.getSuccess().contains("2")){
                        onSnackBar(res.getMessage(),Color.RED);
                    }else if (res.getSuccess().contains("0")){
                        onSnackBar(res.getMessage(),Color.RED);
                    }else if (res.getSuccess().contains("3")){
                        onSnackBar(res.getMessage(),Color.RED);
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
