package com.example.opriday.homeremedies.Screens.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.opriday.homeremedies.Model.Review;
import com.example.opriday.homeremedies.R;


import java.util.List;

public class ReviewAdapter extends ArrayAdapter<Review> {

    List<Review> list;
    Context context;

    public ReviewAdapter(@NonNull Context context, int resource, List<Review> list) {
        super(context, resource);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Review review = list.get(position);
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.review_layout,parent,false);
        }
        TextView descriptionn  = (TextView) convertView.findViewById(R.id.description_item_review);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.rattingBar_item_review);
        descriptionn.setText(review.getDescription());
        ratingBar.setRating(Float.valueOf(review.getRating()));
        return convertView;
    }
}
