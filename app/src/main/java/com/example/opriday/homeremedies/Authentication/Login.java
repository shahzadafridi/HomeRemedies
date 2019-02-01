package com.example.opriday.homeremedies.Authentication;

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
import android.widget.Toast;

import com.example.opriday.homeremedies.MainActivity;
import com.example.opriday.homeremedies.Model.Respond;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Remedies.HomeRemedie;
import com.example.opriday.homeremedies.Utility.Constant;
import com.example.opriday.homeremedies.Network.IRetrofitClient;
import com.example.opriday.homeremedies.Utility.CustomSharedPrefence;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener{

    EditText email,password;
    TextView newAccount,loginAsAdmin;
    Button login;
    IRetrofitClient service;
    ProgressBar progressBar;
    String user_type = "user";
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
        service = Constant.getService();
        if(!TextUtils.isEmpty(getIntent().getStringExtra("user_type")))
        user_type = getIntent().getStringExtra("user_type");
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
                service.getSession(getEmail,getpassword,user_type.toLowerCase()).enqueue(new Callback<Respond>() {
                    @Override
                    public void onResponse(Call<Respond> call, Response<Respond> response) {
                        Respond session = response.body();
                        Log.e("response",session.getMessage());
                        Log.e("response",session.getSuccess());
                        if (session.getSuccess().contains("1")){
                            progressBar.setVisibility(View.GONE);
                            SharedPreferences.Editor editor = CustomSharedPrefence.getCustomSharedPreferencesEditor(Login.this,Constant.LOGIN_SESSION,MODE_PRIVATE);
                            editor.putString(Constant.USER_NAME,getUserName(getEmail));
                            editor.putString(Constant.USER_TYPE,user_type );
                            editor.putBoolean(Constant.LOGIN_SESSION_STATUS,true);
                            editor.commit();
                            Intent main = new Intent(Login.this, MainActivity.class);
                            main.putExtra("user_type",user_type);
                            startActivity(main);
                        }else if (session.getSuccess().contains("2")){
                            onSnackBar("Password Incorrect!",Color.RED);
                        }else if (session.getSuccess().contains("0")){
                            onSnackBar("enter username and password!",Color.RED);
                        }else if (session.getSuccess().contains("3")){
                            onSnackBar("user record not found in database!",Color.RED);
                        }
                    }

                    @Override
                    public void onFailure(Call<Respond> call, Throwable t) {
                        Log.e("response Error",t.getMessage());
                    }
                });
            }

        }else if (v.getId() == R.id.newAccount_login){
            Intent intent = new Intent(Login.this,Registration.class);
            startActivity(intent);
        }else if (v.getId() == R.id.loginasAdmin){
            Intent intent = new Intent(Login.this,Login.class);
            intent.putExtra("user_type","admin");
            startActivity(intent);
        }
    }

    private String getUserName(String getEmail) {
        if (getEmail.contains("@")){
            String getName = getEmail.substring(0,getEmail.indexOf("@"));
            return getName;
        }
        return getEmail;
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
