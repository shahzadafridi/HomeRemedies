package com.example.opriday.homeremedies.Screens.Activities;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.opriday.homeremedies.Model.Remedie;
import com.example.opriday.homeremedies.Model.RemedieData;
import com.example.opriday.homeremedies.Network.IRetrofitRemedie;
import com.example.opriday.homeremedies.Screens.Adapters.CustomAdapter;
import com.example.opriday.homeremedies.Screens.Adapters.PendingCustomAdapter;
import com.example.opriday.homeremedies.Model.Remedies;
import com.example.opriday.homeremedies.Model.Remedy;
import com.example.opriday.homeremedies.Network.IRetrofitClient;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Network.RetrofitConstant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingRemediesActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    ListView listView;
    List<Remedie> list = null;
    CoordinatorLayout coordinatorLayout;
    IRetrofitRemedie remedieClient;
    String TAG = "PendingRemediesActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_remedies);
        listView = (ListView) findViewById(R.id.listView_pending);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_pending);
        listView.setOnItemClickListener(this);
        remedieClient = RetrofitConstant.getRetrofitRemedieClient();
        onGetRemedies();
    }

    private void onGetRemedies() {
        remedieClient.remedies("pending").enqueue(new Callback<RemedieData>() {
            @Override
            public void onResponse(Call<RemedieData> call, Response<RemedieData> response) {
                if (response != null) {
                    RemedieData data = response.body();
                    Log.e(TAG, data.getStatus());
                    if (data.getData() != null) {
                        list = data.getData();
                        PendingCustomAdapter adapter = new PendingCustomAdapter(list,PendingRemediesActivity.this,coordinatorLayout,remedieClient);
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
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
