package com.example.opriday.homeremedies.Screens.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.opriday.homeremedies.Model.Favorite;
import com.example.opriday.homeremedies.Model.FavoriteData;
import com.example.opriday.homeremedies.Network.IRetrofitFavorite;
import com.example.opriday.homeremedies.Network.RetrofitConstant;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Screens.Adapters.FavoriteAdapter;
import com.example.opriday.homeremedies.Utility.ActivityManager;
import com.example.opriday.homeremedies.Utility.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    ListView listView;
    IRetrofitFavorite favoriteClient;
    List<Favorite> favorites;
    FavoriteAdapter adapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        listView = (ListView) findViewById(R.id.list_favorite);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_favourite);
        favoriteClient = RetrofitConstant.getRetrofitFavoirteClient();
        getFavoriteRemedies();
    }

    private void getFavoriteRemedies() {
        String getUserId = Constant.getUserDetail(FavoriteActivity.this,Constant.ID);
        favoriteClient.favoriteRemedies(getUserId).enqueue(new Callback<FavoriteData>() {
            @Override
            public void onResponse(Call<FavoriteData> call, Response<FavoriteData> response) {
                FavoriteData data = response.body();
                if (data != null){
                   favorites = data.getData();
                   adapter = new FavoriteAdapter(favorites,FavoriteActivity.this);
                   listView.setAdapter(adapter);
                   progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<FavoriteData> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.REMEDIE_ID,""+favorites.get(position).getId());
        bundle.putString(Constant.USER_ID,""+favorites.get(position).getUserId());
  //      ActivityManager.goToDetailActivity(FavoriteActivity.this,bundle);
    }
}
