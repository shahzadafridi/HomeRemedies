package com.example.opriday.homeremedies.Screens.Adapters;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.opriday.homeremedies.Model.Remedie;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Utility.Constant;

import java.util.List;

/**
 * Created by Opriday on 11/19/2018.
 */

public class CustomAdapter extends BaseAdapter implements View.OnClickListener {

    List<Remedie> list = null;
    Context context;
    Remedie remedie;
    TextView title, detail,postedBy;
    CoordinatorLayout coordinatorLayout;
    String user_role = "user";

    public CustomAdapter(List<Remedie> list, Context context, CoordinatorLayout coordinatorLayout) {
        this.list = list;
        this.context = context;
        this.coordinatorLayout = coordinatorLayout;
        user_role = Constant.getUserDetail(context,"role");
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
        remedie = list.get(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.remedies_list, parent, false);
        }
        postedBy = (TextView) convertView.findViewById(R.id.postedBy);
        title = (TextView) convertView.findViewById(R.id.title_adapter);
        detail = (TextView) convertView.findViewById(R.id.detial_adpater);
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

    }

    public void onSnackBar(String message, int color){
        Snackbar snackbar  = Snackbar.make(coordinatorLayout,message,Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(color);
        snackbar.show();
    }
}
