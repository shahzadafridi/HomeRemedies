package com.example.opriday.homeremedies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.opriday.homeremedies.Model.Remedy;
import com.example.opriday.homeremedies.Model.Respond;
import com.example.opriday.homeremedies.Network.IRetrofitClient;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Remedies.DeleteRemedie;
import com.example.opriday.homeremedies.Remedies.EditRemedie;
import com.example.opriday.homeremedies.Remedies.HomeRemedie;
import com.example.opriday.homeremedies.Utility.Constant;
import com.example.opriday.homeremedies.Utility.CustomSharedPrefence;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Opriday on 11/19/2018.
 */

public class PendingCustomAdapter extends BaseAdapter implements View.OnClickListener {

    List<Remedy> list = null;
    Context context;
    Remedy remedy;
    TextView delete, approve, title, detail, postedBy;
    CoordinatorLayout coordinatorLayout;
    IRetrofitClient service;

    public PendingCustomAdapter(List<Remedy> list, Context context, CoordinatorLayout coordinatorLayout, IRetrofitClient service) {
        this.list = list;
        this.context = context;
        this.coordinatorLayout = coordinatorLayout;
        this.service = service;

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
        remedy = list.get(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.pending_remedies_list, parent, false);
            approve = (TextView) convertView.findViewById(R.id.approve_pendingAdapter);
            delete = (TextView) convertView.findViewById(R.id.delete_pendingAdapter);
            delete.setOnClickListener(this);
            approve.setOnClickListener(this);
        }
        postedBy = (TextView) convertView.findViewById(R.id.postedBy);
        title = (TextView) convertView.findViewById(R.id.title_adapter);
        detail = (TextView) convertView.findViewById(R.id.detial_adpater);
        title.setText(remedy.getName());
        detail.setText(remedy.getDetail());
        postedBy.setText("Posted by:" + remedy.getPosted_by());
        approve = (TextView) convertView.findViewById(R.id.approve_pendingAdapter);
        delete = (TextView) convertView.findViewById(R.id.delete_pendingAdapter);
        delete = convertView.findViewById(R.id.delete);
        approve.setTag(position);
        delete.setTag(position);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        Integer position = (int) v.getTag();
        String id = list.get(position).getId();
        switch (v.getId()) {
            case R.id.approve_pendingAdapter:
                onApprove(id);
                break;
            case R.id.delete_pendingAdapter:
                onDisApprove(id);
                break;
        }
    }

    private void onDisApprove(String id) {
        service.deleteRemedie("disapprove_remedie", id).enqueue(new Callback<Respond>() {
            @Override
            public void onResponse(Call<Respond> call, Response<Respond> response) {
                Respond res = response.body();
                Log.e("response", res.getMessage());
                Log.e("response", res.getSuccess());
                if (res.getSuccess().contains("1")) {
                    onSnackBar(res.getMessage(), Color.GREEN);
                } else if (res.getSuccess().contains("0")) {
                    onSnackBar(res.getMessage(), Color.RED);
                }
            }

            @Override
            public void onFailure(Call<Respond> call, Throwable t) {

            }
        });

    }

    private void onApprove(String id) {
        service.deleteRemedie("approve_remedie", id).enqueue(new Callback<Respond>() {
            @Override
            public void onResponse(Call<Respond> call, Response<Respond> response) {
                Respond res = response.body();
                Log.e("response", res.getMessage());
                Log.e("response", res.getSuccess());
                if (res.getSuccess().contains("1")) {
                    onSnackBar(res.getMessage(), Color.GREEN);
                } else if (res.getSuccess().contains("0")) {
                    onSnackBar(res.getMessage(), Color.RED);
                }

            }

            @Override
            public void onFailure(Call<Respond> call, Throwable t) {

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
