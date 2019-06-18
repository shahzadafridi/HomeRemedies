package com.example.opriday.homeremedies.Screens.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opriday.homeremedies.Model.Respond;
import com.example.opriday.homeremedies.Network.IRetrofitFavorite;
import com.example.opriday.homeremedies.Network.IRetrofitRemedie;
import com.example.opriday.homeremedies.Network.RetrofitConstant;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Utility.ActivityManager;
import com.example.opriday.homeremedies.Utility.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    TextView title, category, description, postedBy, date, favorite, share, reviews,delete,edit;
    Bundle getBundle;
    IRetrofitFavorite favoriteClient;
    IRetrofitRemedie remedieClient;
    String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getBundle = getIntent().getExtras();
        title = (TextView) findViewById(R.id.detail_title);
        category = (TextView) findViewById(R.id.detaile_category);
        description = (TextView) findViewById(R.id.remedie_detail);
        postedBy = (TextView) findViewById(R.id.detail_postedBy);
        date = (TextView) findViewById(R.id.detail_date);
        favorite = (TextView) findViewById(R.id.favorite_detail);
        share = (TextView) findViewById(R.id.share_detail);
        reviews = (TextView) findViewById(R.id.star_detail);
        delete = (TextView) findViewById(R.id.delete_detail);
        edit = (TextView) findViewById(R.id.edit_detail);
        if (!TextUtils.isEmpty(getBundle.getString(Constant.TITLE)))
        title.setText(getBundle.getString(Constant.TITLE));
        if (!TextUtils.isEmpty(getBundle.getString(Constant.CATEGORY)))
        category.setText("Category: " + getBundle.getString(Constant.CATEGORY));
        if (!TextUtils.isEmpty(getBundle.getString(Constant.DESCRIPTION)))
        description.setText(getBundle.getString(Constant.DESCRIPTION));
        if (!TextUtils.isEmpty(getBundle.getString(Constant.USER_NAME)))
        postedBy.setText("Author: " + getBundle.getString(Constant.USER_NAME));
        date.setText("Date: " + getBundle.getString(Constant.CREATED_AT));
        favorite.setOnClickListener(this);
        share.setOnClickListener(this);
        reviews.setOnClickListener(this);
        delete.setOnClickListener(this);
        edit.setOnClickListener(this);
        remedieClient = RetrofitConstant.getRetrofitRemedieClient();
        favoriteClient = RetrofitConstant.getRetrofitFavoirteClient();
        if (Constant.isAdmin(DetailActivity.this)) {
            delete.setVisibility(View.VISIBLE);
        } else {
            delete.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(getBundle.getString(Constant.USER_NAME))) {
            if (getBundle.getString(Constant.USER_NAME).toLowerCase().contentEquals(Constant.getUserDetail(DetailActivity.this, Constant.NAME).toLowerCase()) || Constant.getUserDetail(DetailActivity.this, Constant.NAME).toLowerCase().contentEquals("admin")) {
                edit.setTextColor(getResources().getColor(R.color.disable));
                edit.setEnabled(false);
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.favorite_detail) {
            String getRemedieId = getBundle.getString((Constant.REMEDIE_ID));
//            String getUserId = getBundle.getString(Constant.USER_ID);
            String getUserId = Constant.getUserDetail(DetailActivity.this,Constant.ID);
            favoriteClient.createfavoriteRemedie(getUserId,getRemedieId).enqueue(new Callback<Respond>() {
                @Override
                public void onResponse(Call<Respond> call, Response<Respond> response) {
                    Respond respond = response.body();
                    if (respond.getStatus().contentEquals("success")){
                        Log.e(TAG,"Remedie added to favorite list.");
                        Toast.makeText(DetailActivity.this,"Remedie added to favorite list.",Toast.LENGTH_LONG).show();
                    }else {
                        Log.e(TAG,"Remedie not added to favorite list.");
                    }
                }

                @Override
                public void onFailure(Call<Respond> call, Throwable t) {
                    Log.e(TAG,t.getMessage());
                }
            });
        } else if (v.getId() == R.id.share_detail) {
            String getData = getRemedieDetail();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, getData);
            startActivity(Intent.createChooser(intent, "Share"));
        } else if (v.getId() == R.id.star_detail) {
            ActivityManager.goToReviewActivity(DetailActivity.this, getBundle);
        }else if(v.getId() == R.id.edit_detail){
            if(getBundle.getString(Constant.USER_NAME).toLowerCase().contentEquals(Constant.getUserDetail(DetailActivity.this,Constant.NAME).toLowerCase())) {
                ActivityManager.goToEditActivity(DetailActivity.this, getBundle);
            }
        }else if (v.getId() == R.id.delete_detail){
            deleteRemedie(getBundle.getString(Constant.REMEDIE_ID));
        }
    }

    private void deleteRemedie(String id) {
        remedieClient.deleteRemedie(id).enqueue(new Callback<Respond>() {
            @Override
            public void onResponse(Call<Respond> call, Response<Respond> response) {
                Respond res = response.body();
                if (res != null) {
                    Log.e("PendingCustomAdapter",res.getMessage());
                    if (res.getStatus().contains("success")) {
                        Toast.makeText(DetailActivity.this,"Remedie deleted successfully", Toast.LENGTH_LONG).show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(DetailActivity.this, HomeRemedieActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }, 2000);
                    }
                }
            }

            @Override
            public void onFailure(Call<Respond> call, Throwable t) {

            }
        });
    }

    private String getRemedieDetail() {
        StringBuilder builder = new StringBuilder();
        builder.append(getBundle.getString(Constant.TITLE));
        builder.append("\n");
        builder.append(getBundle.getString(Constant.CATEGORY));
        builder.append("\n\n");
        builder.append(getBundle.getString(Constant.DESCRIPTION));
        builder.append("\n\n");
        builder.append("By "+getBundle.getString(Constant.USER_NAME));
        builder.append("\n\n\n");
        builder.append("Remedie shared from Home Remedies And Natural Care");
        return builder.toString();
    }
}
