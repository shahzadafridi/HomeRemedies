package com.example.opriday.homeremedies.Screens.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.opriday.homeremedies.Model.User;
import com.example.opriday.homeremedies.Model.UserData;
import com.example.opriday.homeremedies.Network.IRetrofitUser;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Utility.ActivityManager;
import com.example.opriday.homeremedies.Network.RetrofitConstant;
import com.example.opriday.homeremedies.Utility.Constant;
import com.example.opriday.homeremedies.Utility.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText email,password;
    TextView newAccount,loginAsAdmin;
    Button login;
    IRetrofitUser userClient;
    ProgressBar progressBar;
    String TAG = "LoginActivity";
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black_color));
        }
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        email = (EditText) findViewById(R.id.email_login);
        password = (EditText) findViewById(R.id.password_login);
        newAccount = (TextView) findViewById(R.id.newAccount_login);
        loginAsAdmin = (TextView) findViewById(R.id.loginasAdmin);
        login = (Button) findViewById(R.id.login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        login.setOnClickListener(this);
        newAccount.setOnClickListener(this);
        loginAsAdmin.setOnClickListener(this);
        userClient = RetrofitConstant.getRetrofitUserClient();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login){
            login.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            final String getEmail = email.getText().toString();
            String getpassword = password.getText().toString();
            if (TextUtils.isEmpty(getEmail) && TextUtils.isEmpty(getpassword)){
                onSnackBar("Please enter email and password",Color.RED);
            }else {
                userClient.login(getEmail,getpassword).enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(Call<UserData> call, Response<UserData> response) {
                        UserData userData = response.body();
                        if (userData != null) {
                            User user = userData.getData();
                            Log.e(TAG, userData.getStatus());
                            if (user != null) {
                                progressBar.setVisibility(View.GONE);
                                Log.e(TAG,user.getName());
                                Log.e(TAG,user.getEmail());
                                Log.e(TAG,user.getRole());
                                Log.e(TAG,user.getCreatedAt());
                                createSession(user.getId(),user.getName(),user.getEmail(),user.getRole(),user.getCreatedAt());
                                ActivityManager.goToMainActivity(LoginActivity.this);
                            } else {
                                onSnackBar("User not exists.", Color.RED);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserData> call, Throwable t) {
                        Log.e(TAG,t.getMessage());
                    }
                });
            }

        }else if (v.getId() == R.id.newAccount_login){
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.loginasAdmin){
            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
            intent.putExtra("user_type","admin");
            startActivity(intent);
        }
    }

    private Bundle getBundle(Integer id, String name, String email, String role, String createdAt) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ID,String.valueOf(id));
        bundle.putString(Constant.NAME, name);
        bundle.putString(Constant.EMAIL, email);
        bundle.putString(Constant.ROLE, role);
        bundle.putString(Constant.CREATED_AT, createdAt);
        return bundle;
    }

    public void createSession(int id, String name, String email, String role, String createdAt){
        SharedPreferences.Editor editor = SharedPrefManager.getCustomSharedPreferencesEditor(LoginActivity.this, Constant.LOGIN_SESSION, MODE_PRIVATE);
        editor.putBoolean(Constant.IS_LOGIN,true);
        editor.putString(Constant.ID,String.valueOf(id));
        editor.putString(Constant.NAME, name);
        editor.putString(Constant.EMAIL, email);
        editor.putString(Constant.ROLE, role);
        editor.putString(Constant.CREATED_AT, createdAt);
        editor.commit();
    }

    public void onSnackBar(String message, int color){
        progressBar.setVisibility(View.GONE);
        login.setVisibility(View.VISIBLE);
        Snackbar snackbar  = Snackbar.make(coordinatorLayout,message,Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(color);
        snackbar.show();
    }
}
