package com.example.opriday.homeremedies.Remedies;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.opriday.homeremedies.Adapters.CustomAdapter;
import com.example.opriday.homeremedies.Adapters.PendingCustomAdapter;
import com.example.opriday.homeremedies.Model.Remedies;
import com.example.opriday.homeremedies.Model.Remedy;
import com.example.opriday.homeremedies.Network.IRetrofitClient;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Utility.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingRemedies extends AppCompatActivity implements ListView.OnItemClickListener{

    ListView listView;
    List<Remedy> list = null;
    CoordinatorLayout coordinatorLayout;
    IRetrofitClient service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_remedies);
        listView = (ListView) findViewById(R.id.listView_pending);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_pending);
        listView.setOnItemClickListener(this);
        service = Constant.getService();
        onGetRemedies();
    }
    private String onGetRemedies() {

        service.getRemediesList("list_pending").enqueue(new Callback<Remedies>() {
            @Override
            public void onResponse(Call<Remedies> call, Response<Remedies> response) {
                Log.e("response",response.body().getRemedies().get(0).getName());
                list =  response.body().getRemedies();
                PendingCustomAdapter adapter = new PendingCustomAdapter(list, PendingRemedies.this,coordinatorLayout,service);
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

    }
}
