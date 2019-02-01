package com.example.opriday.homeremedies.Remedies;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.opriday.homeremedies.Adapters.CustomAdapter;
import com.example.opriday.homeremedies.MainActivity;
import com.example.opriday.homeremedies.Model.Remedies;
import com.example.opriday.homeremedies.Model.Remedy;
import com.example.opriday.homeremedies.Network.IRetrofitClient;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Utility.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRemedie extends AppCompatActivity implements ListView.OnItemClickListener {
    String[] titles= {"Acne","Arthritis","Asthma","Bone Fracture","Headache"};
    int[] images = {R.drawable.play,R.drawable.play,R.drawable.play,R.drawable.play,R.drawable.play,R.drawable.play};
    GridView grid;
    ListView listView;
    IRetrofitClient service;
    List<Remedy> list = null;
    FloatingActionButton add;
    String getUserType="user";
    CoordinatorLayout coordinatorLayout;

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
                Intent add = new Intent(HomeRemedie.this,NewRemedie.class);
                startActivity(add);
            }
        });
        if(!TextUtils.isEmpty(getIntent().getStringExtra("user_type")))
            getUserType = getIntent().getStringExtra("user_type");

        listView.setOnItemClickListener(this);
        service = Constant.getService();
        onGetRemedies();
    }

    private String onGetRemedies() {
        service.getRemediesList("list_remedie").enqueue(new Callback<Remedies>() {
            @Override
            public void onResponse(Call<Remedies> call, Response<Remedies> response) {
                Log.e("response",response.body().getRemedies().get(0).getName());
                list =  response.body().getRemedies();
                boolean isAdmin = false;
                if (list.size() != 0) {
                    if (getUserType.contentEquals("user")) {
                        isAdmin = false;
                    } else if (getUserType.contentEquals("admin")) {
                        isAdmin = true;
                    }
                }
                CustomAdapter adapter = new CustomAdapter(list, HomeRemedie.this, isAdmin,getUserType,coordinatorLayout);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Remedies> call, Throwable t) {
                Log.e("response",t.getMessage());
            }
        });
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(HomeRemedie.this,Detail.class);
            startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(HomeRemedie.this, MainActivity.class);
        startActivity(intent);
    }
}
