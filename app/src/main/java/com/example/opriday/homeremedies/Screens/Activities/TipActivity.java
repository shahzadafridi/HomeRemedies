package com.example.opriday.homeremedies.Screens.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opriday.homeremedies.Model.Respond;
import com.example.opriday.homeremedies.Model.Tip;
import com.example.opriday.homeremedies.Model.TipData;
import com.example.opriday.homeremedies.Network.IRetrofitTip;
import com.example.opriday.homeremedies.Network.RetrofitConstant;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Screens.Adapters.TipAdapter;
import com.example.opriday.homeremedies.Utility.ActivityManager;
import com.example.opriday.homeremedies.Utility.Constant;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TipActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    String TAG = "TipActivity";
    IRetrofitTip tipClient;
    FloatingActionButton add;
    CoordinatorLayout coordinatorLayout;
    ListView listView;
    TipAdapter adapter;
    List<Tip> tips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);
        initUI();
        tipClient = RetrofitConstant.getRetrofitTipClient();
        getTips();
    }

    private void initUI() {
        listView = (ListView) findViewById(R.id.listView_tip);
        add = (FloatingActionButton) findViewById(R.id.add_tip);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_tip);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TipActivity.this,NewTipActivity.class);
                startActivity(intent);
            }
        });
        if (Constant.isAdmin(TipActivity.this)) {
            add.setVisibility(View.VISIBLE);
        } else {
            add.setVisibility(View.GONE);
        }

        listView.setOnItemClickListener(this);

    }

    private void getTips() {
        tipClient.tips().enqueue(new Callback<TipData>() {
            @Override
            public void onResponse(Call<TipData> call, Response<TipData> response) {
                TipData data = response.body();
                if (data != null){
                    tips = data.getData();
                    adapter = new TipAdapter(tips,TipActivity.this,coordinatorLayout);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<TipData> call, Throwable t) {

            }
        });
    }

    public void onSnackBar(String message, int color){
        Snackbar snackbar  = Snackbar.make(coordinatorLayout,message,Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(color);
        snackbar.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.REMEDIE_ID,""+tips.get(position).getId());
        bundle.putString(Constant.USER_ID,""+tips.get(position).getUserId());
        bundle.putString(Constant.TITLE,tips.get(position).getTitle());
        bundle.putString(Constant.DESCRIPTION,tips.get(position).getDescription());
        bundle.putString(Constant.PICTURE,tips.get(position).getPicture());
        bundle.putString(Constant.CREATED_AT,tips.get(position).getCreatedAt());
        ActivityManager.goToDetailActivity(TipActivity.this,bundle);
    }
}
