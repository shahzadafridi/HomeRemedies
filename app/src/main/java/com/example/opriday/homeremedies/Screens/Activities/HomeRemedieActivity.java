package com.example.opriday.homeremedies.Screens.Activities;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.opriday.homeremedies.Model.Remedie;
import com.example.opriday.homeremedies.Model.RemedieData;
import com.example.opriday.homeremedies.Network.IRetrofitRemedie;
import com.example.opriday.homeremedies.Screens.Adapters.CustomAdapter;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Network.RetrofitConstant;
import com.example.opriday.homeremedies.Utility.ActivityManager;
import com.example.opriday.homeremedies.Utility.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRemedieActivity extends AppCompatActivity implements ListView.OnItemClickListener {
    String[] titles = {"Acne", "Arthritis", "Asthma", "Bone Fracture", "Headache"};
    int[] images = {R.drawable.play, R.drawable.play, R.drawable.play, R.drawable.play, R.drawable.play, R.drawable.play};
    GridView grid;
    ListView listView;
    IRetrofitRemedie remedieClient;
    List<Remedie> list = null;
    FloatingActionButton add;
    CoordinatorLayout coordinatorLayout;
    String TAG = "HomeRemedieActivity";
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_remedie);
        listView = (ListView) findViewById(R.id.listView);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_Remedies);
        add = (FloatingActionButton) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(HomeRemedieActivity.this, NewRemedieActivity.class);
                startActivity(add);
            }
        });

        listView.setOnItemClickListener(this);
        remedieClient = RetrofitConstant.getRetrofitRemedieClient();
        onGetRemedies();
    }

    private String onGetRemedies() {
        remedieClient.remedies("approved").enqueue(new Callback<RemedieData>() {
            @Override
            public void onResponse(Call<RemedieData> call, Response<RemedieData> response) {
                if (response != null) {
                    RemedieData data = response.body();
                    Log.e(TAG, data.getStatus());
                    if (data.getData() != null) {
                        list = data.getData();
                        adapter = new CustomAdapter(list, HomeRemedieActivity.this, coordinatorLayout);
                        listView.setAdapter(adapter);
                    } else {
                        Log.e(TAG, "No resource found.");
                    }
                }
            }

            @Override
            public void onFailure(Call<RemedieData> call, Throwable t) {
                Log.e("response", t.getMessage());
            }
        });
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.REMEDIE_ID,""+list.get(position).getId());
        bundle.putString(Constant.USER_ID,""+list.get(position).getUserId());
        bundle.putString(Constant.USER_NAME,list.get(position).getUserName());
        bundle.putString(Constant.TITLE,list.get(position).getTitle());
        bundle.putString(Constant.TYPE,list.get(position).getType());
        bundle.putString(Constant.CATEGORY,list.get(position).getCategory());
        bundle.putString(Constant.DESCRIPTION,list.get(position).getDescription());
        bundle.putString(Constant.PICTURE,"");
        bundle.putString(Constant.CREATED_AT,list.get(position).getCreatedAt());
        ActivityManager.goToDetailActivity(HomeRemedieActivity.this,bundle);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(HomeRemedieActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
