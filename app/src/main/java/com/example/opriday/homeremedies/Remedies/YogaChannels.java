package com.example.opriday.homeremedies.Remedies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.opriday.homeremedies.Adapters.PlayListAdapter;
import com.example.opriday.homeremedies.Model.Youtube.PlayList.ChannelList;
import com.example.opriday.homeremedies.Model.Youtube.PlayList.PLayListSnipet;
import com.example.opriday.homeremedies.Model.Youtube.PlayList.PlayListItem;
import com.example.opriday.homeremedies.Model.Youtube.PlayList.PlayListsInfo;
import com.example.opriday.homeremedies.Network.IRetrofitClient;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Utility.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YogaChannels extends AppCompatActivity{
    VideoView v1,v2,v3;
    ImageView thmb1,thmb2,thmb3;
    String url = "https://www.youtube.com/watch?v=I288nQr_SX4";
    MediaController mc;
    IRetrofitClient service;
    List<ChannelList> playLists = new ArrayList<>();
    ListView listView;
    PlayListAdapter adapter;
    String publishedAt,channelId,playListTitle,description,channelTitle,nextPageToken,playlistId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_channels);
        listView = (ListView) findViewById(R.id.listView);

//        v1 = (VideoView) findViewById(R.id.video1);
//        v2 = (VideoView) findViewById(R.id.video2);
//        v3 = (VideoView) findViewById(R.id.video3);
//        thmb1 = (ImageView) findViewById(R.id.video1_thumbline);
//        thmb2 = (ImageView) findViewById(R.id.video2_thumbline);
//        thmb3 = (ImageView) findViewById(R.id.video3_thumbline);
//        thmb1.setOnClickListener(this);
//        thmb2.setOnClickListener(this);
//        thmb3.setOnClickListener(this);
        service = Constant.getYoutubeService();
              service.getYoutubePlayListsResponse("snippet","homeremedies","playlist","AIzaSyDgqWwZqlcKnRSCGMFWaGlDK13Thpxkrl8","10")
                .enqueue(new Callback<PlayListsInfo>() {
                    @Override
                    public void onResponse(Call<PlayListsInfo> call, Response<PlayListsInfo> response) {
                        PlayListsInfo infos = response.body();
                        List<PlayListItem> list =  infos.getItems();
                        for (int i=0; i<list.size(); i++){
                            PlayListItem item =  list.get(i);
                            PLayListSnipet snipet = item.getSnippet();
                            ChannelList mItem = new ChannelList();
                            mItem.setPlaylistId(item.getId().getPlaylistId());
                            mItem.setChannelId(snipet.getChannelId());
                            mItem.setChannelTitle(snipet.getChannelTitle());
                            mItem.setDescription(snipet.getDescription());
                            mItem.setNextPageToken(infos.getNextPageToken());
                            mItem.setPlayListTitle(snipet.getTitle());
                            mItem.setPublishedAt(snipet.getPublishedAt());
                            mItem.setThumbnail(snipet.getThumbnails().getDefault().getUrl());
                            playLists.add(mItem);
                        }
                        adapter = new PlayListAdapter(YogaChannels.this,R.layout.playlist_layout,playLists);
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<PlayListsInfo> call, Throwable t) {

                    }
                });

//        service.getYoutubePlayListResponse("snippet","PLptoQ9_8ilUEMGO8UstuxExFhXD8NKLU2","AIzaSyDgqWwZqlcKnRSCGMFWaGlDK13Thpxkrl8","10")
//                .enqueue(new Callback<PlayListVideoInfo>() {
//                    @Override
//                    public void onResponse(Call<PlayListVideoInfo> call, Response<PlayListVideoInfo> response) {
//                        PlayListVideoInfo info = response.body();
//                        List<PlayListVideoItem> list = info.getItems();
//                        String id = list.get(0).getSnippet().getChannelId();
//                        Log.e("response",list.get(0).getSnippet().getChannelId());
//                        Log.e("response",list.get(0).getSnippet().getChannelTitle());
//                    }
//
//                    @Override
//                    public void onFailure(Call<PlayListVideoInfo> call, Throwable t) {
//
//                    }
//                });

    }

//    @Override
//    public void onClick(View v) {
//
//        if (v.getId() == R.id.video1_thumbline){
//            thmb1.setVisibility(View.GONE);
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=9hDpiD-JdZk")));
//
//        }else if (v.getId() == R.id.video2_thumbline){
//            thmb2.setVisibility(View.GONE);
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=TDd_QU9Crmk")));
//        }else if (v.getId() == R.id.video3_thumbline){
//            thmb3.setVisibility(View.GONE);
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=TDd_QU9Crmk")));
//        }
//    }
}
