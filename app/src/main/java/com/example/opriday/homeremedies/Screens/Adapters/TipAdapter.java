package com.example.opriday.homeremedies.Screens.Adapters;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.opriday.homeremedies.Model.Remedie;
import com.example.opriday.homeremedies.Model.Tip;
import com.example.opriday.homeremedies.Network.RetrofitConstant;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Utility.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TipAdapter extends BaseAdapter implements View.OnClickListener {

    List<Tip> list = null;
    Context context;
    Tip tip;
    TextView title, detail,postedBy;
    CoordinatorLayout coordinatorLayout;
    ImageView imageView;
    String user_role = "user";

    public TipAdapter(List<Tip> list, Context context, CoordinatorLayout coordinatorLayout) {
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
        tip = list.get(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.tip_item, parent, false);
        }
        postedBy = (TextView) convertView.findViewById(R.id.postedBy_tip);
        title = (TextView) convertView.findViewById(R.id.title_tip);
        detail = (TextView) convertView.findViewById(R.id.description_tip);
        imageView = (ImageView) convertView.findViewById(R.id.image_tip);
        if (TextUtils.isEmpty(tip.getPicture()))
        {
        }else {
            Picasso.get().load(RetrofitConstant.BASE_URL+tip.getPicture()).resize(500,500).into(imageView);
        }

        if (tip.getTitle().length() > 36){
            String subTitle = tip.getTitle().substring(0,36);
            title.setText(subTitle+"...");
        }else {
            title.setText(tip.getTitle());
        }
        if (tip.getDescription().length() > 370){
            String subDiscription = tip.getDescription().substring(0,370);
            detail.setText(subDiscription+"...");
        }else {
            detail.setText(tip.getDescription());
        }
        postedBy.setText("Author: admin");
        return convertView;
    }
    @Override
    public void onClick(View v) {

    }

}
