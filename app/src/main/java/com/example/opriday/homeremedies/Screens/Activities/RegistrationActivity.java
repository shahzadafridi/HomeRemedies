package com.example.opriday.homeremedies.Screens.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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

import com.example.opriday.homeremedies.Model.Respond;
import com.example.opriday.homeremedies.Network.IRetrofitUser;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Network.RetrofitConstant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    EditText email, password, username;
    TextView alreadyAccount,registrationBack;
    Button register;
    IRetrofitUser userClient;
    ProgressBar progressBar;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black_color));
        }
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        email = (EditText) findViewById(R.id.email_registration);
        password = (EditText) findViewById(R.id.password_registration);
        username = (EditText) findViewById(R.id.username_registration);
        alreadyAccount = (TextView) findViewById(R.id.already_account);
        register = (Button) findViewById(R.id.registration);
        registrationBack = (TextView) findViewById(R.id.registration_back);
        registrationBack.setOnClickListener(this);
        register.setOnClickListener(this);
        alreadyAccount.setOnClickListener(this);
        userClient =  RetrofitConstant.getRetrofitUserClient();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registration) {
            register.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            String getEmail = email.getText().toString();
            String getpassword = password.getText().toString();
            String getUsername = username.getText().toString();
            if (TextUtils.isEmpty(getEmail) && TextUtils.isEmpty(getpassword) && TextUtils.isEmpty(getUsername)) {
                onSnackBar("Please enter email and password", Color.RED);
            } else {
                userClient.createUser(getUsername,getEmail, getpassword,"user").enqueue(new Callback<Respond>() {
                    @Override
                    public void onResponse(Call<Respond> call, Response<Respond> response) {
                        Respond respond = response.body();
                        Log.e("response", respond.getMessage());
                        Log.e("response", respond.getStatus());
                        if (respond.getStatus().contains("success")) {
                            Intent login = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(login);
                        } else {
                            onSnackBar( respond.getMessage(), Color.RED);
                        }

                    }

                    @Override
                    public void onFailure(Call<Respond> call, Throwable t) {
                        Log.e("response", t.getMessage());
                    }
                });
            }

        }else if (v.getId() == R.id.already_account){
            onBackPressed();
        }else if (v.getId() == R.id.registration_back){
            onBackPressed();
        }
    }

    public void onSnackBar(String message, int color) {
        progressBar.setVisibility(View.GONE);
        register.setVisibility(View.VISIBLE);
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(color);
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
