package com.example.opriday.homeremedies.Screens.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opriday.homeremedies.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubePlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    String YOUTUBE_API_KEY = "AIzaSyB8SCKdcqAs7_dHOvUwmjgLtMvnsanzX9U";
    String channelTitle;
    String title;
    String description;
    String time;
    String video_id;
    TextView channelTitleTv,titleTv,descriptionTv,timeTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(YOUTUBE_API_KEY, this);
        channelTitle = getIntent().getStringExtra("channel_title");
        title = getIntent().getStringExtra("title");
        video_id = getIntent().getStringExtra("video_id");
        description = getIntent().getStringExtra("description");
        time = getIntent().getStringExtra("time");
        channelTitleTv = (TextView) findViewById(R.id.ChannelTitle_youTubePlayer);
        titleTv = (TextView) findViewById(R.id.Title_youTubePlayer);
        descriptionTv = (TextView) findViewById(R.id.Description_youTubePlayer);
        timeTv = (TextView) findViewById(R.id.publishAt_youTubePlayer);
        channelTitleTv.setText(channelTitle);
        titleTv.setText(title);
        descriptionTv.setText(description);
        timeTv.setText(time);
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(video_id); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(YOUTUBE_API_KEY, this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }
}
