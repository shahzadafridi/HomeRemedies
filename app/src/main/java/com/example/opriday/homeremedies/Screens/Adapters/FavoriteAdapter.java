package com.example.opriday.homeremedies.Screens.Adapters;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opriday.homeremedies.Model.Favorite;
import com.example.opriday.homeremedies.Model.Remedie;
import com.example.opriday.homeremedies.Model.Respond;
import com.example.opriday.homeremedies.Network.IRetrofitFavorite;
import com.example.opriday.homeremedies.Network.RetrofitConstant;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Utility.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteAdapter extends ArrayAdapter<Favorite> implements View.OnClickListener {

    List<Favorite> favorites;
    Context context;
    Favorite favoirte;
    TextView title, detail,postedBy,favoriteTv;
    IRetrofitFavorite favoriteClient;
    String TAG = "FavoriteAdapter";

    public FavoriteAdapter( List<Favorite> favorites, Context context) {
        super(context,R.layout.favorite_item);
        this.favorites = favorites;
        this.context = context;
        favoriteClient = RetrofitConstant.getRetrofitFavoirteClient();
    }


    @Override
    public int getCount() {
        return favorites.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        favoirte = favorites.get(position);
        Remedie remedie = favoirte.getRemedie();
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.favorite_item, parent, false);
            favoriteTv = (TextView) convertView.findViewById(R.id.favorite_icon);
            favoriteTv.setOnClickListener(this);
        }
        postedBy = (TextView) convertView.findViewById(R.id.postedBy_favorite);
        title = (TextView) convertView.findViewById(R.id.title_favorite);
        detail = (TextView) convertView.findViewById(R.id.detial_favorite);
        favoriteTv = (TextView) convertView.findViewById(R.id.favorite_icon);
        favoriteTv.setOnClickListener(this);
        favoriteTv.setTag(position);
        if (remedie.getTitle().length() > 36){
            String subTitle = remedie.getTitle().substring(0,36);
            title.setText(subTitle+"...");
        }else {
            title.setText(remedie.getTitle());
        }
        if (remedie.getDescription().length() > 370){
            String subDiscription = remedie.getDescription().substring(0,370);
            detail.setText(subDiscription+"...");
        }else {
            detail.setText(remedie.getDescription());
        }
        postedBy.setText("Author: "+remedie.getUserName().toUpperCase());
        return convertView;
    }
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.favorite_icon){
            final int position = (int) favoriteTv.getTag();
            favoriteClient.deleteFavoriteRemedie(String.valueOf(favorites.get(position).getId())).enqueue(new Callback<Respond>() {
                @Override
                public void onResponse(Call<Respond> call, Response<Respond> response) {
                    Respond respond = response.body();
                    if (respond.getStatus().contentEquals("success")){
                        Toast.makeText(context,"Remedie deleted.",Toast.LENGTH_LONG).show();
                        favorites.remove(position);
                        notifyDataSetChanged();
                    }else {
                        Toast.makeText(context,"Remedie failed to delete.",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Respond> call, Throwable t) {
                    Log.e(TAG,t.getMessage());
                }
            });
        }
    }

}
