package com.example.opriday.homeremedies.Screens.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opriday.homeremedies.Model.Remedie;
import com.example.opriday.homeremedies.Model.Remedy;
import com.example.opriday.homeremedies.Model.Respond;
import com.example.opriday.homeremedies.Network.IRetrofitClient;
import com.example.opriday.homeremedies.Network.IRetrofitRemedie;
import com.example.opriday.homeremedies.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Opriday on 11/19/2018.
 */

public class PendingCustomAdapter extends BaseAdapter implements View.OnClickListener {

    List<Remedie> list = null;
    Context context;
    TextView delete, approve, title, detail, postedBy;
    CoordinatorLayout coordinatorLayout;
    IRetrofitRemedie remedieCleint;

    public PendingCustomAdapter(List<Remedie> list, Context context, CoordinatorLayout coordinatorLayout, IRetrofitRemedie remedieCleint) {
        this.list = list;
        this.context = context;
        this.coordinatorLayout = coordinatorLayout;
        this.remedieCleint = remedieCleint;


    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Remedie remedie = list.get(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.pending_remedies_list, parent, false);
            approve = (TextView) convertView.findViewById(R.id.approve_pendingAdapter);
            delete = (TextView) convertView.findViewById(R.id.delete_pendingAdapter);
            delete.setOnClickListener(this);
            approve.setOnClickListener(this);
        }
        postedBy = (TextView) convertView.findViewById(R.id.postedBy_pending);
        title = (TextView) convertView.findViewById(R.id.title_pendingAdapter);
        detail = (TextView) convertView.findViewById(R.id.detial_pendingAdapter);
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
        postedBy.setText("Posted by:" + remedie.getUserName());
        approve = (TextView) convertView.findViewById(R.id.approve_pendingAdapter);
        delete = (TextView) convertView.findViewById(R.id.delete_pendingAdapter);
        approve.setTag(position);
        delete.setTag(position);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        Integer position = (int) v.getTag();
        int id = list.get(position).getId();
        switch (v.getId()) {
            case R.id.approve_pendingAdapter:
                onApprove(id,position);
                break;
            case R.id.delete_pendingAdapter:
                onDisApprove(id,position);
                break;
        }
    }

    private void onDisApprove(final int id,final int position) {
        remedieCleint.deleteRemedie(String.valueOf(id)).enqueue(new Callback<Respond>() {
            @Override
            public void onResponse(Call<Respond> call, Response<Respond> response) {
                Respond res = response.body();
                if (res != null) {
                    Log.e("PendingCustomAdapter",res.getMessage());
                    if (res.getStatus().contains("success")) {
                        list.remove(position);
                        notifyDataSetChanged();
                        onSnackBar("Remedie deleted successfully", Color.RED);
                    }
                }
            }

            @Override
            public void onFailure(Call<Respond> call, Throwable t) {
                Log.e("PendingCustomAdapter",t.getMessage());
            }
        });
    }

    private void onApprove(final int id,final int position) {
       remedieCleint.updateType(String.valueOf(id),"approved").enqueue(new Callback<Respond>() {
           @Override
           public void onResponse(Call<Respond> call, Response<Respond> response) {
               Respond res = response.body();
               if (res != null) {
                   Log.e("PendingCustomAdapter",res.getMessage());
                   if (res.getStatus().contains("success")) {
                       list.remove(position);
                       notifyDataSetChanged();
                       onSnackBar("Remedie approved successfully", Color.GREEN);
                   }
               }
           }

           @Override
           public void onFailure(Call<Respond> call, Throwable t) {
               Log.e("PendingCustomAdapter",t.getMessage());
           }
       });
    }

    public void onSnackBar(String message, int color) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(color);
        snackbar.show();
    }
}
