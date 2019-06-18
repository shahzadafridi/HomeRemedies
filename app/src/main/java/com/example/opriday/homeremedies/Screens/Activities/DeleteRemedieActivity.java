package com.example.opriday.homeremedies.Screens.Activities;

import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opriday.homeremedies.Model.Respond;
import com.example.opriday.homeremedies.Network.IRetrofitClient;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Network.RetrofitConstant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteRemedieActivity extends AppCompatActivity implements View.OnClickListener {

    TextView name,type,detail;
    ProgressBar progressBar;
    CoordinatorLayout coordinatorLayout;
    IRetrofitClient service;
    Button delete;
    String getName,getType,getDetail,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_remedie);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_deleteRemedie);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_deleteRemedie);
        name = (TextView) findViewById(R.id.name_deleteRemedie);
        type = (TextView) findViewById(R.id.detail_deleteRemedie);
        detail = (TextView) findViewById(R.id.detail_deleteRemedie);
        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        id = getIntent().getStringExtra("id");
        getName = getIntent().getStringExtra("name");
        getType = getIntent().getStringExtra("type");
        getDetail = getIntent().getStringExtra("detail");
        name.setText(getName);
        type.setText(getType);
        detail.setText(getDetail);
        service = RetrofitConstant.getService();

    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
        String getName = name.getText().toString();
        String getType = type.getText().toString();
        String getDetail = detail.getText().toString();
        if (TextUtils.isEmpty(getName) && TextUtils.isEmpty(getType) && TextUtils.isEmpty(getDetail)){
            Toast.makeText(DeleteRemedieActivity.this,"Enter complete detail!",Toast.LENGTH_SHORT).show();
        }else {
            service.deleteRemedie("delete_remedie",id).enqueue(new Callback<Respond>() {
                @Override
                public void onResponse(Call<Respond> call, Response<Respond> response) {
                    Respond res = response.body();
                    Log.e("response",res.getMessage());
                    Log.e("response",res.getStatus());
                    if (res.getStatus().contains("success")){
                        onSnackBar(res.getMessage(), Color.GREEN);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(DeleteRemedieActivity.this, HomeRemedieActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        },2000);
                    }else if (res.getStatus().contains("0")){
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
        delete.setVisibility(View.VISIBLE);
        Snackbar snackbar  = Snackbar.make(coordinatorLayout,message,Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(color);
        snackbar.show();
    }
}