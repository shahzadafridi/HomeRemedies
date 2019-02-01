package com.example.opriday.homeremedies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opriday.homeremedies.Model.Remedy;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Remedies.DeleteRemedie;
import com.example.opriday.homeremedies.Remedies.EditRemedie;
import com.example.opriday.homeremedies.Utility.Constant;
import com.example.opriday.homeremedies.Utility.CustomSharedPrefence;

import org.w3c.dom.Text;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Opriday on 11/19/2018.
 */

public class CustomAdapter extends BaseAdapter implements View.OnClickListener {

    List<Remedy> list = null;
    Context context;
    Remedy remedy;
    boolean isAdmin;
    TextView delete, edit, title, detail,postedBy;
    String getUserType;
    CoordinatorLayout coordinatorLayout;

    public CustomAdapter(List<Remedy> list, Context context, boolean isAdmin, String getUserType, CoordinatorLayout coordinatorLayout) {
        this.list = list;
        this.context = context;
        this.isAdmin = isAdmin;
        this.getUserType = getUserType;
        this.coordinatorLayout = coordinatorLayout;

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
            convertView = inflater.inflate(R.layout.remedies_list, parent, false);
            edit = (TextView) convertView.findViewById(R.id.edit);
            edit.setOnClickListener(this);
            delete = convertView.findViewById(R.id.delete);
            delete.setVisibility(View.INVISIBLE);
            if (isAdmin) {
                delete = convertView.findViewById(R.id.delete);
                delete.setVisibility(View.VISIBLE);
                delete.setOnClickListener(this);

            }
        }
        postedBy = (TextView) convertView.findViewById(R.id.postedBy);
        title = (TextView) convertView.findViewById(R.id.title_adapter);
        detail = (TextView) convertView.findViewById(R.id.detial_adpater);
        title.setText(remedy.getName());
        detail.setText(remedy.getDetail());
        postedBy.setText("Posted by:"+remedy.getPosted_by());
        edit = (TextView) convertView.findViewById(R.id.edit);
        delete = convertView.findViewById(R.id.delete);
        edit.setTag(position);
        delete.setTag(position);
        //        TextView title = (TextView) convertView.findViewById(R.id.title);
//        TextView detail = (TextView) convertView.findViewById(R.id.detail);
//        title.setText(remedy.getName());
//        detail.setText(remedy.getDetail());
        return convertView;
    }
    @Override
    public void onClick(View v) {
        Integer position = (int) v.getTag();
        switch (v.getId()) {
            case R.id.edit:
                Log.e("check", "" + position + " at:edit clicked");
                SharedPreferences preferences = CustomSharedPrefence.getCustomSharedPreferences(context, Constant.LOGIN_SESSION,MODE_PRIVATE);
                String getUserName = preferences.getString(Constant.USER_NAME,"user");
                if (getUserType.contentEquals("user") && list.get(position).getPosted_by().contentEquals("admin")) {
                    onSnackBar("You don't have permission to edit admin post", Color.GREEN);
                  //  Toast.makeText(context,"You don't have permission to edit admin post",Toast.LENGTH_SHORT).show();
                }else {
                    Intent editRemedie = new Intent(context, EditRemedie.class);
                    editRemedie.putExtra("id",list.get(position).getId());
                    editRemedie.putExtra("name", list.get(position).getName());
                    editRemedie.putExtra("type", list.get(position).getType());
                    editRemedie.putExtra("detail", list.get(position).getDetail());
                    context.startActivity(editRemedie);
                }
                break;
            case R.id.delete:
                Log.e("check", "" + position + " at:delete clicked");
                Intent deleteRemedie = new Intent(context, DeleteRemedie.class);
                deleteRemedie.putExtra("id",list.get(position).getId());
                deleteRemedie.putExtra("name", list.get(position).getName());
                deleteRemedie.putExtra("type", list.get(position).getType());
                deleteRemedie.putExtra("detail", list.get(position).getDetail());
                context.startActivity(deleteRemedie);
                break;
        }
    }

    public void onSnackBar(String message, int color){
        Snackbar snackbar  = Snackbar.make(coordinatorLayout,message,Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(color);
        snackbar.show();
    }
}
