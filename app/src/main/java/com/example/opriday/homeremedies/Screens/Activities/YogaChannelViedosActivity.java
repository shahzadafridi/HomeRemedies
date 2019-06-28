package com.example.opriday.homeremedies.Screens.Activities;

import android.content.Context;
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
import com.example.opriday.homeremedies.Utility.Constant;
import com.example.opriday.homeremedies.Utility.SharedPrefManager;

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
        service.getYoutubePlayListsResponse("snippet", "PLicKIlsVkGwKwfKmF9P2hXOyez4DonI-H", "AIzaSyCScfMHkayygbzerbANEYrtdv23kwehnfQ", "10")
                .enqueue(new Callback<PlayList>() {
                    @Override
                    public void onResponse(Call<PlayList> call, Response<PlayList> response) {
                        playList = response.body();
                        if (playList != null) {
                            Log.e(TAG, playList.getEtag());
                            List<Item> items = playList.getItems();
                            if (items != null) {
                                String ItemsToJson = Constant.PlayListItemsToJson(items);
                                //Store Json string in SharedPreferences.
                                SharedPrefManager.getCustomSharedPreferencesEditor(YogaChannelViedosActivity.this, "PlayListItems", MODE_PRIVATE)
                                        .putString("items", ItemsToJson)
                                        .apply();
                                adapter = new PlayListAdapter(YogaChannelViedosActivity.this, R.layout.playlist_layout, items);
                                listView.setAdapter(adapter);
                                progressBar.setVisibility(View.GONE);
                            }else {
                                //Read Json String from json file which placed in assets folder. It will read file then store json into string variable.
                                String JSONItemsString = Constant.readJSONFromAsset(YogaChannelViedosActivity.this);
                                List<Item> items2 = Constant.PlayListItemsFromJson(JSONItemsString); // Pass json string to the method. It will convert it into list.
                                adapter = new PlayListAdapter(YogaChannelViedosActivity.this, R.layout.playlist_layout, items2);
                                listView.setAdapter(adapter);
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PlayList> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
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
