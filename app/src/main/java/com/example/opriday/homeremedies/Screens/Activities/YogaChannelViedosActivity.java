package com.example.opriday.homeremedies.Screens.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.opriday.homeremedies.Model.Youtube.Item;
import com.example.opriday.homeremedies.Model.Youtube.PlayList;
import com.example.opriday.homeremedies.Model.Youtube.Snippet;
import com.example.opriday.homeremedies.Screens.Adapters.PlayListAdapter;
import com.example.opriday.homeremedies.Network.IRetrofitClient;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Network.RetrofitConstant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class YogaChannelViedosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    IRetrofitClient service;
    ListView listView;
    PlayListAdapter adapter;
    String TAG = "YogaChannelViedosActivity";
    PlayList playList;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_channels);
        listView = (ListView) findViewById(R.id.listView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_channel);
        listView.setOnItemClickListener(this);
        service = RetrofitConstant.getYoutubeService();
        service.getYoutubePlayListsResponse("snippet", "PLlHwdL_FZdYr_JUHRPwiDWY2RYc0k7YtL", "AIzaSyB8SCKdcqAs7_dHOvUwmjgLtMvnsanzX9U", "10")
                .enqueue(new Callback<PlayList>() {
                    @Override
                    public void onResponse(Call<PlayList> call, Response<PlayList> response) {
                        playList = response.body();
                        if (playList != null) {
                            Log.e(TAG, playList.getEtag());
                            List<Item> items = playList.getItems();
                            if (items != null) {
                                adapter = new PlayListAdapter(YogaChannelViedosActivity.this, R.layout.playlist_layout, items);
                                listView.setAdapter(adapter);
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PlayList> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Item item = playList.getItems().get(position);
        Snippet snippet = item.getSnippet();
        String channelTitle = snippet.getChannelTitle();
        String title = snippet.getTitle();
        String description = snippet.getDescription();
        String time = snippet.getPublishedAt();
        String video_id = snippet.getResourceId().getVideoId();
        Intent intent = new Intent(YogaChannelViedosActivity.this, YoutubePlayerActivity.class);
        intent.putExtra("channel_title", channelTitle);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        intent.putExtra("time", time);
        intent.putExtra("video_id", video_id);
        startActivity(intent);
    }
}
