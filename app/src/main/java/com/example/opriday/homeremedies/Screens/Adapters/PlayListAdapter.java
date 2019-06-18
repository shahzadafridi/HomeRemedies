package com.example.opriday.homeremedies.Screens.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.opriday.homeremedies.Model.Youtube.Item;
import com.example.opriday.homeremedies.Model.Youtube.Snippet;
import com.example.opriday.homeremedies.Model.Youtube.Thumbnails;
import com.example.opriday.homeremedies.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Opriday on 12/26/2018.
 */

public class PlayListAdapter extends ArrayAdapter<Item> {

    List<Item> list;
    Context context;
    public PlayListAdapter(@NonNull Context context, int resource,List<Item> list) {
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
        Item mItem = list.get(position);
        Snippet snippet = mItem.getSnippet();
        Thumbnails thumbnails = snippet.getThumbnails();
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.playlist_layout,parent,false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_playlist);
        TextView playListName = (TextView) convertView.findViewById(R.id.playlistTitle_playlist);
            TextView publishAt = (TextView) convertView.findViewById(R.id.publishAt_playlist);
        TextView discription = (TextView) convertView.findViewById(R.id.Description_playlist);
        Picasso.get().load(thumbnails.getMaxres().getUrl()).resize(770,310).into(imageView);
        playListName.setText(snippet.getTitle());
        String s = snippet.getPublishedAt();
        publishAt.setText("  Publish At: "+s.substring(0,s.indexOf("T")));
        if (snippet.getDescription().length() > 150) {
            String getSubDescription = snippet.getDescription().substring(0,150);
            discription.setText(getSubDescription + "...");
        }else {
            discription.setText(snippet.getDescription());
        }
        return convertView;
    }
}
