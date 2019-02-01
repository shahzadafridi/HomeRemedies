package com.example.opriday.homeremedies.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.opriday.homeremedies.Model.Youtube.PlayList.ChannelList;
import com.example.opriday.homeremedies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Opriday on 12/26/2018.
 */

public class PlayListAdapter extends ArrayAdapter<ChannelList> {

    List<ChannelList> list;
    Context context;
    public PlayListAdapter(@NonNull Context context, int resource,List<ChannelList> list) {
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
        ChannelList mItem = list.get(position);
        if (convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.playlist_layout,parent,false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image_playlist);
        TextView playListName = (TextView) convertView.findViewById(R.id.playlistTitle_playlist);
        TextView channelName = (TextView) convertView.findViewById(R.id.channelName_playlist);
        TextView publishAt = (TextView) convertView.findViewById(R.id.publishAt_playlist);
        TextView discription = (TextView) convertView.findViewById(R.id.Description_playlist);
        Picasso.get().load(mItem.getThumbnail()).into(imageView);
        playListName.setText(mItem.getPlayListTitle());
        channelName.setText("Channel:"+mItem.getChannelTitle());
        publishAt.setText("Publish At:"+mItem.getPublishedAt());
        if (mItem.getDescription().length() > 70) {
            String getSubDescription = mItem.getDescription().substring(0,70);
            discription.setText(getSubDescription + "...");
        }else {
            discription.setText(mItem.getDescription());
        }

        return convertView;
    }
}
